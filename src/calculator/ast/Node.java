package calculator.ast;

public class Node { };

class Expression extends Node {
    private boolean containsRead = false;

    public final boolean getContainsRead() { return this.containsRead; }
    public final void setContainsRead(boolean containsRead) { this.containsRead = containsRead; }
};

class NewLine extends Node { };

class PrintExpr extends Node {
    private final Node value;

    public PrintExpr(Node value) {
        this.value = value;
    }

    public final Node getValue() { return this.value; }
}