package calculator.ast;

public abstract class ASTVisitor<T> {
    public abstract T visit(NodeQueue node);
    public abstract T visit(PrintExpr node);
    public abstract T visit(NewLine node);
    public abstract T visit(UnaryMinusExpression node);
    public abstract T visit(PowerExpression node);
    public abstract T visit(MultiplyExpression node);
    public abstract T visit(DivideExpression node);
    public abstract T visit(AddExpression node);
    public abstract T visit(SubtractExpression node);
    public abstract T visit(Number node);
    public abstract T visit(Variable node);
    public abstract T visit(Parenthesis node);
    public abstract T visit(VariableDeclaration node);
    // Cast nodes of type Node, too general of a class to do anything with
    public T visit(Node node) {
        if (node instanceof NodeQueue) return visit((NodeQueue) node);
        else if (node instanceof PrintExpr) return visit((PrintExpr) node);
        else if (node instanceof NewLine) return visit((NewLine) node);
        else if (node instanceof UnaryMinusExpression) return visit((UnaryMinusExpression) node);
        else if (node instanceof PowerExpression) return visit((PowerExpression) node);
        else if (node instanceof MultiplyExpression) return visit((MultiplyExpression) node);
        else if (node instanceof DivideExpression) return visit((DivideExpression) node);
        else if (node instanceof AddExpression) return visit((AddExpression) node);
        else if (node instanceof SubtractExpression) return visit((SubtractExpression) node);
        else if (node instanceof Number) return visit((Number) node);
        else if (node instanceof Variable) return visit((Variable) node);
        else if (node instanceof Parenthesis) return visit((Parenthesis) node);
        else if (node instanceof VariableDeclaration) return visit((VariableDeclaration) node);
        else return visit((Number) node); // Create Error/NullAction Node for this (null)
    }
}