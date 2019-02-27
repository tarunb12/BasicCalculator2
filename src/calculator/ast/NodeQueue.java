package calculator.ast;

import java.util.Queue;
import java.util.LinkedList;

public class NodeQueue extends Node {
    private Queue<Node> nodeQueue;
    
    public final void init() { nodeQueue = new LinkedList<Node>(); }
    public final void push(Node node) { nodeQueue.add(node); }
    public final Node peek() { return nodeQueue.peek(); }
    public final Node remove() { return nodeQueue.remove(); }
    public final boolean isEmpty() { return nodeQueue.isEmpty(); }
}