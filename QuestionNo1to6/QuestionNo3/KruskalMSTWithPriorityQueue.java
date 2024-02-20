// Implement Kruskal algorithm and priority queue using minimum heap 


/*
 * This program implements the Kruskal's algorithm to find the Minimum Spanning Tree (MST) of a given
 * graph. It also includes a priority queue implementation using a minimum heap for efficient edge
 * selection. The MST is constructed by selecting edges with the minimum weight while avoiding cycles.
 */

 package QuestionNo1to6.QuestionNo3;

 import java.util.*;
 
 // Class to represent an edge in the graph
 class Edge implements Comparable<Edge> {
     int src, dest, weight;
 
     public Edge(int src, int dest, int weight) {
         this.src = src;
         this.dest = dest;
         this.weight = weight;
     }
 
     // Compare edges based on their weights
     @Override
     public int compareTo(Edge other) {
         return this.weight - other.weight;
     }
 }
 
 // Class representing the Disjoint Set data structure for union-find operations
 class DisjointSet {
     private int[] parent, rank;
 
     public DisjointSet(int n) {
         parent = new int[n];
         rank = new int[n];
         for (int i = 0; i < n; i++) {
             parent[i] = i;
             rank[i] = 0;
         }
     }
 
     // Find the parent of a set
     public int find(int x) {
         if (parent[x] != x)
             parent[x] = find(parent[x]);
         return parent[x];
     }
 
     // Perform union of two sets
     public void union(int x, int y) {
         int xRoot = find(x);
         int yRoot = find(y);
         if (xRoot == yRoot)
             return;
         if (rank[xRoot] < rank[yRoot])
             parent[xRoot] = yRoot;
         else if (rank[xRoot] > rank[yRoot])
             parent[yRoot] = xRoot;
         else {
             parent[yRoot] = xRoot;
             rank[xRoot]++;
         }
     }
 }
 
 // Class implementing Kruskal's algorithm for Minimum Spanning Tree
 public class KruskalMSTWithPriorityQueue {
     private int V; // Number of vertices in the graph
     private List<Edge> edges; // List to store edges of the graph
 
     // Constructor to initialize the graph with V vertices
     public KruskalMSTWithPriorityQueue(int V) {
         this.V = V;
         edges = new ArrayList<>();
     }
 
     // Method to add an edge to the graph
     public void addEdge(int src, int dest, int weight) {
         edges.add(new Edge(src, dest, weight));
     }
 
     // Method to find the Minimum Spanning Tree using Kruskal's algorithm
     public List<Edge> kruskalMST() {
         List<Edge> result = new ArrayList<>(); // List to store the MST
         PriorityQueue<Edge> pq = new PriorityQueue<>(edges); // Priority queue to select edges
 
         DisjointSet ds = new DisjointSet(V); // Create a Disjoint Set data structure
 
         // Iterate until all vertices are included in the MST or priority queue is empty
         while (!pq.isEmpty() && result.size() < V - 1) {
             Edge edge = pq.poll(); // Get the edge with minimum weight from the priority queue
             int srcRoot = ds.find(edge.src); // Find the root of the source vertex's set
             int destRoot = ds.find(edge.dest); // Find the root of the destination vertex's set
             // If adding the current edge does not create a cycle, add it to the MST
             if (srcRoot != destRoot) {
                 result.add(edge);
                 ds.union(srcRoot, destRoot); // Perform union of the two sets
             }
         }
 
         return result; // Return the Minimum Spanning Tree
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         int V = 4; // Number of vertices
         KruskalMSTWithPriorityQueue graph = new KruskalMSTWithPriorityQueue(V);
 
         // Add edges to the graph
         graph.addEdge(0, 1, 10);
         graph.addEdge(0, 2, 6);
         graph.addEdge(0, 3, 5);
         graph.addEdge(1, 3, 15);
         graph.addEdge(2, 3, 4);
 
         // Find and print the Minimum Spanning Tree (MST)
         List<Edge> mst = graph.kruskalMST();
         System.out.println("Edges in MST:");
         for (Edge edge : mst) {
             System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
         }
     }
 }
 