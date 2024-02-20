// Implement ant colony algorithm solving travelling a salesman problem

/*
 * This program implements the Ant Colony Optimization (ACO) algorithm to solve the Traveling Salesman Problem (TSP).
 * The ACO algorithm simulates the foraging behavior of ants to find the shortest tour that visits each city exactly once.
 * The program constructs a solution by iteratively building tours for a specified number of ants and updating pheromone levels on the edges.
 */

 package QuestionNo1to6.QuestionNo5;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Random;
 
 public class AntColonyOptimization {
 
     // Class variables
     private int numAnts;
     private int numCities;
     private double[][] distanceMatrix;
     private double[][] pheromoneMatrix;
     private double alpha; // pheromone factor
     private double beta; // visibility factor
     private double evaporationRate;
     private int maxIterations;
 
     // Constructor
     public AntColonyOptimization(int numAnts, double[][] distanceMatrix, double alpha, double beta, double evaporationRate, int maxIterations) {
         this.numAnts = numAnts;
         this.numCities = distanceMatrix.length;
         this.distanceMatrix = distanceMatrix;
         this.alpha = alpha;
         this.beta = beta;
         this.evaporationRate = evaporationRate;
         this.maxIterations = maxIterations;
 
         // Initialize pheromone levels
         this.pheromoneMatrix = new double[numCities][numCities];
         for (int i = 0; i < numCities; i++) {
             for (int j = 0; j < numCities; j++) {
                 pheromoneMatrix[i][j] = 1.0;
             }
         }
     }
 
     // Method to solve the TSP using ACO
     public List<Integer> solveTSP() {
         List<Integer> bestTour = null;
         double bestTourLength = Double.MAX_VALUE;
 
         // Perform iterations
         for (int iteration = 0; iteration < maxIterations; iteration++) {
             List<List<Integer>> antTours = new ArrayList<>();
             double[] tourLengths = new double[numAnts];
 
             // Construct solutions for each ant
             for (int ant = 0; ant < numAnts; ant++) {
                 List<Integer> tour = constructTour();
                 antTours.add(tour);
                 tourLengths[ant] = calculateTourLength(tour);
             }
 
             // Update pheromone levels
             updatePheromones(antTours, tourLengths);
 
             // Find best tour
             for (int ant = 0; ant < numAnts; ant++) {
                 if (tourLengths[ant] < bestTourLength) {
                     bestTourLength = tourLengths[ant];
                     bestTour = antTours.get(ant);
                 }
             }
         }
 
         return bestTour;
     }
 
     // Method to construct a tour for a single ant
     private List<Integer> constructTour() {
         List<Integer> tour = new ArrayList<>();
         boolean[] visited = new boolean[numCities];
         int currentCity = new Random().nextInt(numCities);
         tour.add(currentCity);
         visited[currentCity] = true;
 
         // Construct the tour
         for (int i = 0; i < numCities - 1; i++) {
             int nextCity = selectNextCity(currentCity, visited);
             tour.add(nextCity);
             visited[nextCity] = true;
             currentCity = nextCity;
         }
 
         return tour;
     }
 
     // Method to select the next city for an ant's tour
     private int selectNextCity(int currentCity, boolean[] visited) {
         double[] probabilities = new double[numCities];
         double totalProbability = 0;
 
         // Calculate probabilities for selecting each unvisited city
         for (int city = 0; city < numCities; city++) {
             if (!visited[city]) {
                 probabilities[city] = Math.pow(pheromoneMatrix[currentCity][city], alpha) * Math.pow(1.0 / distanceMatrix[currentCity][city], beta);
                 totalProbability += probabilities[city];
             }
         }
 
         // Roulette wheel selection
         double rand = new Random().nextDouble() * totalProbability;
         double sum = 0;
         for (int city = 0; city < numCities; city++) {
             if (!visited[city]) {
                 sum += probabilities[city];
                 if (sum >= rand) {
                     return city;
                 }
             }
         }
 
         // Should never reach here
         return -1;
     }
 
     // Method to calculate the length of a tour
     private double calculateTourLength(List<Integer> tour) {
         double length = 0;
         for (int i = 0; i < tour.size() - 1; i++) {
             int city1 = tour.get(i);
             int city2 = tour.get(i + 1);
             length += distanceMatrix[city1][city2];
         }
         length += distanceMatrix[tour.get(numCities - 1)][tour.get(0)]; // Return to starting city
         return length;
     }
 
     // Method to update pheromone levels
     private void updatePheromones(List<List<Integer>> antTours, double[] tourLengths) {
         // Evaporation
         for (int i = 0; i < numCities; i++) {
             for (int j = 0; j < numCities; j++) {
                 pheromoneMatrix[i][j] *= (1 - evaporationRate);
             }
         }
 
         // Pheromone deposition
         for (int ant = 0; ant < numAnts; ant++) {
             List<Integer> tour = antTours.get(ant);
             double tourLength = tourLengths[ant];
             for (int i = 0; i < numCities - 1; i++) {
                 int city1 = tour.get(i);
                 int city2 = tour.get(i + 1);
                 pheromoneMatrix[city1][city2] += 1.0 / tourLength;
                 pheromoneMatrix[city2][city1] += 1.0 / tourLength;
             }
             int lastCity = tour.get(numCities - 1);
             int firstCity = tour.get(0);
             pheromoneMatrix[lastCity][firstCity] += 1.0 / tourLength;
             pheromoneMatrix[firstCity][lastCity] += 1.0 / tourLength;
         }
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         // Example distance matrix for 5 cities
         double[][] distanceMatrix = {
             {0, 10, 15, 20, 25}, // distances from city 0 to other cities
             {10, 0, 35, 25, 30}, // distances from city 1 to other cities
             {15, 35, 0, 30, 20}, // distances from city 2 to other cities
             {20, 25, 30, 0, 40}, // distances from city 3 to other cities
             {25, 30, 20, 40, 0}  // distances from city 4 to other cities
         };
 
         // Parameters for ACO
         int numAnts = 10;
         double alpha = 1.0; // Pheromone factor
         double beta = 2.0; // Visibility factor
         double evaporationRate = 0.5;
         int maxIterations = 100;
 
         // Create instance of AntColonyOptimization and solve TSP
         AntColonyOptimization aco = new AntColonyOptimization(numAnts, distanceMatrix, alpha, beta, evaporationRate, maxIterations);
         List<Integer> bestTour = aco.solveTSP();
 
         // Print the best tour found
         System.out.println("Best tour found: " + bestTour);
     }
 }
 