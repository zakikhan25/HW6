
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Note, additional comments provided throughout this source code
 *   is for educational purposes
 *
 ********************************************************************/

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class PriorityQueue<E,P>
 *
 * The class implements a priority queue utilizing a min heap.
 */
class PriorityQueue<E, P> {
    private static final int DEFAULT_CAPACITY = 10;
    final Comparator<P> comparator;
    final ArrayList<Node> tree;

    /* Constructors */
    public PriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(int capacity) {
        this(capacity, (a, b) -> ((Comparable<P>) a).compareTo(b));
    }

    public PriorityQueue(int capacity, Comparator<P> comparator) {
        tree = new ArrayList<>(capacity);
        this.comparator = comparator;
    }

    /* Basic Queue Operations */
    public int size() { return tree.size(); }
    public boolean isEmpty() { return tree.size() == 0; }
    public void clear() { tree.clear(); }
    public Node offer(E e, P p) { return add(e, p); }

    /**
     * Retrieves, but does not remove, the head of this queue.
     */
    public Node peek() {
        return tree.isEmpty() ? null : tree.get(0);
    }

    /**
     * Inserts the specified element into this priority queue.
     */
    public Node add(E e, P priority) {
        Node newNode = new Node(e, priority, tree.size());
        tree.add(newNode);
        pullUp(tree.size() - 1);
        return newNode;
    }

    /**
     * Returns true if this queue contains the specified element.
     */
    public boolean contains(E e) {
        for (Node node : tree) {
            if (node.value.equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves and removes the head of this queue.
     */
    public Node remove() {
        if (tree.isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        return poll();
    }

    /**
     * Retrieves and removes the head of this queue.
     */
    public Node poll() {
        if (tree.isEmpty()) return null;
        if (tree.size() == 1) {
            Node removedNode = tree.remove(0);
            removedNode.markRemoved();
            return removedNode;
        }
        
        Node head = tree.get(0);
        head.markRemoved();
        Node lastNode = tree.remove(tree.size() - 1);
        lastNode.idx = 0;
        tree.set(0, lastNode);
        pushDown(0);
        return head;
    }

    /* Heap Maintenance Methods */
    private void pushDown(int i) {
        while (true) {
            int left = leftChild(i);
            int right = rightChild(i);
            int smallest = i;

            if (left < size() && compare(tree.get(left).priority, tree.get(smallest).priority) < 0) {
                smallest = left;
            }
            if (right < size() && compare(tree.get(right).priority, tree.get(smallest).priority) < 0) {
                smallest = right;
            }
            if (smallest == i) break;
            
            swap(i, smallest);
            i = smallest;
        }
    }

    private void pullUp(int i) {
        while (i > 0 && compare(tree.get(parent(i)).priority, tree.get(i).priority) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    /* Helper Methods */
    int leftChild(int i) { return 2 * i + 1; }
    int rightChild(int i) { return 2 * i + 2; }
    int parent(int i) { return (i - 1) / 2; }
    private int compare(P a, P b) { return comparator.compare(a, b); }

    void swap(int idx1, int idx2) {
        Node node1 = tree.get(idx1);
        Node node2 = tree.get(idx2);
        node1.idx = idx2;
        node2.idx = idx1;
        tree.set(idx1, node2);
        tree.set(idx2, node1);
    }

    /* Node Class */
    public class Node {
        E value;
        P priority;
        int idx;
        boolean removed = false;

        public Node(E value, P priority, int idx) {
            this.value = value;
            this.priority = priority;
            this.idx = idx;
        }

        void markRemoved() { removed = true; }
        public E value() { return value; }
        public P priority() { return priority; }
        public boolean isValid() { return !removed; }

        public void changePriority(P newPriority) {
            if (removed) throw new IllegalStateException("Node is removed");
            int cmp = compare(newPriority, priority);
            priority = newPriority;
            if (cmp < 0) pullUp(idx);
            else if (cmp > 0) pushDown(idx);
        }

        public void remove() {
            if (removed) throw new IllegalStateException("Node is removed");
            PriorityQueue.this.remove(this);
        }
    }
}
