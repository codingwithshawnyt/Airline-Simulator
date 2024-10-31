//Shawn Ray
//Period 4
//Graph.java
//This file implements an algorithm that helps find patterns in airline schedules

import java.text.*; // for decimal formatting
import java.util.*; // for Scanner, Arrays, Stack
import java.io.*; // for File, FileNotFoundException
import java.awt.*; // for Point

public class Graph implements AirlineGraph {

    // Instance variables
    // The adjacency matrix that stores the graph connections
    private int[][] adjMatrix = new int[SIZE][SIZE];
    // Stack used for rebuilding the path in graph traversal algorithms
    private Stack<Integer> rebuildingStack = new Stack<>();

    // Boolean array used for Depth-First Search (DFS) or Shortest Path (SP)
    // algorithms to track unvisited nodes
    private boolean[] visited = new boolean[SIZE];

    // Array that stores the previous nodes in the path, useful for path
    // reconstruction
    private int[] prevNodes = new int[SIZE];

    // Array that stores the shortest distances from the source node to each other
    // node
    private int[] distances = new int[SIZE];

    // DecimalFormat instance for formatting the cost output to two decimal places
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    // Constructor for the Graph class
    Graph() throws FileNotFoundException {
        // Initialize a Scanner to read from a file
        Scanner keyboard = new Scanner(new File("connections.dat"));

        // Initialize the adjacency matrix and the rebuilding stack
        adjMatrix = new int[SIZE][SIZE];
        rebuildingStack = new Stack<Integer>();

        // Fill the adjacency matrix with zeros to ensure no null values
        for (int i = 0; i < adjMatrix.length; i++) {
            Arrays.fill(adjMatrix[i], 0);
        }

        // Read from the file until there are no more lines
        while (keyboard.hasNext()) {
            // Parse each line to get the source node, destination node, and cost of the
            // edge
            String s = keyboard.nextLine();
            int source = Integer.parseInt(s.substring(0, 1)); // Source node
            int destination = Integer.parseInt(s.substring(s.indexOf(',') + 1, s.lastIndexOf(','))); // Destination node
            int weight = Integer.parseInt(s.substring(s.lastIndexOf(',') + 1)); // Cost of the edge

            // Add the edge to the adjacency matrix
            adjMatrix[source][destination] = weight;
        }
    }

    // Method to find the index of a given airport code in the airportCode array
    private int findAirportCode(String airportCode) {
        // Traverse through the array of airport codes
        for (int i = 0; i < this.airportCode.length; i++) {
            // If the current airport code matches the given airport code, return its index
            if (this.airportCode[i].equals(airportCode))
                return i;
        }
        // If the airport code is not found, return -1
        return -1;
    }

    // Method to check if two points are adjacent in the adjacency matrix
    private boolean adjacent(Point edge) {
        // If the value at the given points in the adjacency matrix is not 0, then the
        // points are adjacent
        return adjMatrix[edge.x][edge.y] != 0;
    }

    // Method to find the shortest path from a given source to all other nodes
    public int[] shortestPath(String source) {
        // Find the index of the source node
        int indx = findAirportCode(source);

        // Initialize all distances to infinity, all nodes as unvisited, and all
        // previous nodes as -1
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        Arrays.fill(prevNodes, -1);

        // The distance from the source node to itself is 0
        distances[indx] = 0;

        // For each node
        for (int i = 0; i < 10; i++) {
            // Find the node with the minimum distance that has not been visited
            int next = minDistance(distances, visited);
            // Mark the node as visited
            visited[next] = true;

            // For each node
            for (int j = 0; j < 10; j++) {
                // If the node is unvisited, is adjacent to the current node, and the distance
                // to the current node is not infinity
                // and the sum of the distance to the current node and the weight of the edge is
                // less than the current minimum distance
                if (!visited[j] && adjMatrix[next][j] != 0 && distances[next] != Integer.MAX_VALUE
                        && distances[next] + adjMatrix[next][j] < distances[j]) {
                    // Update the minimum distance and the previous node
                    distances[j] = distances[next] + adjMatrix[next][j];
                    prevNodes[j] = next;
                }
            }
        }

        // Return the array of minimum distances
        return distances;
    }

    // Method to find the node with the minimum distance that has not been visited
    private int minDistance(int[] distances, boolean[] visited) {
        // Initialize the minimum distance and the index of the node with the minimum
        // distance
        int min = Integer.MAX_VALUE, minIndex = -1;

        // For each node
        for (int i = 0; i < 10; i++) {
            // If the node has not been visited and its distance is less than or equal to
            // the current minimum distance
            if (!visited[i] && distances[i] <= min) {
                // Update the minimum distance and the index of the node with the minimum
                // distance
                min = distances[i];
                minIndex = i;
            }
        }

        // Return the index of the node with the minimum distance
        return minIndex;
    }

    // Method to find the cheapest route from a start node to an end node
    public String cheapestRoute(String start, String end) {
        // Find the shortest path from the start node to all other nodes
        shortestPath(start);

        // Find the index of the end node
        int endindx = findAirportCode(end);

        // If there is no path from the start node to the end node, return a message
        // indicating this
        if (distances[endindx] == Integer.MAX_VALUE) {
            return "There is no path from " + start + " to " + end;
        }

        // Initialize the string that will store the path
        String ans = "";

        // Start from the end node
        int index = endindx;

        // While the current node is not the start node
        while (index != -1) {
            // Add the current node to the stack and move to the previous node
            rebuildingStack.push(index);
            index = prevNodes[index];
        }

        // While the stack is not empty
        while (!rebuildingStack.isEmpty()) {
            // Pop the top node from the stack and add it to the path
            int cityIndex = rebuildingStack.pop();
            ans += (city[cityIndex]);

            // If the stack is not empty, add an arrow to indicate the direction of the path
            if (!rebuildingStack.isEmpty()) {
                ans += (" -> ");
            }
        }

        // Calculate the total cost of the path
        int totalCost = distances[endindx];

        // Add the total cost to the path
        ans += (" $") + (totalCost);

        // Clear the stack for future use
        rebuildingStack.clear();

        // Return the path
        return ans;
    }

    // Method to find a path of a given length from a start node to an end node
    private boolean findPath(int length, Point p) {
        // If the length of the path is 1
        if (length == 1) {
            // If the start node and the end node are adjacent, add the end node to the
            // stack
            if (adjacent(p))
                rebuildingStack.push(p.y);
            // Return whether the start node and the end node are adjacent
            return adjacent(p);
        } else {
            // For each node
            for (int i = 0; i < 10; i++) {
                // If the node is adjacent to the start node and a path of length (length - 1)
                // can be found from the node to the end node
                if (adjacent(new Point(i, p.x)) && findPath(length - 1, new Point(i, p.y))) {
                    // Add the node to the stack and return true
                    rebuildingStack.push(i);
                    return true;
                }
            }
            // If no path is found, return false
            return false;
        }
    }

    // Method to find a route of a given length from a start node to an end node
    @Override
    public String findRoute(int length, String start, String end) {
        // Convert the start and end nodes to points
        Point p = new Point(findAirportCode(start), findAirportCode(end));

        // Initialize the total cost of the route
        double total = 0;

        // If no path is found, return a message indicating this
        if (!findPath(length, p))
            return "There is no such connection!";

        // Initialize the route with the start node
        String ans = city[findAirportCode(start)] + " -> ";
        int prev = findAirportCode(start);

        // While the stack is not empty
        while (!rebuildingStack.isEmpty()) {
            // Pop the top node from the stack and add it to the route
            int cityIndex = rebuildingStack.pop();
            ans += (city[cityIndex]);

            // Add the cost of the edge from the previous node to the current node to the
            // total cost
            total += adjMatrix[prev][cityIndex];

            // If the stack is not empty, add an arrow to indicate the direction of the
            // route
            if (!rebuildingStack.isEmpty()) {
                ans += (" -> ");
            }

            // Update the previous node
            prev = cityIndex;
        }

        // Return the route and the total cost
        return ans + "   $" + decimalFormat.format(total);
    }

    // Method to convert the graph to a string
    public String toString() {
        // Initialize the StringBuilder with the top row of the adjacency matrix
        StringBuilder ans = new StringBuilder("   ");
        for (int i = 0; i < 10; i++) {
            ans.append("  ").append(airportCode[i]);
        }

        ans.append("\n");

        // Add each row of the adjacency matrix to the StringBuilder
        for (int i = 0; i < 10; i++) {
            ans.append(airportCode[i]);
            for (int j = 0; j < 10; j++) {
                int c = adjMatrix[i][j];

                // If there is no edge between the nodes, add a dash to the StringBuilder
                if (c == 0) {
                    ans.append("   - ");
                } else {
                    // If there is an edge between the nodes, add the weight of the edge to the
                    // StringBuilder
                    ans.append(String.format(" %3d ", c));
                }
            }
            ans.append("\n\n");
        }

        // Return the string
        return ans.toString();
    }
}