package calculator.ast;

public class BooleanExpression extends Expression { };

class UnaryBooleanExpression extends BooleanExpression {
    private final Node value;

    public UnaryBooleanExpression(Node value) {
        this.value = value;
    }

    public final Node getValue() { return this.value; }
}

class BinaryBooleanExpression extends BooleanExpression {
    private final Node left;
    private final Node right;

    public BinaryBooleanExpression(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public final Node getLeft() { return this.left; }
    public final Node getRight() { return this.right; }
}

class NotExpression extends UnaryBooleanExpression {
    public NotExpression(Node value) {
        super(value);
    }
}

class AndExpression extends BinaryBooleanExpression {
    public AndExpression(Node left, Node right) {
        super(left, right);
    }
}

class OrExpression extends BinaryBooleanExpression {
    public OrExpression(Node left, Node right) {
        super(left, right);
    }
}