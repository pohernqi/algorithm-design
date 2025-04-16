package Q3;
import Q1.Star;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


class Vertex {
    int vertex;
    int distance;

    public Vertex(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public int getVertex() {
        return vertex;
    }
}

class Dijkstra {
    int vertices;
    Map<Integer, Star> stars;
    List<List<Vertex>> adjacencyList;
    Star star;

    public Dijkstra(int vertices) {
        this.vertices = vertices;
        stars = new HashMap<>();
        adjacencyList = new ArrayList<>();
        for (int i = 0; i <= vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }


    public void addEdge(int source, int destination, int distance) {
        // Add edge from source to destination if it doesn't exist
        if (!edgeExists(source, destination, distance)) {
            adjacencyList.get(source).add(new Vertex(destination, distance));
        }
        // Since this is an undirected graph, add edge from destination to source if it doesn't exist
        if (!edgeExists(destination, source, distance)) {
            adjacencyList.get(destination).add(new Vertex(source, distance));
        }
    }

    
    public boolean edgeExists(int from, int to, double distance) {
        // Iterate over all edges emanating from the vertex 'from'
        for (Vertex vertex : adjacencyList.get(from)) {
            // Check if there is an edge going to vertex 'to' with the exact distance specified
            if (vertex.getVertex() == to && Double.compare(vertex.distance, distance) == 0) {
                return true;
            }
        }
        return false;
    }

    //print adjacency list
    public void printAdjacencyList() {
        System.out.println("Adjacency List:");
        for (int i = 1; i < adjacencyList.size(); i++) {
            System.out.print("Star " + i + ": ");
            for (Vertex vertex : adjacencyList.get(i)) {
                System.out.print("(" + vertex.vertex + ", " +  vertex.distance + ") ");
            }
            System.out.println();
        }
    }

    public static void dijkstraStart(Dijkstra graph, int sourceVertex, PrintWriter writer) {
        // Keeps track of whether a vertex has been fully processed.
        boolean[] visited = new boolean[graph.vertices + 1];
    
        // Stores the shortest distance from the source vertex to every other vertex.
        int[] distances = new int[graph.vertices + 1];
    
        // Holds the previous vertex from the source for each vertex.
        int[] predecessors = new int[graph.vertices + 1];
    
        // Fill predecessors array with -1 indicating no predecessors initially.
        Arrays.fill(predecessors, -1);

        // Initialize distances array with Integer.MAX_VALUE to represent infinity.
        Arrays.fill(distances, Integer.MAX_VALUE);
    
        // The distance of the source vertex is set to 0.
        distances[sourceVertex] = 0;
    
        // Priority queue to select the next vertex with the shortest distance.
        PriorityQueue<Vertex> pq = new PriorityQueue<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex e1, Vertex e2) {
                return Integer.compare(e1.distance, e2.distance);
            }
        });

        //add source vertex to priority queue
        pq.offer(new Vertex(sourceVertex, 0));
    
        while (!pq.isEmpty()) {
            // Extract the vertex with the minimum distance.
            Vertex current = pq.poll();
            int currentVertex = current.vertex;
    
            // Skip this vertex if it has already been processed.
            if (visited[currentVertex]) {
                continue;
            }
    
            // Mark the current vertex as processed.
            visited[currentVertex] = true;
    
            // Explore all adjacent vertices of the current vertex.
            List<Vertex> vertices = graph.adjacencyList.get(currentVertex);
            for (Vertex vertex : vertices) {
                // Proceed only if the adjacent vertex has not been visited.
                if (!visited[vertex.vertex]) {
                    // Calculate the distance to the adjacent vertex.
                    int newDist = distances[currentVertex] + vertex.distance;
                    // Update the distance if it is shorter than the previously known distance.
                    if (newDist < distances[vertex.vertex]) {
                        distances[vertex.vertex] = newDist;
                        predecessors[vertex.vertex] = currentVertex;
                        // Add the adjacent vertex to the queue with the new distance.
                        pq.offer(new Vertex(vertex.vertex, newDist));
                    }
                }
            }
        }
        // Print the shortest distances from the source vertex to all other vertices.
        printDistances(distances, sourceVertex, writer);
        // Print the shortest paths from the source vertex to all other vertices.
        printPaths(sourceVertex, predecessors, graph.vertices, writer);
    }

    //function to print distances from star 1 to other stars
    private static void printDistances(int[] distances, int sourceVertex, PrintWriter writer) {
        writer.println("Shortest paths from star " + sourceVertex);
        for (int i = 1; i < distances.length; i++) {
            writer.println("To star " + i + ", " + (distances[i] == Integer.MAX_VALUE ? "No path" : Integer.toString(distances[i])));
        }
        writer.println();
    }
    
    //function to print the predecessors of the path of star
    public static void printPaths(int sourceVertex, int[] predecessors, int vertices, PrintWriter writer) {
        writer.println("Graph representing shortest Paths from star " + sourceVertex);
        for (int i = 0; i <= vertices; i++) {
            if (i != sourceVertex && predecessors[i] != -1) {
                writer.print("Path to star " + i + ", ");
                printPath(i, predecessors, writer);
                writer.println();
            }
        }
        writer.println(); 
    }

    public static void printPath(int vertex, int[] predecessors, PrintWriter writer) {
        if (predecessors[vertex] != -1) {
            printPath(predecessors[vertex], predecessors, writer);
            writer.print(" -> ");
        }
        writer.print(vertex);
    }

    //function to read file
    public static void processFile(Dijkstra graph) {
        HashMap<Integer, double[]> starPositions = new HashMap<>();

        //read dataset2
        try (BufferedReader br = new BufferedReader(new FileReader("dataSet2.csv"))) {
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
            e.printStackTrace();
        }

        // Read connected_stars.csv and compute distances
        try (BufferedReader br = new BufferedReader(new FileReader("connected_stars.csv"))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int star1 = Integer.parseInt(values[0]); // Parse the star name
                int star2 = Integer.parseInt(values[1]); // Parse the star that is connected to
                double[] pos1 = starPositions.get(star1);  // Retrieve the first star's coordinates
                double[] pos2 = starPositions.get(star2); // Retrieve the connected star's coordinates
                int distance = Star.calculateDistance(pos1, pos2); // Calculate distance between stars
                graph.addEdge(star1, star2, distance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Dijkstra graph = new Dijkstra(20); // graph with 20 vertices

        processFile(graph);

        // uncomment this for adjacency list
       //graph.printAdjacencyList();

        try {
        PrintWriter writer = new PrintWriter(new File("Q3/D_results.txt"));
        
        dijkstraStart(graph, 1, writer);

        writer.close();  

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

    }

}
