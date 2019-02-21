package calculator;

//
// Binary Expressions
//

interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
}

//
// Addition
//
class AddExpression implements BinaryExpression {
    private final Expression left;
    private final Expression right;

    public AddExpression(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    public final Expression getLeft() { return this.left; }
    public final Expression getRight() { return this.right; }
}

//
// Subtraction
//
class SubtractExpression implements BinaryExpression {
    private final Expression left;
    private final Expression right;

    public SubtractExpression(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    public final Expression getLeft() { return this.left; }
    public final Expression getRight() { return this.right; }
}

//
// Multiplication
//
class MultiplyExpression implements BinaryExpression {
    private final Expression left;
    private final Expression right;

    public MultiplyExpression(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    public final Expression getLeft() { return this.left; }
    public final Expression getRight() { return this.right; }
}

//
// Division
//
class DivideExpression implements BinaryExpression {
    private final Expression left;
    private final Expression right;

    public DivideExpression(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    public final Expression getLeft() { return this.left; }
    public final Expression getRight() { return this.right; }
}