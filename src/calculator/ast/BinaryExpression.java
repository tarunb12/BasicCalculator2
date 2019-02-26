package calculator.ast;

public class BinaryExpression extends Node {
    private Node left = new Number(0.0);
    private Node right = new Number(0.0);

    public BinaryExpression(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public final Node getLeft() { return this.left; }
    public final Node getRight() { return this.right; }
}

class AddExpression extends BinaryExpression {
    public AddExpression(Node left, Node right) {
        super(left, right);
    }
}

class SubtractExpression extends BinaryExpression {
    public SubtractExpression(Node left, Node right) {
        super(left, right);
    }
}

class MultiplyExpression extends BinaryExpression {
    public MultiplyExpression(Node left, Node right) {
        super(left, right);
    }
}

class DivideExpression extends BinaryExpression {
    public DivideExpression(Node left, Node right) {
        super(left, right);
    }
}   
