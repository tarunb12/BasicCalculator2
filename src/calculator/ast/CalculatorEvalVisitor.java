package calculator.ast;

import calculator.antlr.CalculatorBaseVisitor;
import calculator.antlr.CalculatorParser.*;
import calculator.ast.ASTVisitor;
import calculator.ast.Node;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorEvalVisitor extends CalculatorBaseVisitor<Node> {

    @Override
    public Node visitProg(ProgContext ctx) {
        List<StartContext> ctxs = ctx.start();
        StartNodeQueue exprNodeQueue = new StartNodeQueue(new LinkedList<Node>());
        for (StartContext context : ctxs) {
            Node start = visit(context);
            if (!(start instanceof NewLine)) exprNodeQueue.push(visit(context));
        }
        return exprNodeQueue;
    }

    @Override
    public Node visitPrintExpr(PrintExprContext ctx) {
        return new PrintExpr(visit(ctx.expr()));
    }

    @Override
    public Node visitNewLine(NewLineContext ctx) {
        return new NewLine();
    }

    @Override
    public Node visitVariableDefinition(VariableDefinitionContext ctx) {
        String variableName = ctx.VAR().getText();
        Node value = visit(ctx.expr());
        return new VariableDefinition(variableName, value);
    }

    @Override
    public Node visitUnaryMinus(UnaryMinusContext ctx) {
        Node value = visit(ctx.expr());
        return new UnaryMinusExpression(value);
    }

    @Override
    public Node visitPower(PowerContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new PowerExpression(left, right);
    }

    @Override
    public Node visitExponential(ExponentialContext ctx) {
        Node value = visit(ctx.expr());
        return new ExponentialFunction(value);
    }

    @Override
    public Node visitLogarithm(LogarithmContext ctx) {
        Node value = visit(ctx.expr());
        return new LogarithmFunction(value);
    }

    @Override
    public Node visitSquareRoot(SquareRootContext ctx) {
        Node value = visit(ctx.expr());
        return new SquareRootFunction(value);
    }

    @Override
    public Node visitSine(SineContext ctx) {
        Node value = visit(ctx.expr());
        return new SineFunction(value);
    }

    @Override
    public Node visitCosine(CosineContext ctx) {
        Node value = visit(ctx.expr());
        return new CosineFunction(value);
    }

    @Override
    public Node visitNot(NotContext ctx) {
        Node value = visit(ctx.expr());
        return new NotExpression(value);
    }

    @Override
    public Node visitAnd(AndContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new AndExpression(left, right);
    }

    @Override
    public Node visitOr(OrContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new OrExpression(left, right);
    }
    
    @Override
    public Node visitMultiply(MultiplyContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new MultiplyExpression(left, right);
    }

    @Override
    public Node visitDivide(DivideContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new DivideExpression(left, right);
    }

    @Override 
    public Node visitAdd(AddContext ctx) {
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
    public Node visitNumber(NumberContext ctx) {
        double value = Double.parseDouble(ctx.NUM().getText());
        return new Number(value);
    }

    @Override
    public Node visitVariable(VariableContext ctx) {
        String variableName = ctx.VAR().getText();
        return new Variable(variableName);
    }

    @Override
    public Node visitRead(ReadContext ctx) {
        return new Read(0.0);
    }

    @Override
    public Node visitTextStatement(TextStatementContext ctx) {
        String text = ctx.txt().getText();
        return new Text(text);
    }

    @Override
    public Node visitExprStatement(ExprStatementContext ctx) {
        Node value = visit(ctx.expr());
        return value;
    }
    
    @Override
    public Node visitParenthesis(ParenthesisContext ctx) {
        Node expr = visit(ctx.expr());
        return expr;
    }

    @Override
    public Node visitPrintExpressionText(PrintExpressionTextContext ctx) {
        List<StatementContext> ctxs = ctx.statement();
        StatementNodeQueue statementNodeQueue = new StatementNodeQueue(new LinkedList<Node>());
        for (StatementContext context : ctxs) {
            Node start = visit(context);
            statementNodeQueue.push(visit(context));
        }
        return statementNodeQueue;
    }
}