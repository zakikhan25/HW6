
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Problem Solutions Implementation
 *
 ********************************************************************/
import java.util.*;

public class ProblemSolutions {
    public static int lastBoulder(int[] boulders) {
        // Create a max heap (reversed comparator)
        PriorityQueue<Integer, Integer> maxHeap = new PriorityQueue<>(
            boulders.length,
            (a, b) -> b.compareTo(a)
        );
        
        // Add all boulders to the max heap
        for (int stone : boulders) {
            maxHeap.add(stone, stone);
        }
        
        // Process according to the game rules
        while (maxHeap.size() > 1) {
            // Get the heaviest two boulders
            int y = maxHeap.poll().value();
            int x = maxHeap.poll().value();
            
            // If they have different weights
            if (x != y) {
                // Add back the new boulder with weight y-x
                maxHeap.add(y - x, y - x);
            }
            // If equal weights, both are destroyed (do nothing)
        }
        
        // Return the last boulder or 0 if none left
        return maxHeap.isEmpty() ? 0 : maxHeap.poll().value();
    }
    
    public static ArrayList<String> showDuplicates(ArrayList<String> input) {
        // Track frequency of each string
        Map<String, Integer> freq = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        
        // Count occurrences of each string
        for (String s : input) {
            freq.put(s, freq.getOrDefault(s, 0) + 1);
        }
        
        // Add strings that appear more than once to result
        for (Map.Entry<String, Integer> e : freq.entrySet()) {
            if (e.getValue() > 1) {
                result.add(e.getKey());
            }
        }
        
        // Sort in ascending order
        Collections.sort(result);
        return result;
    }
    
    public static ArrayList<String> pair(int[] input, int k) {
        // Sort the input array
        Arrays.sort(input);
        ArrayList<String> result = new ArrayList<>();
        int left = 0, right = input.length - 1;
        
        while (left < right) {
            int sum = input[left] + input[right];
            if (sum == k) {
                // Add pair to result
                result.add("(" + input[left] + ", " + input[right] + ")");
                
                // Skip duplicates
                int currLeft = input[left];
                int currRight = input[right];
                while (left < right && input[left] == currLeft) left++;
                while (left < right && input[right] == currRight) right--;
            } else if (sum < k) {
                // Need larger sum, move left pointer
                left++;
            } else {
                // Need smaller sum, move right pointer
                right--;
            }
        }
        
        return result;
    }
}
