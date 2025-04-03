
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
        // Create max-heap using custom comparator
        PriorityQueue<Integer, Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        // Add all boulders to the heap
        for (int stone : boulders) {
            maxHeap.offer(stone, stone);
        }
        
        // Process until 1 or 0 boulders remain
        while (maxHeap.size() > 1) {
            int y = maxHeap.poll().value(); // Heaviest
            int x = maxHeap.poll().value(); // Second heaviest
            
            if (x != y) {
                maxHeap.offer(y - x, y - x); // Add remainder
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
        
        Collections.sort(result); // Sort alphabetically
        return result;
    }

    /**
     * Finds all pairs of numbers that sum to k.
     */
    public static ArrayList<String> pair(int[] input, int k) {
        Arrays.sort(input); // Sort array first
        ArrayList<String> result = new ArrayList<>();
        int left = 0;
        int right = input.length - 1;
        
        while (left < right) {
            int sum = input[left] + input[right];
            if (sum == k) {
                // Add formatted pair to result
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
