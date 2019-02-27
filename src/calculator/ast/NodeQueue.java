package calculator.ast;

import java.util.Queue;

public class NodeQueue extends Node {
    private Queue<Node> nodeQueue;
    
    public NodeQueue(Queue<Node> nodeQueue) {
        this.nodeQueue = nodeQueue;
    }

    public final void push(Node node) { nodeQueue.add(node); }
    public final Node peek() { return nodeQueue.peek(); }
    public final Node remove() { return nodeQueue.remove(); }
    public final boolean isEmpty() { return nodeQueue.isEmpty(); }
}

class StartNodeQueue extends NodeQueue {
    public StartNodeQueue(Queue<Node> nodeQueue) {
        super(nodeQueue);
    }
}

class StatementNodeQueue extends NodeQueue {
    public StatementNodeQueue(Queue<Node> nodeQueue) {
        super(nodeQueue);
    }
}