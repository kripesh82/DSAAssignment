// You are given an integer n representing the total number of individuals. Each individual is identified by a unique 
// ID from 0 to n-1. The individuals have a unique secret that they can share with others. 
// The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret 
// with any number of individuals simultaneously during specific time intervals. Each time interval is represented by 
// a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval. 
// You need to determine the set of individuals who will eventually know the secret after all the possible secret
// sharing intervals have occurred. 
// Example: 
// Input: n = 5, intervals = [(0, 2), (1, 3), (2, 4)], firstPerson = 0 
// Output: [0, 1, 2, 3, 4] 
// Explanation: 
// In this scenario, we have 5 individuals labeled from 0 to 4. 
// The secret-sharing process starts with person 0, who has the secret at time 0. At time 0, person 0 can share the 
// secret with any other person. Similarly, at time 1, person 0 can also share the secret. At time 2, person 0 shares the 
// secret again, and so on. 
// Given the intervals [(0, 2), (1, 3), (2, 4)], we can observe that during these intervals, person 0 shares the secret with 
// every other individual at least once. 
// Hence, after all the secret-sharing intervals, individuals 0, 1, 2, 3, and 4 will eventually know the secret. 


/*
 * This program determines the set of individuals who will eventually know a secret after a
 * series of secret-sharing intervals. Each individual is identified by a unique ID from 0 to n-1,
 * and the secret-sharing process starts with person 0 possessing the secret. During specific time
 * intervals represented by tuples (start, end), person 0 can share the secret with any number of
 * individuals simultaneously. The program calculates the set of individuals who will eventually
 * know the secret after all possible secret-sharing intervals have occurred.
 *
 * Example:
 * Given the total number of individuals (n) as 5 and the secret-sharing intervals as [(0, 2), (1, 3), (2, 4)],
 * with person 0 initially possessing the secret, the output should be [0, 1, 2, 3, 4]. This indicates
 * that after all the secret-sharing intervals, individuals 0, 1, 2, 3, and 4 will eventually know the secret.
 */

 package QuestionNo1to6.QuestionNo2;

 import java.util.*;
 
 public class SecretSharing {
     
     // Function to determine the set of individuals who will eventually know the secret
     public List<Integer> whoKnowsSecret(int n, int[][] intervals, int firstPerson) {
         Set<Integer> known = new HashSet<>();
         known.add(firstPerson); // Person 0 initially knows the secret
         // Iterate through each secret-sharing interval
         for (int[] interval : intervals) {
             int start = interval[0];
             int end = interval[1];
             // Add all individuals within the interval to the set of known individuals
             for (int i = start; i <= end; i++) {
                 known.add(i);
             }
         }
         // Convert the set to a list and return
         return new ArrayList<>(known);
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         SecretSharing secretSharing = new SecretSharing();
         int n = 5;
         int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
         int firstPerson = 0;
         List<Integer> result = secretSharing.whoKnowsSecret(n, intervals, firstPerson);
         
         // Print the result
         StringBuilder sb = new StringBuilder("Individuals ");
         for (int i = 0; i < result.size(); i++) {
             sb.append(result.get(i));
             if (i < result.size() - 1) {
                 sb.append(", ");
             }
         }
         sb.append(" will eventually know the secret.");
         System.out.println(sb.toString());
     }
 }
 