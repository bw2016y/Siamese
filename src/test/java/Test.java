import org.daslab.NewSqlBaseLexer;
import org.daslab.NewSqlBaseParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.daslab.sqlengine.plans.logical.LogicalPlan;
import org.daslab.sqlengine.plans.logical.unary.Project;
import org.daslab.sqlengine.visitors.PrintVisitor;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        //转化为ast并打印
        CharStream input = CharStreams.fromString("SELECT * FROM XXX\r\n");
        NewSqlBaseLexer lexer=new NewSqlBaseLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        NewSqlBaseParser parser = new NewSqlBaseParser(tokens);
        ParseTree tree = parser.singleStatement(); // parse
        System.out.println();
//        System.out.println(tree.toStringTree(parser));
        PrintVisitor printVisitor = new PrintVisitor();
        printVisitor.visit(tree);

        //TreeNode test
        ArrayList<String> strings = new ArrayList<>();
        strings.add("id");
        strings.add("age");
//        LogicalPlan logicalPlan = new Project(strings);
//        logicalPlan.foreach((node) -> System.out.println(node.toString()));
//        logicalPlan.collectLeaves().forEach((node) -> System.out.println(node.toString()));
    }
}
