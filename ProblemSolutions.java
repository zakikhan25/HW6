/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Problem Solutions Implementation
 *
 ********************************************************************/
import java.util.*;

public class ProblemSolutions {
    /******************************************************************
     * 1️⃣ Boulder Game
     * This method simulates the "smashing boulders" game using a max-heap.
     ******************************************************************/
    public static int lastBoulder(int[] boulders) {
        // Use a max heap (reverse order priority queue)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        // Add all boulders to max heap
        for (int stone : boulders) {
            maxHeap.add(stone);
        }
        
        while (maxHeap.size() > 1) {
            int y = maxHeap.poll(); // Heaviest boulder
            int x = maxHeap.poll(); // Second heaviest

            if (x != y) {
                maxHeap.add(y - x); // Add remaining boulder
            }
        }
        
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }

    /******************************************************************
     * 2️⃣ Sorting 1 - Find Duplicates
     * This method finds and returns a sorted list of duplicates.
     ******************************************************************/
    public static ArrayList<String> showDuplicates(ArrayList<String> input) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        
        for (String s : input) {
            frequencyMap.put(s, frequencyMap.getOrDefault(s, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > 1) {
                result.add(entry.getKey());
            }
        }
        
        Collections.sort(result);
        return result;
    }

    /******************************************************************
     * 3️⃣ Sorting 2 - Pair Sum Matching
     * This method finds all unique pairs that sum to k and returns them as strings.
     ******************************************************************/
    public static ArrayList<String> pair(int[] input, int k) {
        // Sort the input array to use two-pointer technique
        Arrays.sort(input);
        ArrayList<String> result = new ArrayList<>();
        int left = 0, right = input.length - 1;

        while (left < right) {
            int sum = input[left] + input[right];
            if (sum == k) {
                result.add("(" + input[left] + ", " + input[right] + ")");

                // Skip duplicate values to avoid duplicate pairs
                int currLeft = input[left];
                int currRight = input[right];
                while (left < right && input[left] == currLeft) left++;
                while (left < right && input[right] == currRight) right--;
            } else if (sum < k) {
                left++; // Increase sum
            } else {
                right--; // Decrease sum
            }
        }

        return result;
    }
}
