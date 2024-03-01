import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

class Node implements Comparable<Node> 
{
    int vertex;
    int distance;

    // constructor for Node class
    public Node(int vertex, int distance) 
    {
        this.vertex = vertex;
        this.distance = distance;
    }

    // used in priority queue, returns positive, negative or zero depending on whether this is < || > || = to the node being compared.
    public int compareTo(Node other) 
    {
        return Integer.compare(this.distance, other.distance);
    }
}

public class Dijkstra {

    public static void main(String[] args) throws IOException 
    {

        // creating object to get input from txt file
        Scanner sc = new Scanner(new File("cop3503-asn2-input.txt"));

        if (sc == null) {
            System.out.println("Please scan in a text file");
            return;
        }

        // scan in first three integer and save them as their appropriate variables
        int numVertices = sc.nextInt();
        int source = sc.nextInt();
        int numEdges = sc.nextInt();

        // declaring adjacency matrix to be length of the number of vertices
        int[][] adjacencyMatrix = new int[numVertices + 1][numVertices + 1];

        // initialize matrix to 0s and -1 for source vertex
        for (int i = 1; i <= numVertices; i++) {
            for (int j = 1; j <= numVertices; j++) 
            {
                if (i == source && j == source)
                    adjacencyMatrix[i][j] = -1;
                else
                    adjacencyMatrix[i][j] = 0;
            }
        }

        System.out.println("\n\n");

        // finish scanning in graph and assigning edge weights to connecting nodes
        for (int j = 1; j <= numEdges; j++) {
            int sourceVertex = sc.nextInt();
            int destinationVertex = sc.nextInt();
            int weight = sc.nextInt();
            adjacencyMatrix[sourceVertex][destinationVertex] = weight;
            adjacencyMatrix[destinationVertex][sourceVertex] = weight;
        }

        // initialize distances array with a priority queue
        // priority queue keeps track of unvisited nodes
        int[] distances = new int[numVertices + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        PriorityQueue<Node> unvisited = new PriorityQueue<>();
        unvisited.add(new Node(source, 0));

        // initialize array tracking the predecessor vertices. Predecessor vertices are the vertices that
        // come immediately before a vertex, on the shortest path from the source vertex to every other vertex.
        int[] predecessors = new int[numVertices + 1];
        Arrays.fill(predecessors, -1);

        // main loop, compares vertices and organizes them according to
        while (!unvisited.isEmpty()) {
            // remove and return the node with the smallest distance from the queue. u keeps track of vertex id
            Node node = unvisited.poll();
            int u = node.vertex;

            // iterate over entire adjacency matrix
            for (int v = 1; v <= numVertices; v++) 
            {
                // check if theres an edge from u to v
                if (adjacencyMatrix[u][v] > 0) 
                {
                    // update shortest distance
                    int alt = distances[u] + adjacencyMatrix[u][v];
                    if (alt < distances[v]) 
                    {
                        // add node with updated distance
                        distances[v] = alt;
                        predecessors[v] = u;
                        unvisited.add(new Node(v, alt));
                    }
                }
            }
        }

        // make output file for Dijkstra
        PrintWriter writer = new PrintWriter("cop3503-asn2-output-voor-samuel.txt", "UTF-8");

        // print results to the file
        writer.println(numVertices);
        for (int v = 1; v <= numVertices; v++) 
        {
            if (v == source)
                writer.printf("%d -1 -1\n", v);
            else
                writer.printf("%d %d %d\n", v, distances[v], predecessors[v]);
        }

        sc.close();
        writer.close();
    }
}
