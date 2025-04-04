/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Final Corrected Priority Queue Implementation
 *
 ********************************************************************/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

@SuppressWarnings("unchecked")
class PriorityQueue<E, P> {
    private static final int DEFAULT_CAPACITY = 10;
    final Comparator<P> comparator;
    final ArrayList<Node> tree;

    public PriorityQueue() {
        this(DEFAULT_CAPACITY, (a, b) -> ((Comparable<P>) a).compareTo(b));
    }

    public PriorityQueue(int capacity, Comparator<P> comparator) {
        tree = new ArrayList<>(capacity);
        this.comparator = comparator;
    }

    public int size() { return tree.size(); }
    public boolean isEmpty() { return tree.isEmpty(); }
    public void clear() { tree.clear(); }
    public Node offer(E e, P p) { return add(e, p); }

    public Node peek() {
        return tree.isEmpty() ? null : tree.get(0);
    }

    public Node add(E e, P priority) {
        Node newNode = new Node(e, priority, tree.size());
        tree.add(newNode);
        pullUp(tree.size() - 1);
        return newNode;
    }

    public boolean contains(E e) {
        for (Node node : tree) {
            if (node.value.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public Node remove() {
        if (tree.isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        return poll();
    }

    public Node poll() {
        if (tree.isEmpty()) return null;
        
        Node head = tree.get(0);
        if (tree.size() == 1) {
            tree.remove(0);
            return head;
        }
        
        Node last = tree.remove(tree.size() - 1);
        tree.set(0, last);
        last.idx = 0;
        pushDown(0);
        
        return head;
    }

    private void pushDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < tree.size() && 
            comparator.compare(tree.get(left).priority, tree.get(smallest).priority) < 0) {
            smallest = left;
        }
        if (right < tree.size() && 
            comparator.compare(tree.get(right).priority, tree.get(smallest).priority) < 0) {
            smallest = right;
        }

        if (smallest != i) {
            swap(i, smallest);
            pushDown(smallest);
        }
    }

    private void pullUp(int i) {
        while (i > 0) {
            int parent = parent(i);
            if (comparator.compare(tree.get(parent).priority, tree.get(i).priority) <= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    int leftChild(int i) { return 2 * i + 1; }
    int rightChild(int i) { return 2 * i + 2; }
    int parent(int i) { return (i - 1) / 2; }

    void swap(int i, int j) {
        Node nodeI = tree.get(i);
        Node nodeJ = tree.get(j);
        
        nodeI.idx = j;
        nodeJ.idx = i;
        
        tree.set(i, nodeJ);
        tree.set(j, nodeI);
    }

    public void remove(Node node) {
        if (node.removed || node.idx >= tree.size() || tree.get(node.idx) != node) {
            throw new IllegalStateException("Node is not in the queue");
        }
        
        if (node.idx == tree.size() - 1) {
            tree.remove(node.idx);
        } else {
            Node last = tree.get(tree.size() - 1);
            swap(node.idx, tree.size() - 1);
            tree.remove(tree.size() - 1);
            node.markRemoved();
            
            if (last.idx > 0 && 
                comparator.compare(tree.get(parent(last.idx)).priority, last.priority) > 0) {
                pullUp(last.idx);
            } else {
                pushDown(last.idx);
            }
        }
    }

    public class Node {
        final E value;
        P priority;
        int idx;
        boolean removed = false;

        public Node(E value, P priority, int idx) {
            this.value = value;
            this.priority = priority;
            this.idx = idx;
        }

        public E value() { return value; }
        public P priority() { return priority; }
        public boolean isValid() { return !removed; }
        void markRemoved() { removed = true; }

        public void changePriority(P newPriority) {
            if (removed) throw new IllegalStateException("Node is removed");
            int cmp = comparator.compare(newPriority, priority);
            priority = newPriority;
            if (cmp < 0) pullUp(idx);
            else if (cmp > 0) pushDown(idx);
        }

        public void remove() {
            PriorityQueue.this.remove(this);
        }
    }
}
