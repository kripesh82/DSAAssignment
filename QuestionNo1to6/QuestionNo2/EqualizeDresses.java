// You are the manager of a clothing manufacturing factory with a production line of super sewing machines. The 
// production line consists of n super sewing machines placed in a line. Initially, each sewing machine has a certain 
// number of dresses or is empty. 
// For each move, you can select any m (1 <= m <= n) consecutive sewing machines on the production line and pass 
// one dress from each selected sewing machine to its adjacent sewing machine simultaneously. 
// Your goal is to equalize the number of dresses in all the sewing machines on the production line. You need to 
// determine the minimum number of moves required to achieve this goal. If it is not possible to equalize the number 
// of dresses, return -1. 
// Input: [1,0,5] 
// Output: 2 
// Example 1: 
// Imagine you have a production line with the following number of dresses in each sewing machine: [1,0,5]. The 
// production line has 5 sewing machines. 
// Here's how the process works: 
// 1. Initial state: [1,0,5] 
// 2. Move 1: Pass one dress from the third sewing machine to the first sewing machine, resulting in [1,1,4] 
// 3. Move 2: Pass one dress from the second sewing machine to the first sewing machine, and from third to 
// first sewing Machine [2,1,3] 
// 4. Move 3: Pass one dress from the third sewing machine to the second sewing machine, resulting in [2,2,2] 
// After these 3 moves, the number of dresses in each sewing machine is equalized to 2. Therefore, the minimum 
// number of moves required to equalize the number of dresses is 3.


// Solution

/*
 * This program calculates the minimum number of moves required to equalize the number of dresses 
 * in a production line of super sewing machines. The goal is to distribute the dresses evenly 
 * among all sewing machines. Each move involves selecting a consecutive group of sewing machines 
 * and passing one dress from each selected machine to its adjacent machine. If it is impossible 
 * to equalize the number of dresses, the program returns -1.
 * 
 * Algorithm:
 * 1. Calculate the total number of dresses in all sewing machines.
 * 2. If the total number of dresses cannot be evenly distributed among the machines (i.e., the 
 *    remainder of total dresses divided by the number of machines is not zero), return -1.
 * 3. Determine the target number of dresses per machine, which is the total dresses divided by 
 *    the number of machines.
 * 4. Initialize the number of moves required to 0 and set the current index to 0.
 * 5. Iterate until all dresses are equalized:
 *    a. Find the next sewing machine with excess dresses.
 *    b. If all machines have equal or fewer dresses than the target, exit the loop.
 *    c. Calculate the excess dresses and redistribute them by passing one dress from the machine
 *       with excess to its adjacent machine.
 * 6. Return the total number of moves required to equalize the dresses.
 */


 package QuestionNo1to6.QuestionNo2;

 public class EqualizeDresses {
     
     // Function to find the minimum number of moves required to equalize the dresses
     public static int minMovesToEqualize(int[] dresses) {
         // Calculate the total number of dresses
         int totalDresses = 0;
         for (int dress : dresses) {
             totalDresses += dress;
         }
 
         // Get the number of sewing machines
         int n = dresses.length;
         
         // Check if equal distribution is possible
         if (totalDresses % n != 0) {
             return -1;
         }
 
         // Calculate the target number of dresses per machine
         int target = totalDresses / n;
         int moves = 0;
         int currentIndex = 0;
 
         // Iterate until all dresses are equalized
         while (!areEqualized(dresses, target)) {
             // Find the next sewing machine with excess dresses
             while (currentIndex < n && dresses[currentIndex] <= target) {
                 currentIndex++;
             }
 
             // If all machines have equal or fewer dresses than the target, break
             if (currentIndex == n) {
                 break;
             }
 
             // Calculate excess dresses and redistribute them
             int excess = dresses[currentIndex] - target;
             moves += excess;
             dresses[currentIndex] -= excess;
             dresses[(currentIndex + 1) % n] += excess;
         }
 
         // Return the total number of moves
         return moves;
     }
 
     // Check if all dresses are equalized
     private static boolean areEqualized(int[] dresses, int target) {
         for (int dress : dresses) {
             if (dress != target) {
                 return false;
             }
         }
         return true;
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         int[] inputDresses = {1, 0, 5};
         System.out.println(minMovesToEqualize(inputDresses));
     }
 }
 