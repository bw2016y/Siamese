import org.daslab.NewSqlBaseLexer;
import org.daslab.NewSqlBaseParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.daslab.visitors.PrintVisitor;

public class Test {
    public static void main(String[] args) {
      /*  CharStream input = CharStreams.fromString("12");
        CalLexer  lexer = new CalLexer(input);

        CommonTokenStream tokens=new CommonTokenStream(lexer);
        CalParser parser=new CalParser(tokens);

        ParseTree tree=parser.prog();

        System.out.println(tree.getText());*/
        System.out.println("aaa");

        CharStream input = CharStreams.fromString("SELECT * FROM XXX\r\n");
        NewSqlBaseLexer lexer=new NewSqlBaseLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        NewSqlBaseParser parser = new NewSqlBaseParser(tokens);
        ParseTree tree = parser.singleStatement(); // parse
        System.out.println();
//        System.out.println(tree.toStringTree(parser));
        PrintVisitor printVisitor = new PrintVisitor();
        printVisitor.visit(tree);

    }
}
