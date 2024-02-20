// Assume you were hired to create an application for an ISP, and there are n network devices, such as routers, 
// that are linked together to provide internet access to users. You are given a 2D array that represents network 
// connections between these network devices. write an algorithm to return impacted network devices, If there is 
// a power outage on a certain device, these impacted device list assist you notify linked consumers that there is a 
// power outage and it will take some time to rectify an issue. 
// Input: edges= {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}} 
// Target Device (On which power Failure occurred): 4 
// Output (Impacted Device List) = {5,7}

/*
 * This program implements an algorithm to find impacted network devices in the event of a power outage on a specific device.
 * It identifies the devices that are impacted by the outage, considering the network connections between them.
 */

 package QuestionNo1to6.QuestionNo5;

 import java.util.*;
 
 public class ImpactedDevicesFinder {
 
     // Class variables
     private int[] discoveryTime;
     private int[] low;
     private int time = 0;
     private List<List<Integer>> bridges = new ArrayList<>();
     private Map<Integer, List<Integer>> graph = new HashMap<>();
 
     // Method to find impacted devices
     public List<Integer> findImpactedDevices(int numDevices, List<List<Integer>> connections, int targetDevice) {
         // Initialize data structures
         discoveryTime = new int[numDevices];
         low = new int[numDevices];
         for (int i = 0; i < numDevices; i++) {
             graph.put(i, new ArrayList<Integer>());
         }
 
         // Populate the graph from the connections
         for (List<Integer> connection : connections) {
             int from = connection.get(0);
             int to = connection.get(1);
             graph.get(from).add(to);
             graph.get(to).add(from);
         }
 
         // Run DFS to find bridges
         dfs(targetDevice, -1);
 
         // Identify impacted devices
         Set<Integer> impactedDevicesSet = new HashSet<>();
         for (List<Integer> bridge : bridges) {
             int device1 = bridge.get(0);
             int device2 = bridge.get(1);
             if (device1 == targetDevice) {
                 impactedDevicesSet.add(device2);
             } else if (device2 == targetDevice) {
                 impactedDevicesSet.add(device1);
             }
         }
 
         // Retrieve additional impacted devices connected to the identified ones
         Set<Integer> additionalAffectedDevices = new HashSet<>();
         for (int impactedDevice : impactedDevicesSet) {
             for (int neighbor : graph.get(impactedDevice)) {
                 if (!impactedDevicesSet.contains(neighbor)) {
                     additionalAffectedDevices.add(neighbor);
                 }
             }
         }
 
         // Combine all impacted devices
         impactedDevicesSet.addAll(additionalAffectedDevices);
         impactedDevicesSet.remove(targetDevice);
 
         return new ArrayList<>(impactedDevicesSet);
     }
 
     // Depth-First Search (DFS) to find bridges
     private void dfs(int currentDevice, int parentDevice) {
         discoveryTime[currentDevice] = low[currentDevice] = ++time;
         for (int neighbor : graph.get(currentDevice)) {
             if (neighbor == parentDevice) continue;
             if (discoveryTime[neighbor] == 0) {
                 dfs(neighbor, currentDevice);
                 low[currentDevice] = Math.min(low[currentDevice], low[neighbor]);
                 if (low[neighbor] > discoveryTime[currentDevice]) {
                     bridges.add(Arrays.asList(currentDevice, neighbor));
                 }
             } else {
                 low[currentDevice] = Math.min(low[currentDevice], discoveryTime[neighbor]);
             }
         }
     }
 
     // Main method to demonstrate the functionality
     public static void main(String[] args) {
         ImpactedDevicesFinder finder = new ImpactedDevicesFinder();
 
         // Input data
         int numDevices = 8;
         List<List<Integer>> connections = new ArrayList<>();
         connections.add(Arrays.asList(0, 1));
         connections.add(Arrays.asList(0, 2));
         connections.add(Arrays.asList(1, 3));
         connections.add(Arrays.asList(1, 6));
         connections.add(Arrays.asList(2, 4));
         connections.add(Arrays.asList(4, 6));
         connections.add(Arrays.asList(4, 5));
         connections.add(Arrays.asList(5, 7));
         int targetDevice = 4;
 
         // Find impacted devices
         List<Integer> impactedDevices = finder.findImpactedDevices(numDevices, connections, targetDevice);
 
         // Output the impacted devices
         System.out.println("Impacted Devices (excluding target device " + targetDevice + "): " + impactedDevices);
     }
 }
 