import org.spark.daslab.NewSqlBaseBaseVisitor;
import org.spark.daslab.NewSqlBaseParser;

public class PrintVisitor extends NewSqlBaseBaseVisitor<Object> {

    private Integer dashCount = 0;

    @Override
    public Object visitSingleStatement(NewSqlBaseParser.SingleStatementContext ctx) {
        for (Integer i = 0; i < dashCount; i++) System.out.println("---");
        System.out.println("SingleStatement");
        dashCount++;
        visit(ctx.statement());
        dashCount--;
        System.out.println("---EOF");
        return null;
    }

    @Override
    public Object visitStatementDefault(NewSqlBaseParser.StatementDefaultContext ctx) {
        for (Integer i = 0; i < dashCount; i++) System.out.print("---");
        System.out.println("Statement: StatementDefault");
        dashCount++;
        visitChildren(ctx);
//        visit(ctx.query());
        dashCount--;
        return null;
    }

    @Override
    public Object visitQuery(NewSqlBaseParser.QueryContext ctx) {
        for (Integer i = 0; i < dashCount; i++) System.out.print("---");
        System.out.println("Query");
        dashCount++;
        visitChildren(ctx);
        dashCount--;
        return null;
    }

    @Override
    public Object visitSingleInsertQuery(NewSqlBaseParser.SingleInsertQueryContext ctx) {
        for (Integer i = 0; i < dashCount; i++) System.out.print("---");
        System.out.println("QueryNoWith: SingleInsertQuery");
        dashCount++;
        visitChildren(ctx);
        dashCount--;
        return null;
    }

    @Override
    public Object visitQueryTermDefault(NewSqlBaseParser.QueryTermDefaultContext ctx) {
        for (Integer i = 0; i < dashCount; i++) System.out.print("---");
        System.out.println("QueryTerm: QueryTermDefault");
        for (Integer i = 0; i < dashCount; i++) System.out.print("---");
        System.out.println("---XXXXXX");
        dashCount++;
        visitChildren(ctx);
        dashCount--;
        return null;
    }

    @Override
    public Object visitQueryOrganization(NewSqlBaseParser.QueryOrganizationContext ctx) {
        for (Integer i = 0; i < dashCount; i++) System.out.print("---");
        System.out.println("QueryOrganization");
        dashCount++;
        visitChildren(ctx);
        dashCount--;
        return null;
    }
}
