package calculator.ast;

class NotExpression extends UnaryExpression {
    public NotExpression(Node value) {
        super(value);
    }
}

class AndExpression extends BinaryExpression {
    public AndExpression(Node left, Node right) {
        super(left, right);
    }
}

class OrExpression extends BinaryExpression {
    public OrExpression(Node left, Node right) {
        super(left, right);
    }
}