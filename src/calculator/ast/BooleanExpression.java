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

class GreaterThanExpression extends BinaryExpression {
    public GreaterThanExpression(Node left, Node right) {
        super(left, right);
    }
}

class GreaterThanOrEqualToExpression extends BinaryExpression {
    public GreaterThanOrEqualToExpression(Node left, Node right) {
        super(left, right);
    }
}

class LessThanExpression extends BinaryExpression {
    public LessThanExpression(Node left, Node right) {
        super(left, right);
    }
}

class LessThanOrEqualToExpression extends BinaryExpression {
    public LessThanOrEqualToExpression(Node left, Node right) {
        super(left, right);
    }
}

class EqualToExpression extends BinaryExpression {
    public EqualToExpression(Node left, Node right) {
        super(left, right);
    }
}

class NotEqualToExpression extends BinaryExpression {
    public NotEqualToExpression(Node left, Node right) {
        super(left, right);
    }
}