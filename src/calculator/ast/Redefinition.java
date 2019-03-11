package calculator.ast;

public class Redefinition extends Node {
    private final String variableName;

    public Redefinition(String variableName) {
        this.variableName = variableName;
    }

    public final String getVariableName() { return this.variableName; }
}

class BinaryRedefinition extends Redefinition {
    private final Node redefintionValue;

    public BinaryRedefinition(String variableName, Node redefintionValue) {
        super(variableName);
        this.redefintionValue = redefintionValue;
    }

    public final Node getRedefintionValue() { return this.redefintionValue; }
}

class Increment extends Redefinition {
    public Increment(String variableName) {
        super(variableName);
    }
}

class Decrement extends Redefinition {
    public Decrement(String variableName) {
        super(variableName);
    }
}

class PreIncrement extends Increment {
    public PreIncrement(String variableName) {
        super(variableName);
    }
}

class PostIncrement extends Increment {
    public PostIncrement(String variableName) {
        super(variableName);
    }
}

class PreDecrement extends Decrement {
    public PreDecrement(String variableName) {
        super(variableName);
    }
}

class PostDecrement extends Decrement {
    public PostDecrement(String variableName) {
        super(variableName);
    }
}

class PowerRedefinition extends BinaryRedefinition {
    public PowerRedefinition(String variableName, Node redefintionValue) {
        super(variableName, redefintionValue);
    }
}

class MultiplicationRedefinition extends BinaryRedefinition {
    public MultiplicationRedefinition(String variableName, Node redefintionValue) {
        super(variableName, redefintionValue);
    }
}

class DivisionRedefinition extends BinaryRedefinition {
    public DivisionRedefinition(String variableName, Node redefintionValue) {
        super(variableName, redefintionValue);
    }
}

class AdditionRedefinition extends BinaryRedefinition {
    public AdditionRedefinition(String variableName, Node redefintionValue) {
        super(variableName, redefintionValue);
    }
}

class SubtractionRedefinition extends BinaryRedefinition {
    public SubtractionRedefinition(String variableName, Node redefintionValue) {
        super(variableName, redefintionValue);
    }
}