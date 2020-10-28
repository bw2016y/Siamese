import org.apache.spark.daslab.sql.engine.InternalRow;

public class SQL7CodeGen1 {
    public Object generate(Object[] references) {
        return new GeneratedIteratorForCodegenStage2(references);
    }

    /*wsc_codegenStageId*/
    final class GeneratedIteratorForCodegenStage2 extends org.apache.spark.daslab.sql.execution.BufferedRowIterator {
        private Object[] references;
        private scala.collection.Iterator[] inputs;
        private boolean agg_initAgg_0;
        // 聚合缓存，保存着聚合中间结果是否为空
        private boolean agg_bufIsNull_0;
        // 聚合缓存，保存着聚合中间结果的值
        private long agg_bufValue_0;
        // 聚合算子的所有输入数据行
        private scala.collection.Iterator inputadapter_input_0;
        private boolean agg_agg_isNull_3_0;
        // 一个写入器，可以将结果包装为UnsafeRow对象
        private org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter[] agg_mutableStateArray_0 = new org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter[1];

        /**
         * 构造函数，需要传入references
         * @param references
         */
        public GeneratedIteratorForCodegenStage2(Object[] references) {
            this.references = references;
        }

        /**
         * 初始化函数，初始化分区号、输入、UnsafeRowWriter对象
         * @param index
         * @param inputs
         */
        public void init(int index, scala.collection.Iterator[] inputs) {
            partitionIndex = index;
            this.inputs = inputs;
            inputadapter_input_0 = inputs[0];
            agg_mutableStateArray_0[0] = new org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter(1, 0);
        }

        /**
         * 数据行不断迭代输入，更新聚合缓存的结果
         * @throws java.io.IOException
         */
        private void agg_doAggregateWithoutKey_0() throws java.io.IOException {
            // initialize aggregation buffer
            agg_bufIsNull_0 = true;
            agg_bufValue_0 = -1L;
            while (inputadapter_input_0.hasNext() && !stopEarly()) {
                InternalRow inputadapter_row_0 = (InternalRow) inputadapter_input_0.next();
                boolean inputadapter_isNull_0 = inputadapter_row_0.isNullAt(0);
                long inputadapter_value_0 = inputadapter_isNull_0 ?
                        -1L : (inputadapter_row_0.getLong(0));
                agg_doConsume_0(inputadapter_row_0, inputadapter_value_0, inputadapter_isNull_0);
                if (shouldStop()) return;
            }
        }

        /**
         * 如果agg_expr_0_0 > agg_bufValue_0，agg_bufValue_0 = agg_expr_0_0
         * 如果agg_bufValue_0不为初始值-1L，agg_bufIsNull_0为false。
         * @param inputadapter_row_0
         * @param agg_expr_0_0
         * @param agg_exprIsNull_0_0
         * @throws java.io.IOException
         */
        private void agg_doConsume_0(InternalRow inputadapter_row_0, long agg_expr_0_0, boolean agg_exprIsNull_0_0) throws java.io.IOException {
            // do aggregate
            // common sub-expressions
            // evaluate aggregate function
            agg_agg_isNull_3_0 = true;
            long agg_value_3 = -1L;
            if (!agg_bufIsNull_0 && (agg_agg_isNull_3_0 ||
                    agg_bufValue_0 > agg_value_3)) {
                agg_agg_isNull_3_0 = false;
                agg_value_3 = agg_bufValue_0;
            }
            if (!agg_exprIsNull_0_0 && (agg_agg_isNull_3_0 ||
                    agg_expr_0_0 > agg_value_3)) {
                agg_agg_isNull_3_0 = false;
                agg_value_3 = agg_expr_0_0;
            }
            // update aggregation buffer
            agg_bufIsNull_0 = agg_agg_isNull_3_0;
            agg_bufValue_0 = agg_value_3;
        }
        protected void processNext() throws java.io.IOException {
            while (!agg_initAgg_0) {
                agg_initAgg_0 = true;
                long agg_beforeAgg_0 = System.nanoTime();
                agg_doAggregateWithoutKey_0();
                ((org.apache.spark.daslab.sql.execution.metric.SQLMetric) references[1] /* aggTime */).add((System.nanoTime() - agg_beforeAgg_0) / 1000000);
                // output the result
                ((org.apache.spark.daslab.sql.execution.metric.SQLMetric) references[0] /* numOutputRows */).add(1);
                agg_mutableStateArray_0[0].reset();
                agg_mutableStateArray_0[0].zeroOutNullBytes();
                if (agg_bufIsNull_0) {
                    agg_mutableStateArray_0[0].setNullAt(0);
                } else {
                    agg_mutableStateArray_0[0].write(0, agg_bufValue_0);
                }
                append((agg_mutableStateArray_0[0].getRow()));
            }
        }
    }

    public static void main(String[] args) {

    }
}
