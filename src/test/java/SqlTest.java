import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.daslab.sql.Dataset;
import org.apache.spark.daslab.sql.Row;
import org.apache.spark.daslab.sql.SparkSession;
import org.apache.spark.daslab.sql.execution.datasources.json.JsonUtils;
import org.daslab.NewSqlBaseLexer;
import org.daslab.NewSqlBaseParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
//import org.daslab.sql.engine.plans.logical.LogicalPlan;
//import org.daslab.sql.engine.plans.logical.unary.Project;

import java.io.File;
import java.util.ArrayList;

public class SqlTest {
    public static void main(String[] args) {
//        //转化为ast并打印
//        CharStream input = CharStreams.fromString("SELECT * FROM XXX\r\n");
//        NewSqlBaseLexer lexer=new NewSqlBaseLexer(input);
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        NewSqlBaseParser parser = new NewSqlBaseParser(tokens);
//        ParseTree tree = parser.singleStatement(); // parse
//        System.out.println();
////        System.out.println(tree.toStringTree(parser));
//        PrintVisitor printVisitor = new PrintVisitor();
//        printVisitor.visit(tree);
//
//        //TreeNode test
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("id");
//        strings.add("age");
////        LogicalPlan logicalPlan = new Project(strings);
////        logicalPlan.foreach((node) -> System.out.println(node.toString()));
////        logicalPlan.collectLeaves().forEach((node) -> System.out.println(node.toString()));
        SparkConf sparkConf = new SparkConf().setMaster("local[*]");
        SparkSession sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
//        String file = getClass().getClassLoader().getResource("student.json").getFile();
//        System.out.println(file);
//        String absolutePath = (new File(file)).getAbsolutePath();
        Dataset<Row> dataFrame1 = sparkSession.read().json("src/test/resources/student.json");
        dataFrame1.createOrReplaceGlobalTempView("student");
        Dataset<Row> dataFrame2 = sparkSession.read().json("src/test/resources/teacher.json");
        dataFrame2.createOrReplaceGlobalTempView("teacher");

        Dataset<Row> result = sparkSession.sql("select *,2 from global_temp.student s join global_temp.teacher t on s.teacher=t.name where s.age >=18 ");
        result.foreach(new ForeachFunction<Row>() {
            @Override
            public void call(Row row) throws Exception {
                System.out.println(row);
            }
        });

    }

}
