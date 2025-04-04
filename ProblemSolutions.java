
/******************************************************************
 *
 *   Zaki Khan / 272 001
 *
 *   Correct Problem Solutions Implementation
 *
 ********************************************************************/

import java.util.*;

public class ProblemSolutions {
    public static int lastBoulder(int[] boulders) {
        PriorityQueue<Integer, Integer> maxHeap = new PriorityQueue<>(
            boulders.length,
            Collections.reverseOrder()
        );
        
        for (int stone : boulders) {
            maxHeap.add(stone, stone);
        }
        
        while (maxHeap.size() > 1) {
            int y = maxHeap.poll().value();
            int x = maxHeap.poll().value();
            if (x != y) {
                maxHeap.add(y - x, y - x);
            }
        }
        
        return maxHeap.isEmpty() ? 0 : maxHeap.poll().value();
    }

    public static ArrayList<String> showDuplicates(ArrayList<String> input) {
        Map<String, Integer> freq = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        
        for (String s : input) {
            freq.put(s, freq.getOrDefault(s, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> e : freq.entrySet()) {
            if (e.getValue() > 1) {
                result.add(e.getKey());
            }
        }
        
        Collections.sort(result);
        return result;
    }

    public static ArrayList<String> pair(int[] input, int k) {
        Arrays.sort(input);
        ArrayList<String> result = new ArrayList<>();
        int left = 0, right = input.length - 1;
        
        while (left < right) {
            int sum = input[left] + input[right];
            if (sum == k) {
                result.add("(" + input[left] + ", " + input[right] + ")");
                while (left < right && input[left] == input[left+1]) left++;
                while (left < right && input[right] == input[right-1]) right--;
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
