package calculator.ast;

public class Statement extends Node { };

class IfElseStatement extends Statement {
    private final Node ifBlock;
    private final Node elseBlock;

    public IfElseStatement(Node ifBlock, Node elseBlock) {
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    public final Node getIfBlock() { return this.ifBlock; }
    public final Node getElseBlock() { return this.elseBlock; }
}