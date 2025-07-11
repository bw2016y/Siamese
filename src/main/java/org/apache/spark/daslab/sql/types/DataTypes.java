package org.apache.spark.daslab.sql.types;

import java.util.*;

import org.apache.spark.annotation.InterfaceStability;

/**
 * To get/create specific data type, users should use singleton objects and factory methods
 * provided by this class.
 *
 * @since 1.3.0
 */
@InterfaceStability.Stable
public class DataTypes {
    /**
     * Gets the StringType object.
     */
    public static final DataType StringType = StringType$.MODULE$;

    /**
     * Gets the BinaryType object.
     */
    public static final DataType BinaryType = BinaryType$.MODULE$;

    /**
     * Gets the BooleanType object.
     */
    public static final DataType BooleanType = BooleanType$.MODULE$;

    /**
     * Gets the DateType object.
     */
    public static final DataType DateType = DateType$.MODULE$;

    /**
     * Gets the TimestampType object.
     */
    public static final DataType TimestampType = TimestampType$.MODULE$;

    /**
     * Gets the CalendarIntervalType object.
     */
    public static final DataType CalendarIntervalType = CalendarIntervalType$.MODULE$;

    /**
     * Gets the DoubleType object.
     */
    public static final DataType DoubleType = DoubleType$.MODULE$;

    /**
     * Gets the FloatType object.
     */
    public static final DataType FloatType = FloatType$.MODULE$;

    /**
     * Gets the ByteType object.
     */
    public static final DataType ByteType = ByteType$.MODULE$;

    /**
     * Gets the IntegerType object.
     */
    public static final DataType IntegerType = IntegerType$.MODULE$;

    /**
     * Gets the LongType object.
     */
    public static final DataType LongType = LongType$.MODULE$;

    /**
     * Gets the ShortType object.
     */
    public static final DataType ShortType = ShortType$.MODULE$;

    /**
     * Gets the NullType object.
     */
    public static final DataType NullType = NullType$.MODULE$;

    /**
     * Creates an ArrayType by specifying the data type of elements ({@code elementType}).
     * The field of {@code containsNull} is set to {@code true}.
     */
    public static ArrayType createArrayType(DataType elementType) {
        if (elementType == null) {
            throw new IllegalArgumentException("elementType should not be null.");
        }
        return new ArrayType(elementType, true);
    }

    /**
     * Creates an ArrayType by specifying the data type of elements ({@code elementType}) and
     * whether the array contains null values ({@code containsNull}).
     */
    public static ArrayType createArrayType(DataType elementType, boolean containsNull) {
        if (elementType == null) {
            throw new IllegalArgumentException("elementType should not be null.");
        }
        return new ArrayType(elementType, containsNull);
    }

    /**
     * Creates a DecimalType by specifying the precision and scale.
     */
    public static DecimalType createDecimalType(int precision, int scale) {
        return DecimalType$.MODULE$.apply(precision, scale);
    }

    /**
     * Creates a DecimalType with default precision and scale, which are 10 and 0.
     */
    public static DecimalType createDecimalType() {
        return DecimalType$.MODULE$.USER_DEFAULT();
    }

    /**
     * Creates a MapType by specifying the data type of keys ({@code keyType}) and values
     * ({@code keyType}). The field of {@code valueContainsNull} is set to {@code true}.
     */
    public static MapType createMapType(DataType keyType, DataType valueType) {
        if (keyType == null) {
            throw new IllegalArgumentException("keyType should not be null.");
        }
        if (valueType == null) {
            throw new IllegalArgumentException("valueType should not be null.");
        }
        return new MapType(keyType, valueType, true);
    }

    /**
     * Creates a MapType by specifying the data type of keys ({@code keyType}), the data type of
     * values ({@code keyType}), and whether values contain any null value
     * ({@code valueContainsNull}).
     */
    public static MapType createMapType(
            DataType keyType,
            DataType valueType,
            boolean valueContainsNull) {
        if (keyType == null) {
            throw new IllegalArgumentException("keyType should not be null.");
        }
        if (valueType == null) {
            throw new IllegalArgumentException("valueType should not be null.");
        }
        return new MapType(keyType, valueType, valueContainsNull);
    }

    /**
     * Creates a StructField by specifying the name ({@code name}), data type ({@code dataType}) and
     * whether values of this field can be null values ({@code nullable}).
     */
    public static StructField createStructField(
            String name,
            DataType dataType,
            boolean nullable,
            Metadata metadata) {
        if (name == null) {
            throw new IllegalArgumentException("name should not be null.");
        }
        if (dataType == null) {
            throw new IllegalArgumentException("dataType should not be null.");
        }
        if (metadata == null) {
            throw new IllegalArgumentException("metadata should not be null.");
        }
        return new StructField(name, dataType, nullable, metadata);
    }

    /**
     * Creates a StructField with empty metadata.
     *
     * @see #createStructField(String, DataType, boolean, Metadata)
     */
    public static StructField createStructField(String name, DataType dataType, boolean nullable) {
        return createStructField(name, dataType, nullable, (new MetadataBuilder()).build());
    }

    /**
     * Creates a StructType with the given list of StructFields ({@code fields}).
     */
    public static StructType createStructType(List<StructField> fields) {
        return createStructType(fields.toArray(new StructField[fields.size()]));
    }

    /**
     * Creates a StructType with the given StructField array ({@code fields}).
     */
    public static StructType createStructType(StructField[] fields) {
        if (fields == null) {
            throw new IllegalArgumentException("fields should not be null.");
        }
        Set<String> distinctNames = new HashSet<>();
        for (StructField field : fields) {
            if (field == null) {
                throw new IllegalArgumentException(
                        "fields should not contain any null.");
            }

            distinctNames.add(field.name());
        }
        if (distinctNames.size() != fields.length) {
            throw new IllegalArgumentException("fields should have distinct names.");
        }

        return StructType$.MODULE$.apply(fields);
    }
}
