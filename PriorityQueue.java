
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Priority Queue Implementation - Min-Heap
 *
 ********************************************************************/
import java.util.ArrayList;

public class PriorityQueue {
    private ArrayList<Integer> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    // Adds an element while maintaining the min-heap property
    public void add(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    // Checks if the heap contains a specific value
    public boolean contains(int value) {
        return heap.contains(value);
    }

    // Helper method to restore heap property upwards
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) >= heap.get(parentIndex)) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Swap two elements in the heap
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
