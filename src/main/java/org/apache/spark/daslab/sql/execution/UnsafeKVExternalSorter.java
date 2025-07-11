package org.apache.spark.daslab.sql.execution;


import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Supplier;

import com.google.common.annotations.VisibleForTesting;


import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow;
import org.apache.spark.daslab.sql.engine.expressions.codegen.BaseOrdering;
import org.apache.spark.daslab.sql.engine.expressions.codegen.GenerateOrdering;
import org.apache.spark.daslab.sql.types.StructType;

//todo
import org.apache.spark.SparkEnv;
import org.apache.spark.TaskContext;
import org.apache.spark.memory.TaskMemoryManager;
import org.apache.spark.serializer.SerializerManager;
import org.apache.spark.storage.BlockManager;
import org.apache.spark.unsafe.KVIterator;
import org.apache.spark.unsafe.Platform;
import org.apache.spark.unsafe.array.LongArray;
import org.apache.spark.unsafe.map.BytesToBytesMap;
import org.apache.spark.unsafe.memory.MemoryBlock;
import org.apache.spark.util.collection.unsafe.sort.*;

/**
 * A class for performing external sorting on key-value records. Both key and value are UnsafeRows.
 *
 * Note that this class allows optionally passing in a {@link BytesToBytesMap} directly in order
 * to perform in-place sorting of records in the map.
 */
public final class UnsafeKVExternalSorter {

    private final StructType keySchema;
    private final StructType valueSchema;
    private final UnsafeExternalRowSorter.PrefixComputer prefixComputer;
    private final UnsafeExternalSorter sorter;

    public UnsafeKVExternalSorter(
            StructType keySchema,
            StructType valueSchema,
            BlockManager blockManager,
            SerializerManager serializerManager,
            long pageSizeBytes,
            int numElementsForSpillThreshold) throws IOException {
        this(keySchema, valueSchema, blockManager, serializerManager, pageSizeBytes,
                numElementsForSpillThreshold, null);
    }

    public UnsafeKVExternalSorter(
            StructType keySchema,
            StructType valueSchema,
            BlockManager blockManager,
            SerializerManager serializerManager,
            long pageSizeBytes,
            int numElementsForSpillThreshold,
            @Nullable BytesToBytesMap map) throws IOException {
        this.keySchema = keySchema;
        this.valueSchema = valueSchema;
        final TaskContext taskContext = TaskContext.get();

        prefixComputer = SortPrefixUtils.createPrefixGenerator(keySchema);
        PrefixComparator prefixComparator = SortPrefixUtils.getPrefixComparator(keySchema);
        BaseOrdering ordering = GenerateOrdering.create(keySchema);
        Supplier<RecordComparator> comparatorSupplier =
                () -> new KVComparator(ordering, keySchema.length());
        boolean canUseRadixSort = keySchema.length() == 1 &&
                SortPrefixUtils.canSortFullyWithPrefix(keySchema.apply(0));

        TaskMemoryManager taskMemoryManager = taskContext.taskMemoryManager();

        if (map == null) {
            sorter = UnsafeExternalSorter.create(
                    taskMemoryManager,
                    blockManager,
                    serializerManager,
                    taskContext,
                    comparatorSupplier,
                    prefixComparator,
                    SparkEnv.get().conf().getInt("spark.shuffle.sort.initialBufferSize",
                            UnsafeExternalRowSorter.DEFAULT_INITIAL_SORT_BUFFER_SIZE),
                    pageSizeBytes,
                    numElementsForSpillThreshold,
                    canUseRadixSort);
        } else {
            // During spilling, the pointer array in `BytesToBytesMap` will not be used, so we can borrow
            // that and use it as the pointer array for `UnsafeInMemorySorter`.
            LongArray pointerArray = map.getArray();
            // `BytesToBytesMap`'s pointer array is only guaranteed to hold all the distinct keys, but
            // `UnsafeInMemorySorter`'s pointer array need to hold all the entries. Since
            // `BytesToBytesMap` can have duplicated keys, here we need a check to make sure the pointer
            // array can hold all the entries in `BytesToBytesMap`.
            // The pointer array will be used to do in-place sort, which requires half of the space to be
            // empty. Note: each record in the map takes two entries in the pointer array, one is record
            // pointer, another is key prefix. So the required size of pointer array is `numRecords * 4`.
            // TODO: It's possible to change UnsafeInMemorySorter to have multiple entries with same key,
            // so that we can always reuse the pointer array.
            if (map.numValues() > pointerArray.size() / 4) {
                // Here we ask the map to allocate memory, so that the memory manager won't ask the map
                // to spill, if the memory is not enough.
                pointerArray = map.allocateArray(map.numValues() * 4L);
            }

            // Since the pointer array(either reuse the one in the map, or create a new one) is guaranteed
            // to be large enough, it's fine to pass `null` as consumer because we won't allocate more
            // memory.
            final UnsafeInMemorySorter inMemSorter = new UnsafeInMemorySorter(
                    null,
                    taskMemoryManager,
                    comparatorSupplier.get(),
                    prefixComparator,
                    pointerArray,
                    canUseRadixSort);

            // We cannot use the destructive iterator here because we are reusing the existing memory
            // pages in BytesToBytesMap to hold records during sorting.
            // The only new memory we are allocating is the pointer/prefix array.
            BytesToBytesMap.MapIterator iter = map.iterator();
            final int numKeyFields = keySchema.size();
            UnsafeRow row = new UnsafeRow(numKeyFields);
            while (iter.hasNext()) {
                final BytesToBytesMap.Location loc = iter.next();
                final Object baseObject = loc.getKeyBase();
                final long baseOffset = loc.getKeyOffset();

                // Get encoded memory address
                // baseObject + baseOffset point to the beginning of the key data in the map, but that
                // the KV-pair's length data is stored in the word immediately before that address
                MemoryBlock page = loc.getMemoryPage();
                long address = taskMemoryManager.encodePageNumberAndOffset(page, baseOffset - 8);

                // Compute prefix
                row.pointTo(baseObject, baseOffset, loc.getKeyLength());
                final UnsafeExternalRowSorter.PrefixComputer.Prefix prefix =
                        prefixComputer.computePrefix(row);

                inMemSorter.insertRecord(address, prefix.value, prefix.isNull);
            }

            sorter = UnsafeExternalSorter.createWithExistingInMemorySorter(
                    taskMemoryManager,
                    blockManager,
                    serializerManager,
                    taskContext,
                    comparatorSupplier,
                    prefixComparator,
                    SparkEnv.get().conf().getInt("spark.shuffle.sort.initialBufferSize",
                            UnsafeExternalRowSorter.DEFAULT_INITIAL_SORT_BUFFER_SIZE),
                    pageSizeBytes,
                    numElementsForSpillThreshold,
                    inMemSorter);

            // reset the map, so we can re-use it to insert new records. the inMemSorter will not used
            // anymore, so the underline array could be used by map again.
            map.reset();
        }
    }

    /**
     * Inserts a key-value record into the sorter. If the sorter no longer has enough memory to hold
     * the record, the sorter sorts the existing records in-memory, writes them out as partially
     * sorted runs, and then reallocates memory to hold the new record.
     */
    public void insertKV(UnsafeRow key, UnsafeRow value) throws IOException {
        final UnsafeExternalRowSorter.PrefixComputer.Prefix prefix =
                prefixComputer.computePrefix(key);
        sorter.insertKVRecord(
                key.getBaseObject(), key.getBaseOffset(), key.getSizeInBytes(),
                value.getBaseObject(), value.getBaseOffset(), value.getSizeInBytes(),
                prefix.value, prefix.isNull);
    }

    /**
     * Merges another UnsafeKVExternalSorter into `this`, the other one will be emptied.
     *
     * @throws IOException
     */
    public void merge(UnsafeKVExternalSorter other) throws IOException {
        sorter.merge(other.sorter);
    }

    /**
     * Returns a sorted iterator. It is the caller's responsibility to call `cleanupResources()`
     * after consuming this iterator.
     */
    public KVSorterIterator sortedIterator() throws IOException {
        try {
            final UnsafeSorterIterator underlying = sorter.getSortedIterator();
            if (!underlying.hasNext()) {
                // Since we won't ever call next() on an empty iterator, we need to clean up resources
                // here in order to prevent memory leaks.
                cleanupResources();
            }
            return new KVSorterIterator(underlying);
        } catch (IOException e) {
            cleanupResources();
            throw e;
        }
    }

    /**
     * Return the total number of bytes that has been spilled into disk so far.
     */
    public long getSpillSize() {
        return sorter.getSpillSize();
    }

    /**
     * Return the peak memory used so far, in bytes.
     */
    public long getPeakMemoryUsedBytes() {
        return sorter.getPeakMemoryUsedBytes();
    }

    /**
     * Marks the current page as no-more-space-available, and as a result, either allocate a
     * new page or spill when we see the next record.
     */
    @VisibleForTesting
    void closeCurrentPage() {
        sorter.closeCurrentPage();
    }

    /**
     * Frees this sorter's in-memory data structures and cleans up its spill files.
     */
    public void cleanupResources() {
        sorter.cleanupResources();
    }

    private static final class KVComparator extends RecordComparator {
        private final BaseOrdering ordering;
        private final UnsafeRow row1;
        private final UnsafeRow row2;

        KVComparator(BaseOrdering ordering, int numKeyFields) {
            this.row1 = new UnsafeRow(numKeyFields);
            this.row2 = new UnsafeRow(numKeyFields);
            this.ordering = ordering;
        }

        @Override
        public int compare(
                Object baseObj1,
                long baseOff1,
                int baseLen1,
                Object baseObj2,
                long baseOff2,
                int baseLen2) {
            // Note that since ordering doesn't need the total length of the record, we just pass 0
            // into the row.
            row1.pointTo(baseObj1, baseOff1 + 4, 0);
            row2.pointTo(baseObj2, baseOff2 + 4, 0);
            return ordering.compare(row1, row2);
        }
    }

    public class KVSorterIterator extends KVIterator<UnsafeRow, UnsafeRow> {
        private UnsafeRow key = new UnsafeRow(keySchema.size());
        private UnsafeRow value = new UnsafeRow(valueSchema.size());
        private final UnsafeSorterIterator underlying;

        private KVSorterIterator(UnsafeSorterIterator underlying) {
            this.underlying = underlying;
        }

        @Override
        public boolean next() throws IOException {
            try {
                if (underlying.hasNext()) {
                    underlying.loadNext();

                    Object baseObj = underlying.getBaseObject();
                    long recordOffset = underlying.getBaseOffset();
                    int recordLen = underlying.getRecordLength();

                    // Note that recordLen = keyLen + valueLen + 4 bytes (for the keyLen itself)
                    int keyLen = Platform.getInt(baseObj, recordOffset);
                    int valueLen = recordLen - keyLen - 4;
                    key.pointTo(baseObj, recordOffset + 4, keyLen);
                    value.pointTo(baseObj, recordOffset + 4 + keyLen, valueLen);

                    return true;
                } else {
                    key = null;
                    value = null;
                    cleanupResources();
                    return false;
                }
            } catch (IOException e) {
                cleanupResources();
                throw e;
            }
        }

        @Override
        public UnsafeRow getKey() {
            return key;
        }

        @Override
        public UnsafeRow getValue() {
            return value;
        }

        @Override
        public void close() {
            cleanupResources();
        }
    }
}
