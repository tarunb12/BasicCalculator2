package calculator.ast;

import calculator.antlr.CalculatorBaseVisitor;
import calculator.antlr.CalculatorParser;
import calculator.antlr.CalculatorParser.SubtractContext;
import calculator.ast.ASTVisitor;
import calculator.ast.Node;

import java.util.HashMap;
import java.util.Map;

public class CalculatorEvalVisitor extends CalculatorBaseVisitor<Node> {
    // "memory" for the calculator, variable/value pairs go here
    Map<String, Double> varDefs = new HashMap<String, Double>();

    // @Override
    // public Node visitPrintExpr(CalculatorParser.PrintExprContext ctx) {
    //     ctx.expr();
    //     return new Node();
    // }

    @Override
    public Node visitMultiply(CalculatorParser.MultiplyContext ctx) {
        return new MultiplyExpression(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Node visitDivide(CalculatorParser.DivideContext ctx) {
        return new DivideExpression(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override 
    public Node visitAdd(CalculatorParser.AddContext ctx) {
        return new AddExpression(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Node visitSubtract(SubtractContext ctx) {
        return new SubtractExpression(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Node visitNumber(CalculatorParser.NumberContext ctx) {
        return new Number(Double.parseDouble(ctx.NUM().getText()));
    }
}