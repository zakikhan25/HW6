
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Complete implementation of a Priority Queue using min-heap
 *
 ********************************************************************/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * Priority Queue implementation using a min-heap
 */
class PriorityQueue<E, P> {
    private static final int DEFAULT_CAPACITY = 10;
    final Comparator<P> comparator;
    final ArrayList<Node> tree;

    /* Constructors */
    public PriorityQueue() {
        this(DEFAULT_CAPACITY, (a, b) -> ((Comparable<P>) a).compareTo(b));
    }

    public PriorityQueue(int capacity, Comparator<P> comparator) {
        tree = new ArrayList<>(capacity);
        this.comparator = comparator;
    }

    /* Basic Operations */
    public int size() { return tree.size(); }
    public boolean isEmpty() { return tree.isEmpty(); }
    public void clear() { tree.clear(); }
    public Node offer(E e, P p) { return add(e, p); }

    /**
     * Retrieves the head of the queue without removing it
     */
    public Node peek() {
        return tree.isEmpty() ? null : tree.get(0);
    }

    /**
     * Adds an element to the priority queue
     */
    public Node add(E e, P priority) {
        Node newNode = new Node(e, priority, tree.size());
        tree.add(newNode);
        pullUp(tree.size() - 1);
        return newNode;
    }

    /**
     * Checks if queue contains element
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
     * Removes and returns the head of the queue
     */
    public Node poll() {
        if (tree.isEmpty()) return null;
        
        Node head = tree.get(0);
        head.markRemoved();
        
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

    /* Heap maintenance methods */
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
        while (i > 0 && 
               comparator.compare(tree.get(parent(i)).priority, tree.get(i).priority) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    /* Helper methods */
    int leftChild(int i) { return 2 * i + 1; }
    int rightChild(int i) { return 2 * i + 2; }
    int parent(int i) { return (i - 1) / 2; }

    void swap(int i, int j) {
        Node temp = tree.get(i);
        tree.set(i, tree.get(j));
        tree.set(j, temp);
        tree.get(i).idx = i;
        tree.get(j).idx = j;
    }

    /**
     * Node class representing elements in the priority queue
     */
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
            if (removed) throw new IllegalStateException("Node is removed");
            PriorityQueue.this.remove(this);
        }
    }
}
