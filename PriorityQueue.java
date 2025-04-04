
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Priority Queue Implementation
 *
 ********************************************************************/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

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
        
        // Move last element to root
        Node last = tree.remove(tree.size() - 1);
        tree.set(0, last);
        last.idx = 0;
        
        // Heapify down
        int current = 0;
        while (true) {
            int left = leftChild(current);
            int right = rightChild(current);
            int smallest = current;
            
            if (left < tree.size() && 
                comparator.compare(tree.get(left).priority, tree.get(smallest).priority) < 0) {
                smallest = left;
            }
            if (right < tree.size() && 
                comparator.compare(tree.get(right).priority, tree.get(smallest).priority) < 0) {
                smallest = right;
            }
            
            if (smallest == current) break;
            
            swap(current, smallest);
            current = smallest;
        }
        
        return head;
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
        
        // Swap positions
        nodeI.idx = j;
        nodeJ.idx = i;
        
        // Swap in array
        tree.set(i, nodeJ);
        tree.set(j, nodeI);
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

        private void pushDown(int i) {
            while (true) {
                int left = leftChild(i);
                int right = rightChild(i);
                int smallest = i;
                
                if (left < tree.size() && 
                    comparator.compare(tree.get(left).priority, tree.get(smallest).priority) < 0) {
                    smallest = left;
                }
                if (right < tree.size() && 
                    comparator.compare(tree.get(right).priority, tree.get(smallest).priority) < 0) {
                    smallest = right;
                }
                
                if (smallest == i) break;
                
                swap(i, smallest);
                i = smallest;
            }
        }

        public void remove() {
            if (removed) throw new IllegalStateException("Node already removed");
            PriorityQueue.this.remove(this);
        }
    }
}
