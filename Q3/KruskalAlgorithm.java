package Q3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import Q1.Star;

class EdgeK {
    int src, dest;
    int distance;

    //src is the source vertex from which the edge originates.
    //dest is destination vertex at which the edge terminates.
    //distance represent the weight.
    EdgeK(int src, int dest, int distance) {
        this.src = src;
        this.dest = dest;
        this.distance = distance;
    }

}

public class KruskalAlgorithm {
    private int[] parent;

    public int find(int i) {
        // This condition checks whether the current element i is its own parent.
        // If i is its own parent, it means i is the root of its subset
        if (parent[i] != i)
            parent[i] = find(parent[i]);
        return parent[i];
    }

    public void Kruskal(ArrayList<EdgeK> edges, int V, PrintWriter writer) {
        // sorts the edges of the graph based on their weights in ascending order.
        Collections.sort(edges, new Comparator<EdgeK>() {
            @Override
            public int compare(EdgeK edge1, EdgeK edge2) {
                return Integer.compare(edge1.distance, edge2.distance);
            }
        });

        // Parent array to track the root of each vertex.
        parent = new int[V + 1];
        for (int i = 0; i <= V; i++) {
        // Initialize each vertex as its own parent.
            parent[i] = i; 
        }

        // Variable to store the weight of the MST.
        int mst_weight = 0; 
        // List to store the edges included in the MST.
        List<EdgeK> mst = new ArrayList<>(); 

        for (EdgeK edge : edges) {
            int nextSource = find(edge.src); // Find root of the source vertex.
            int nextDest = find(edge.dest); // Find root of the destination vertex.

            // If nextsource and nextdest are different, it indicates that edge.src and edge.dest are in
            // different subsets, /the edge can be safely added without forming a cycle
            if (nextSource != nextDest) {
                mst.add(edge); // Add the edge to the MST
                parent[nextDest] = nextSource; // Union the two subsets.
                mst_weight += edge.distance; // Add the weight of the edge to the MST weight
            }
            //If MST contains enough edges to span all vertices, stop the process.
            if (mst.size() == V - 1) {
                break;
            }

        }
        //display the output
        writer.println("Edges in MST");
        for (EdgeK edge : mst) {
            writer.println(edge.src + " -- " + edge.dest + " distance:  " + edge.distance);
        }
        writer.println("\nTotal MST weight: " + mst_weight);
    }

    public static void main(String[] args) {
        ArrayList<EdgeK> edges = new ArrayList<>();
        int vertices = 20; // Number of vertices in the graph

        HashMap<Integer, double[]> starPositions = new HashMap<>();

        // Read dataset2.csv to get star positions
        try (BufferedReader br = new BufferedReader(new FileReader("dataset2.csv"))) {
            String line = br.readLine(); // Read and ignore the header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int starName = Integer.parseInt(values[0]); // Parse the 1st column, star name
                double x = Double.parseDouble(values[1]); // Parse the 2nd column, x-coordinate of star
                double y = Double.parseDouble(values[2]); // Parse y-coordinate
                double z = Double.parseDouble(values[3]); // Parse z-coordinate
                starPositions.put(starName, new double[] { x, y, z }); // Store coordinates in the map
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of an IOException
        }

        // Read connected_stars.csv and compute distances
        try (BufferedReader br = new BufferedReader(new FileReader("connected_stars.csv"))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int star1 = Integer.parseInt(values[0]); // Parse the star name
                int star2 = Integer.parseInt(values[1]); // Parse the star that is connected to
                double[] pos1 = starPositions.get(star1); // Retrieve the first star's coordinates
                double[] pos2 = starPositions.get(star2); // Retrieve the connected star's coordinates
                int distance = Star.calculateDistance(pos1, pos2); // Calculate distance between stars
                edges.add(new EdgeK(star1, star2, distance)); // Add this edge to the edges list
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of an IOException
        }

        // Write the results to a file
        try (PrintWriter writer = new PrintWriter(new File("Q3/K_results.txt"))) {
            KruskalAlgorithm kruskal = new KruskalAlgorithm();
            kruskal.Kruskal(edges, vertices, writer); // Execute Kruskal's algorithm and output results
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open file for writing: " + e.getMessage()); // Error message if file not found
            e.printStackTrace();
        }

    }
     //edges.add(new Edge(0, 1, 1));
        //edges.add(new Edge(1, 2, 2));
       // edges.add(new Edge(2, 0, 2)); //Edge(2, 0, 2): find(2) traces back to 0, and find(0) finds root 0. Since the roots are the same, adding this edge would create a cycle. Skip this edge.
}
