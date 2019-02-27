package calculator.ast;

public class Node { };

class ErrorNode extends Node { };

class Expression extends Node { };

class NewLine extends Node { };

class PrintExpr extends Node {
    private final Node value;

    public PrintExpr(Node value) {
        this.value = value;
    }

    public final Node getValue() { return this.value; }
}