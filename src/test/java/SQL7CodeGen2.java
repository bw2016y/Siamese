import org.apache.spark.daslab.sql.engine.InternalRow;
import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow;

import java.util.ArrayList;

public class SQL7CodeGen2 {
    public Object generate(Object[] references) {
        return new GeneratedIteratorForCodegenStage1(references);
    }

    /*wsc_codegenStageId*/
    final class GeneratedIteratorForCodegenStage1 extends org.apache.spark.daslab.sql.execution.BufferedRowIterator {
        private Object[] references;
        private scala.collection.Iterator[] inputs;
        private boolean agg_initAgg_0;
        private boolean agg_bufIsNull_0;
        private long agg_bufValue_0;
        private boolean agg_agg_isNull_2_0;
        private ArrayList<Integer> arrayList = new ArrayList();
        private org.apache.spark.util.random.BernoulliCellSampler<UnsafeRow>[] aqpsample_mutableStateArray_0 = (org.apache.spark.util.random.BernoulliCellSampler<UnsafeRow>[])new org.apache.spark.util.random.BernoulliCellSampler[1];
        //原本的代码，因为创建泛型数组而不能通过编译
//        private org.apache.spark.util.random.BernoulliCellSampler<UnsafeRow>[] aqpsample_mutableStateArray_0 = new org.apache.spark.util.random.BernoulliCellSampler<UnsafeRow>[1];
        private scala.collection.Iterator[] scan_mutableStateArray_0 = new scala.collection.Iterator[1];
        private org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter[] aqpsample_mutableStateArray_1 = new org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter[4];

        public GeneratedIteratorForCodegenStage1(Object[] references) {
            this.references = references;
        }

        public void init(int index, scala.collection.Iterator[] inputs) {
            partitionIndex = index;
            this.inputs = inputs;

            scan_mutableStateArray_0[0] = inputs[0];
            aqpsample_mutableStateArray_0[0] = new org.apache.spark.util.random.BernoulliCellSampler<UnsafeRow>(0.0,0.3,false);
            aqpsample_mutableStateArray_0[0].setSeed(406L+partitionIndex);
            aqpsample_mutableStateArray_1[0] = new org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter(4, 96);
            aqpsample_mutableStateArray_1[1] = new org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter(1, 0);
            aqpsample_mutableStateArray_1[2] = new org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter(1, 0);
            aqpsample_mutableStateArray_1[3] = new org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter(1, 0);

        }

        private void agg_doAggregateWithoutKey_0() throws java.io.IOException {
            // initialize aggregation buffer
            agg_bufIsNull_0 = true;
            agg_bufValue_0 = -1L;

            while (scan_mutableStateArray_0[0].hasNext()) {
                InternalRow scan_row_0 = (InternalRow) scan_mutableStateArray_0[0].next();
                ((org.apache.spark.daslab.sql.execution.metric.SQLMetric) references[0] /* numOutputRows */).add(1);
                if(aqpsample_mutableStateArray_0[0].sample()!=0 ){
                    ((org.apache.spark.daslab.sql.execution.metric.SQLMetric) references[1] /* numOutputRows */).add(1);

                    boolean scan_isNull_0 = scan_row_0.isNullAt(0);
                    long scan_value_0 = scan_isNull_0 ?
                            -1L : (scan_row_0.getLong(0));

                    agg_doConsume_0(scan_value_0, scan_isNull_0);

                }
                if (shouldStop()) return;
            }

        }

        private void agg_doConsume_0(long agg_expr_0_0, boolean agg_exprIsNull_0_0) throws java.io.IOException {
            // do aggregate
            // common sub-expressions

            // evaluate aggregate function
            agg_agg_isNull_2_0 = true;
            long agg_value_2 = -1L;

            if (!agg_bufIsNull_0 && (agg_agg_isNull_2_0 ||
                    agg_bufValue_0 > agg_value_2)) {
                agg_agg_isNull_2_0 = false;
                agg_value_2 = agg_bufValue_0;
            }

            if (!agg_exprIsNull_0_0 && (agg_agg_isNull_2_0 ||
                    agg_expr_0_0 > agg_value_2)) {
                agg_agg_isNull_2_0 = false;
                agg_value_2 = agg_expr_0_0;
            }
            // update aggregation buffer
            agg_bufIsNull_0 = agg_agg_isNull_2_0;
            agg_bufValue_0 = agg_value_2;

        }

        protected void processNext() throws java.io.IOException {
            while (!agg_initAgg_0) {
                agg_initAgg_0 = true;
                long agg_beforeAgg_0 = System.nanoTime();
                agg_doAggregateWithoutKey_0();
                ((org.apache.spark.daslab.sql.execution.metric.SQLMetric) references[3] /* aggTime */).add((System.nanoTime() - agg_beforeAgg_0) / 1000000);

                // output the result

                ((org.apache.spark.daslab.sql.execution.metric.SQLMetric) references[2] /* numOutputRows */).add(1);
                aqpsample_mutableStateArray_1[3].reset();

                aqpsample_mutableStateArray_1[3].zeroOutNullBytes();

                if (agg_bufIsNull_0) {
                    aqpsample_mutableStateArray_1[3].setNullAt(0);
                } else {
                    aqpsample_mutableStateArray_1[3].write(0, agg_bufValue_0);
                }
                append((aqpsample_mutableStateArray_1[3].getRow()));
            }
        }

    }

    public static void main(String[] args) {

    }
}
