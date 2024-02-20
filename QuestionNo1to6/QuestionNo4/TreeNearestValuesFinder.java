// You are provided with balanced binary tree with the target value k. return x number of values that are closest to the 
// given target k. provide solution in O(n) 
// Note: You have only one set of unique values x in binary search tree that are closest to the target. 
// Input:  
// K=3.8 
// x=2 
// Output: 3,4


/*
 * This program finds x number of values in a balanced binary tree that are closest to the given target k.
 * The solution is provided in O(n) time complexity.
 */

 package QuestionNo1to6.QuestionNo4;

 import java.util.ArrayList;
 import java.util.List;
 
 public class TreeNearestValuesFinder {
 
     // Definition of TreeNode class representing a node in the binary tree
     public static class TreeNode {
         TreeNode leftNode;
         TreeNode rightNode;
         int value;
 
         TreeNode(int value) {
             this.value = value;
             this.leftNode = this.rightNode = null;
         }
     }
 
     // Method to find the nearest values to the target in the binary tree
     public static void findNearestValues(TreeNode node, double target, List<Integer> nearestValues, int count) {
         if (node == null) {
             return;
         }
 
         // Traverse the left subtree
         findNearestValues(node.leftNode, target, nearestValues, count);
 
         // Add the current node's value if there are less than x nearest values found
         if (nearestValues.size() < count) {
             nearestValues.add(node.value);
         } 
         // Update nearest values if the current node's value is closer to the target
         else if (Math.abs(node.value - target) < Math.abs(nearestValues.get(0) - target)) {
             nearestValues.remove(0);
             nearestValues.add(node.value);
         }
 
         // Traverse the right subtree
         findNearestValues(node.rightNode, target, nearestValues, count);
     }
 
     // Method to get x closest values to the target in the binary tree
     public static List<Integer> getClosestValues(TreeNode node, double target, int count) {
         List<Integer> nearestValues = new ArrayList<>();
         findNearestValues(node, target, nearestValues, count);
         return nearestValues;
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         TreeNode root = new TreeNode(4);
         root.leftNode = new TreeNode(2);
         root.rightNode = new TreeNode(5);
         root.leftNode.leftNode = new TreeNode(1);
         root.leftNode.rightNode = new TreeNode(3);
         root.rightNode.rightNode = new TreeNode(6);
 
         double target = 3.8;
         int count = 2;
 
         List<Integer> closestValues = getClosestValues(root, target, count);
         System.out.println("Nearest values to the target " + target + " are: " + closestValues);
     }
 }
 