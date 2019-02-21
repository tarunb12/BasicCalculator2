package calculator;

import calculator.antlr4.CalculatorBaseVisitor;
import calculator.antlr4.CalculatorParser;

import java.lang.Override;

public class CalculatorEvaluationVisitor extends CalculatorBaseVisitor<Node> {

    @Override
    public Node visitProg(CalculatorParser.ProgContext ctx) {
        return visit(ctx.stat());
    }

    @Override
    public Node visitNumber(CalculatorParser.NumberContext ctx) {
        return new Number(Double.parseDouble(ctx.num().toString()));
    }

    @Override
    public Node visitParenthesis(CalculatorParser.ParenthesisContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Node visitAdd(CalculatorParser.ParenthesisContext ctx) {
        return new AddExpression(ctx.expr(0), ctx.expr(1));
    }
    

}