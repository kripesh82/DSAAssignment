// You are given a 2D grid representing a maze in a virtual game world. The grid is of size m x n and consists of 
// different types of cells: 
// 'P' represents an empty path where you can move freely. 'W' represents a wall that you cannot pass through. 'S' 
// represents the starting point. Lowercase letters represent hidden keys. Uppercase letters represent locked doors. 
// You start at the starting point 'S' and can move in any of the four cardinal directions (up, down, left, right) to 
// adjacent cells. However, you cannot walk through walls ('W'). 
// As you explore the maze, you may come across hidden keys represented by lowercase letters. To unlock a door 
// represented by an uppercase letter, you need to collect the corresponding key first. Once you have a key, you can 
// pass through the corresponding locked door. 
// For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the English 
// alphabet in the maze. This means that there is exactly one key for each door, and one door for each key. The letters 
// used to represent the keys and doors follow the English alphabet order. 
// Your task is to find the minimum number of moves required to collect all the keys. If it is impossible to collect all 
// the keys and reach the exit, return -1. 
// Example: 
// Input: grid = [ ["S","P","q","P","P"], ["W","W","W","P","W"], ["r","P","Q","P","R"]]  
// Output: 8 
// The goal is to Collect all key

/*
 * This program solves a maze represented by a 2D grid to collect all the keys and reach the exit.
 * The maze consists of different types of cells: 'P' represents an empty path where the player can move freely,
 * 'W' represents a wall that cannot be passed through, and 'S' represents the starting point.
 * Lowercase letters represent hidden keys, and uppercase letters represent locked doors.
 * The task is to find the minimum number of moves required to collect all the keys and reach the exit.
 */

 package QuestionNo1to6.QuestionNo4;

 import java.util.*;
 
 public class MazeSolver {
 
     private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
     private static final char START = 'S';
     private static final char WALL = 'W';
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         char[][] maze = {
             {'S', 'P', 'q', 'P', 'P'},
             {'W', 'W', 'W', 'P', 'W'},
             {'r', 'P', 'Q', 'P', 'R'}
         };
 
         int minMoves = findMinimumMoves(maze);
         if (minMoves != -1) {
             System.out.println("Minimum moves required to collect all keys: " + minMoves);
         } else {
             System.out.println("It is impossible to collect all keys and reach the exit.");
         }
     }
 
     // Method to find the minimum number of moves required to collect all keys and reach the exit
     public static int findMinimumMoves(char[][] maze) {
         int rows = maze.length;
         int cols = maze[0].length;
 
         boolean[][] visited = new boolean[rows][cols];
         Queue<Point> queue = new LinkedList<>();
 
         // Find the starting point
         Point start = findStart(maze);
         if (start == null) {
             return -1; // Starting point not found
         }
 
         visited[start.x][start.y] = true;
         queue.offer(start);
 
         int totalKeys = countKeys(maze);
         int keysCollected = 0;
         int moves = 0;
 
         // Perform BFS traversal to collect keys and reach the exit
         while (!queue.isEmpty()) {
             int size = queue.size();
 
             for (int i = 0; i < size; i++) {
                 Point current = queue.poll();
 
                 // Check if all keys are collected
                 if (keysCollected == totalKeys) {
                     return moves; // All keys collected, return the number of moves
                 }
 
                 // Explore neighboring cells
                 for (int[] dir : DIRECTIONS) {
                     int newX = current.x + dir[0];
                     int newY = current.y + dir[1];
 
                     // Check if the move is valid
                     if (isValidMove(maze, newX, newY, visited)) {
                         visited[newX][newY] = true;
 
                         // Increment keys collected if a key is found
                         if (Character.isLowerCase(maze[newX][newY])) {
                             keysCollected++;
                         }
                         queue.offer(new Point(newX, newY));
                     }
                 }
             }
             moves++; // Increment moves after exploring all cells at current level
         }
 
         return -1; // Cannot collect all keys and reach the exit
     }
 
     // Method to find the starting point in the maze
     private static Point findStart(char[][] maze) {
         for (int i = 0; i < maze.length; i++) {
             for (int j = 0; j < maze[0].length; j++) {
                 if (maze[i][j] == START) {
                     return new Point(i, j);
                 }
             }
         }
         return null; // Starting point not found
     }
 
     // Method to count the total number of keys in the maze
     private static int countKeys(char[][] maze) {
         int count = 0;
         for (char[] row : maze) {
             for (char cell : row) {
                 if (Character.isLowerCase(cell)) {
                     count++;
                 }
             }
         }
         return count;
     }
 
     // Method to check if a move is valid
     private static boolean isValidMove(char[][] maze, int x, int y, boolean[][] visited) {
         int rows = maze.length;
         int cols = maze[0].length;
 
         return x >= 0 && x < rows && y >= 0 && y < cols && !visited[x][y] && maze[x][y] != WALL;
     }
 
     // Inner class representing a point in the maze
     private static class Point {
         int x;
         int y;
 
         Point(int x, int y) {
             this.x = x;
             this.y = y;
         }
     }
 }
 