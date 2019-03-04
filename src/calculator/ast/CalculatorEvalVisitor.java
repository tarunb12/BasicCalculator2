package calculator.ast;

import calculator.antlr.CalculatorBaseVisitor;
import calculator.antlr.CalculatorParser.*;
import calculator.ast.ASTVisitor;
import calculator.ast.Node;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorEvalVisitor extends CalculatorBaseVisitor<Node> {

    @Override
    public Node visitProg(ProgContext ctx) {
        List<StartContext> startContexts = ctx.start();
        StartNodeQueue exprNodeQueue = new StartNodeQueue(new LinkedList<Node>());
        for (StartContext context : startContexts) {
            Node start = visit(context);
            exprNodeQueue.push(visit(context));
        }
        return exprNodeQueue;
    }

    @Override
    public Node visitPrintExpr(PrintExprContext ctx) {
        return new PrintExpr(visit(ctx.expr()));
    }

    @Override
    public Node visitFunc(FuncContext ctx) {
        String functionName = ctx.functionName().getText();
        List<String> parameters = new ArrayList<String>();
        List<ParameterContext> parameterCtx = ctx.parameter();
        for (ParameterContext context : parameterCtx) {
            parameters.add(context.VAR().getText());
        }
        StartNodeQueue exprNodeQueue = new StartNodeQueue(new LinkedList<Node>());
        List<StartContext> startContexts = ctx.start();
        for (StartContext context : startContexts) {
            exprNodeQueue.push(visit(context));
        }
        Map<String, Node> localScopeDefinitions = new HashMap<String, Node>();
        for (String parameter : parameters) {
            localScopeDefinitions.put(parameter, new Number(0.0));
        }
        ExprContext returnExpressionContext = ctx.expr();
        Node returnExpression;
        if (returnExpressionContext == null) return new Number(Double.NaN);
        returnExpression = visit(returnExpressionContext);
        if (returnExpression == null) returnExpression = new Number(Double.NaN);
        return new Function(functionName, parameters, localScopeDefinitions, exprNodeQueue, returnExpression);
    }

    @Override
    public Node visitFuncCall(FuncCallContext ctx) {
        String functionName = ctx.VAR().getText();
        List<ExprContext> ctxs = ctx.expr();
        List<Node> parameters = new ArrayList<Node>();
        for (ExprContext context : ctxs) {
            parameters.add(visit(context));
        }
        return new FunctionCall(functionName, parameters);
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
        return new ExponentialFunctionExpression(value);
    }

    @Override
    public Node visitLogarithm(LogarithmContext ctx) {
        Node value = visit(ctx.expr());
        return new LogarithmFunctionExpression(value);
    }

    @Override
    public Node visitSquareRoot(SquareRootContext ctx) {
        Node value = visit(ctx.expr());
        return new SquareRootFunctionExpression(value);
    }

    @Override
    public Node visitSine(SineContext ctx) {
        Node value = visit(ctx.expr());
        return new SineFunctionExpression(value);
    }

    @Override
    public Node visitCosine(CosineContext ctx) {
        Node value = visit(ctx.expr());
        return new CosineFunctionExpression(value);
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

    @Override
    public Node visitGreaterThan(GreaterThanContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new GreaterThanExpression(left, right);
    }

    @Override
    public Node visitGreaterThanOrEqualTo(GreaterThanOrEqualToContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new GreaterThanOrEqualToExpression(left, right);
    }

    @Override
    public Node visitLessThan(LessThanContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new LessThanExpression(left, right);
    }

    @Override
    public Node visitLessThanOrEqualTo(LessThanOrEqualToContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new LessThanOrEqualToExpression(left, right);
    }

    @Override
    public Node visitLogicalEqual(LogicalEqualContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new EqualToExpression(left, right);
    }

    @Override
    public Node visitLogicalNotEqual(LogicalNotEqualContext ctx) {
        Node left = visit(ctx.expr(0));
        Node right = visit(ctx.expr(1));
        return new NotEqualToExpression(left, right);
    }
}