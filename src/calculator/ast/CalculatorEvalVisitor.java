package calculator.ast;

import calculator.antlr.CalculatorBaseVisitor;
import calculator.antlr.CalculatorParser;
import calculator.antlr.CalculatorParser.NewLineContext;
import calculator.antlr.CalculatorParser.SubtractContext;
import calculator.ast.ASTVisitor;
import calculator.ast.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CalculatorEvalVisitor extends CalculatorBaseVisitor<Node> {

    @Override
    public Node visitProg(CalculatorParser.ProgContext ctx) {
        List<CalculatorParser.StartContext> ctxs = ctx.start();
        NodeQueue nodeQueue = new NodeQueue();
        nodeQueue.init();
        int i = 0;
        for (CalculatorParser.StartContext context : ctxs) {
            nodeQueue.push(visit(context));
        }
        return nodeQueue;
    }

    @Override
    public Node visitPrintExpr(CalculatorParser.PrintExprContext ctx) {
        return new PrintExpr(visit(ctx.expr()));
    }

    @Override
    public Node visitNewLine(CalculatorParser.NewLineContext ctx) {
        return new NewLine();
    }
    
    @Override
    public Node visitMultiply(CalculatorParser.MultiplyContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new MultiplyExpression(left, right);
    }

    @Override
    public Node visitDivide(CalculatorParser.DivideContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new DivideExpression(left, right);
    }

    @Override 
    public Node visitAdd(CalculatorParser.AddContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new AddExpression(left, right);
    }

    @Override
    public Node visitSubtract(SubtractContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new SubtractExpression(left, right);
    }

    @Override
    public Node visitNumber(CalculatorParser.NumberContext ctx) {
        double value = Double.parseDouble(ctx.NUM().getText());
        return new Number(value);
    }

    @Override
    public Node visitParenthesis(CalculatorParser.ParenthesisContext ctx) {
        Node expr = visit(ctx.expr());
        return expr;
    }
}