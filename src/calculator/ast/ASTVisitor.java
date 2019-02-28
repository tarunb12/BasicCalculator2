package calculator.ast;

public abstract class ASTVisitor<T> {
    public abstract T visit(StartNodeQueue node);
    public abstract T visit(PrintExpr node);
    public abstract T visit(NewLine node);
    public abstract T visit(VariableDefinition node);
    public abstract T visit(UnaryMinusExpression node);
    public abstract T visit(PowerExpression node);
    public abstract T visit(ExponentialFunction node);
    public abstract T visit(LogarithmFunction node);
    public abstract T visit(SquareRootFunction node);
    public abstract T visit(SineFunction node);
    public abstract T visit(CosineFunction node);
    public abstract T visit(NotExpression node);
    public abstract T visit(AndExpression node);
    public abstract T visit(OrExpression or);
    public abstract T visit(MultiplyExpression node);
    public abstract T visit(DivideExpression node);
    public abstract T visit(AddExpression node);
    public abstract T visit(SubtractExpression node);
    public abstract T visit(Number node);
    public abstract T visit(Variable node);
    public abstract T visit(Read node);
    public abstract T visit(Text node);
    public abstract T visit(Parenthesis node);
    public abstract T visit(StatementNodeQueue node);
    public abstract T visit(GreaterThanExpression node);
    public abstract T visit(GreaterThanOrEqualToExpression node);
    public abstract T visit(LessThanExpression node);
    public abstract T visit(LessThanOrEqualToExpression node);
    public abstract T visit(EqualToExpression node);
    public abstract T visit(NotEqualToExpression node);
    public abstract T visit(ErrorNode node);
    // Cast all nodes of type Node, too general of a class to do anything with
    public T visit(Node node) {
        if (node instanceof StartNodeQueue) return visit((StartNodeQueue) node);
        else if (node instanceof PrintExpr) return visit((PrintExpr) node);
        else if (node instanceof NewLine) return visit((NewLine) node);
        else if (node instanceof VariableDefinition) return visit((VariableDefinition) node);
        else if (node instanceof UnaryMinusExpression) return visit((UnaryMinusExpression) node);
        else if (node instanceof PowerExpression) return visit((PowerExpression) node);
        else if (node instanceof ExponentialFunction) return visit((ExponentialFunction) node);
        else if (node instanceof LogarithmFunction) return visit((LogarithmFunction) node);
        else if (node instanceof SquareRootFunction) return visit((SquareRootFunction) node);
        else if (node instanceof SineFunction) return visit((SineFunction) node);
        else if (node instanceof CosineFunction) return visit((CosineFunction) node);
        else if (node instanceof NotExpression) return visit((NotExpression) node);
        else if (node instanceof AndExpression) return visit((AndExpression) node);
        else if (node instanceof OrExpression) return visit((OrExpression) node);
        else if (node instanceof MultiplyExpression) return visit((MultiplyExpression) node);
        else if (node instanceof DivideExpression) return visit((DivideExpression) node);
        else if (node instanceof AddExpression) return visit((AddExpression) node);
        else if (node instanceof SubtractExpression) return visit((SubtractExpression) node);
        else if (node instanceof Number) return visit((Number) node);
        else if (node instanceof Variable) return visit((Variable) node);
        else if (node instanceof Read) return visit((Read) node);
        else if (node instanceof Text) return visit((Text) node);
        else if (node instanceof Parenthesis) return visit((Parenthesis) node);
        else if (node instanceof StatementNodeQueue) return visit((StatementNodeQueue) node);
        else if (node instanceof GreaterThanExpression) return visit((GreaterThanExpression) node);
        else if (node instanceof GreaterThanOrEqualToExpression) return visit((GreaterThanOrEqualToExpression) node);
        else if (node instanceof LessThanExpression) return visit((LessThanExpression) node);
        else if (node instanceof LessThanOrEqualToExpression) return visit((LessThanOrEqualToExpression) node);
        else if (node instanceof EqualToExpression) return visit((EqualToExpression) node);
        else if (node instanceof NotEqualToExpression) return visit((NotEqualToExpression) node);
        else return visit((ErrorNode) node);
    }
}