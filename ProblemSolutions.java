
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   This java file contains the problem solutions for the methods
 *   lastBoulder, showDuplicates, and pair methods.
 *
 ********************************************************************/

import java.util.*;

public class ProblemSolutions {
    /**
     * Solves the boulder game problem using a max-heap.
     */
    public static int lastBoulder(int[] boulders) {
        // Create max-heap with explicit Integer types for both element and priority
        PriorityQueue<Integer, Integer> maxHeap = new PriorityQueue<Integer, Integer>(
            (a, b) -> Integer.compare(b, a)  // Reverse comparator for max-heap
        );
        
        // Add all boulders to the heap (using element as its own priority)
        for (int stone : boulders) {
            maxHeap.offer(stone, stone);
        }
        
        // Process until 1 or 0 boulders remain
        while (maxHeap.size() > 1) {
            int y = maxHeap.poll().value();
            int x = maxHeap.poll().value();
            
            if (x != y) {
                maxHeap.offer(y - x, y - x);
            }
        }
        
        return maxHeap.isEmpty() ? 0 : maxHeap.poll().value();
    }

    /**
     * Finds and returns sorted list of duplicate strings.
     */
    public static ArrayList<String> showDuplicates(ArrayList<String> input) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        
        // Count frequency of each string
        for (String s : input) {
            frequencyMap.put(s, frequencyMap.getOrDefault(s, 0) + 1);
        }
        
        // Add strings with frequency > 1 to result
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > 1) {
                result.add(entry.getKey());
            }
        }
        
        Collections.sort(result);
        return result;
    }

    /**
     * Finds all pairs of numbers that sum to k.
     */
    public static ArrayList<String> pair(int[] input, int k) {
        Arrays.sort(input);
        ArrayList<String> result = new ArrayList<>();
        int left = 0;
        int right = input.length - 1;
        
        while (left < right) {
            int sum = input[left] + input[right];
            if (sum == k) {
                result.add("(" + input[left] + ", " + input[right] + ")");
                // Skip duplicates
                while (left < right && input[left] == input[left + 1]) left++;
                while (left < right && input[right] == input[right - 1]) right--;
                left++;
                right--;
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }
        
        return result;
    }
}
