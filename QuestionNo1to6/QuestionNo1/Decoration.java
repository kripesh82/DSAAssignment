// You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with 
// one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating 
// each venue with a certain theme varies. 
// The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example, 
// costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of 
// decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering 
// to the adjacency constraint. 
// For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One 
// possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of 
// 1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of 
// 3 + 2 = 5. 
// Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while 
// satisfying the adjacency constraint. 
// Please note that the costs are positive integers. 
// Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] Output: 7  
// Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 + 
// 6 + 1 = 7.






// Solution
/*
 * This program calculates the minimum cost to decorate a series of venues with different themes,
 * ensuring that adjacent venues do not have the same theme. The cost of decorating each venue with
 * a particular theme is represented by an n x k cost matrix, where n is the number of venues and k
 * is the number of available themes. The program utilizes dynamic programming to find the optimal
 * arrangement of themes for each venue while considering the adjacency constraint.
 */

 package QuestionNo1to6.QuestionNo1;

 public class Decoration {
 
     // Function to find the minimum cost to decorate all venues
     public static int minCost(int[][] costs) {
         // Check for invalid input
         if (costs == null || costs.length == 0 || costs[0].length == 0) {
             return 0;
         }
 
         // Get the number of venues (n) and available themes (k)
         int n = costs.length;
         int k = costs[0].length;
 
         // Initialize the dynamic programming table
         // dp[i][j] represents the minimum cost to decorate the first i venues
         // where the ith venue is decorated with theme j
         int[][] dp = new int[n][k];
 
         // Initialize the base case for the first venue
         for (int i = 0; i < k; i++) {
             dp[0][i] = costs[0][i];
         }
 
         // Dynamically calculate the minimum cost for each venue
         for (int i = 1; i < n; i++) {
             for (int j = 0; j < k; j++) {
                 int minCost = Integer.MAX_VALUE;
                 // Iterate through all possible previous themes
                 for (int l = 0; l < k; l++) {
                     // Ensure that adjacent venues do not have the same theme
                     if (l != j) {
                         // Update the minimum cost considering the current and previous themes
                         minCost = Math.min(minCost, dp[i - 1][l] + costs[i][j]);
                     }
                 }
                 // Assign the minimum cost for the current venue and theme
                 dp[i][j] = minCost;
             }
         }
 
         // Find the minimum cost from the last venue
         int minTotalCost = Integer.MAX_VALUE;
         for (int i = 0; i < k; i++) {
             minTotalCost = Math.min(minTotalCost, dp[n - 1][i]);
         }
 
         // Return the minimum total cost to decorate all venues
         return minTotalCost;
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
         int minCost = Decoration.minCost(costs); 
         System.out.println("Minimum cost: " + minCost); 
     }
 }
 