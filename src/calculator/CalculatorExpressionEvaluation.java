package calculator;


class EvaluateExpression extends ASTVisitor<Double> {
    @Override
    public Double visit(UnaryMinusExpression node) {
        return -1.0 * visit(node.getValue());
    }

    @Override
    public Double visit(MultiplyExpression node) {
        return visit(node.getLeft()) * visit(node.getRight()); 
    }

    @Override
    public Double visit(DivideExpression node) {
        return visit(node.getLeft()) / visit(node.getRight()); 
    }

    @Override
    public Double visit(AddExpression node) {
        return visit(node.getLeft()) + visit(node.getRight()); 
    }

    @Override
    public Double visit(SubtractExpression node) {
        return visit(node.getLeft()) - visit(node.getRight()); 
    }
}