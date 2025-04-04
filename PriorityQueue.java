
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Final Corrected Priority Queue Implementation
 *
 ********************************************************************/

import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("unchecked")
class PriorityQueue<E extends Comparable<E>> {
    private ArrayList<E> queue;

    /**
     * Constructs an empty priority queue.
     */
    public PriorityQueue() {
        queue = new ArrayList<>();
    }

    /**
     * Returns the number of elements in this queue.
     * @return the number of elements in this queue
     */
    public int size() {
        return queue.size();
    }

    /**
     * Returns true if this queue contains no elements.
     * @return true if this queue contains no elements
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns, but does not remove, the smallest element in this queue.
     * @return the smallest element in this queue
     * @throws IllegalStateException if this queue is empty
     */
    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue.get(0);
    }

    /**
     * Removes all of the elements from this queue.
     */
    public void clear() {
        queue.clear();
    }

    /**
     * Removes and returns the smallest element in this queue.
     * @return the smallest element in this queue
     * @throws IllegalStateException if this queue is empty
     */
    public E poll() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        E min = queue.get(0);
        int lastIdx = queue.size() - 1;
        queue.set(0, queue.get(lastIdx));
        queue.remove(lastIdx);
        if (!isEmpty()) {
            siftDown(0);
        }
        return min;
    }

    /**
     * Helper method to maintain heap invariant after removal.
     * @param idx the index to start sifting down from
     */
    private void siftDown(int idx) {
        int smallest = idx;
        int leftChild = 2 * idx + 1;
        int rightChild = 2 * idx + 2;
        int size = queue.size();

        if (leftChild < size && queue.get(leftChild).compareTo(queue.get(smallest)) < 0) {
            smallest = leftChild;
        }

        if (rightChild < size && queue.get(rightChild).compareTo(queue.get(smallest)) < 0) {
            smallest = rightChild;
        }

        if (smallest != idx) {
            E temp = queue.get(idx);
            queue.set(idx, queue.get(smallest));
            queue.set(smallest, temp);
            siftDown(smallest);
        }
    }

    /**
     * Adds the specified element to this queue.
     * The element is added at the end of the underlying array and then
     * "bubbled up" to maintain the min-heap property.
     * @param element the element to add
     */
    public void add(E element) {
        // Add element to the end of the heap
        queue.add(element);
        int index = queue.size() - 1;
        
        // Bubble up to maintain min-heap property
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (queue.get(index).compareTo(queue.get(parentIndex)) >= 0) {
                break; // Heap property satisfied
            }
            // Swap with parent
            E temp = queue.get(index);
            queue.set(index, queue.get(parentIndex));
            queue.set(parentIndex, temp);
            index = parentIndex;
        }
    }

    /**
     * Returns true if this queue contains the specified element.
     * @param element object to be checked for containment in this queue
     * @return true if this queue contains the specified element
     */
    public boolean contains(E element) {
        // Check if element exists in the heap
        for (E e : queue) {
            if (e.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of this queue.
     * @return a string representation of this queue
     */
    @Override
    public String toString() {
        return queue.toString();
    }
}
