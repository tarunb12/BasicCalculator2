package calculator.ast;

import java.util.Queue;

public class NodeQueue extends Node {
    private Queue<Node> nodeQueue;
    
    public NodeQueue(Queue<Node> nodeQueue) {
        this.nodeQueue = nodeQueue;
    }

    public final int size() { return nodeQueue.size(); }
    public final void push(Node node) { nodeQueue.add(node); }
    public final Node peek() { return nodeQueue.peek(); }
    public final Node remove() { return nodeQueue.remove(); }
    public final boolean isEmpty() { return nodeQueue.isEmpty(); }
    public final Queue<Node> getQueue() { return this.nodeQueue; }
}

class StartNodeQueue extends NodeQueue {
    public StartNodeQueue(Queue<Node> nodeQueue) {
        super(nodeQueue);
    }
}

class TextNodeQueue extends NodeQueue {
    public TextNodeQueue(Queue<Node> nodeQueue) {
        super(nodeQueue);
    }
}