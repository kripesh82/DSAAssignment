// You are developing a student score tracking system that keeps track of scores from different assignments. The 
// ScoreTracker class will be used to calculate the median score from the stream of assignment scores. The class 
// should have the following methods: 
// • ScoreTracker() initializes a new ScoreTracker object. 
// • void addScore(double score) adds a new assignment score to the data stream. 
// • double getMedianScore() returns the median of all the assignment scores in the data stream. If the number 
// of scores is even, the median should be the average of the two middle scores. 
// Input: 
// ScoreTracker scoreTracker = new ScoreTracker(); 
// scoreTracker.addScore(85.5);    // Stream: [85.5] 
// scoreTracker.addScore(92.3);    // Stream: [85.5, 92.3] 
// scoreTracker.addScore(77.8);    // Stream: [85.5, 92.3, 77.8] 
// scoreTracker.addScore(90.1);    // Stream: [85.5, 92.3, 77.8, 90.1] 
// double median1 = scoreTracker.getMedianScore(); // Output: 87.8  (average of 90.1 and 85.5) 
// scoreTracker.addScore(81.2);    // Stream: [85.5, 92.3, 77.8, 90.1, 81.2] 
// scoreTracker.addScore(88.7);    // Stream: [85.5, 92.3, 77.8, 90.1, 81.2, 88.7] 
// double median2 = scoreTracker.getMedianScore(); // Output: 87.1 (average of 88.7 and 85.5)


/*
 * This program implements a ScoreTracker class that calculates the median score from a stream of assignment scores.
 * The ScoreTracker class provides methods to add a new score to the data stream and to get the median score.
 * If the number of scores is even, the median is calculated as the average of the two middle scores.
 */

 package QuestionNo1to6.QuestionNo3;

 import java.util.*;
 
 public class ScoreTracking {
     private PriorityQueue<Double> minHeap; // Min heap to store the larger half of scores
     private PriorityQueue<Double> maxHeap; // Max heap to store the smaller half of scores
 
     // Constructor to initialize the ScoreTracker object
     public ScoreTracking() {
         minHeap = new PriorityQueue<>();
         maxHeap = new PriorityQueue<>(Collections.reverseOrder());
     }
 
     // Method to add a new assignment score to the data stream
     public void addScore(double score) {
         // Add the score to the appropriate heap based on its value
         if (maxHeap.isEmpty() || score <= maxHeap.peek()) {
             maxHeap.offer(score);
         } else {
             minHeap.offer(score);
         }
 
         // Balance the heaps if necessary
         if (maxHeap.size() > minHeap.size() + 1) {
             minHeap.offer(maxHeap.poll());
         } else if (minHeap.size() > maxHeap.size()) {
             maxHeap.offer(minHeap.poll());
         }
     }
 
     // Method to calculate and return the median score
     public double getMedianScore() {
         // If no scores are added yet, throw an exception
         if (maxHeap.isEmpty()) {
             throw new IllegalStateException("No scores added yet.");
         }
 
         // If the number of scores is even, return the average of the two middle scores
         if (maxHeap.size() == minHeap.size()) {
             return (maxHeap.peek() + minHeap.peek()) / 2.0;
         } else { // If the number of scores is odd, return the middle score from the max heap
             return maxHeap.peek();
         }
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         ScoreTracking scoreTracker = new ScoreTracking();
         scoreTracker.addScore(85.5);
         scoreTracker.addScore(92.3);
         scoreTracker.addScore(77.8);
         scoreTracker.addScore(90.1);
         double median1 = scoreTracker.getMedianScore();
         System.out.println("Median 1: " + median1);
 
         scoreTracker.addScore(81.2);
         scoreTracker.addScore(88.7);
         double median2 = scoreTracker.getMedianScore();
         System.out.println("Median 2: " + median2);
     }
 }
 