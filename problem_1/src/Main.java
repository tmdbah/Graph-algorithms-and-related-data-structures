/**
 * Dijkstra's Algorithm == O(m log n)
 *
 * For Both Directed & Undirected Graphs (Using a Priority Queue)
 *
 * The Algorithm is only concerned with the Vertices and Costs.
 * Nodes are added into the Priority Queue only if there is an edge to travel to from the Adjacency List.
 * Ex. Point A to B in a directed graph adds a single edge, while an Undirected graph adds two edges.
 * Using a binary heap for our Priority Queue gives us an overall running time of O(m log n).
 *
 * @authors TJ Bah & Ria Banerjee
 */

import java.util.*;

/////////////////////////////////////////////////////////////// Node Class
class Node implements Comparator<Node>{ // Used to store heap node
    public int vertex;
    public int cost;
    public Node() { } // Empty Constructor

    public Node(int vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }

    @Override
    public int compare(Node node_o1, Node node_o2) {
        return Integer.compare(node_o1.cost, node_o2.cost);
    }
} // class Node implements Comparator<Node> ** ENDS **

/////////////////////////////////////////////////////////////// Graph_priorityQ Class

class Graph_priorityQ {
    // ------------------------------------------[ Declare Class Member VARs ]-------- //
    int [] path; // Distance
    int [] parent;
    Set<Integer> visited;
    PriorityQueue<Node> pQueue;
    int V; // Total # of Vertices (Nodes)
    List<List<Node>> adj_list; // Adjacency List Data Structure

    public Graph_priorityQ(int V) { // Class constructor
        this.V = V;
        path = new int[V];
        parent = new int[V];
        visited = new HashSet<Integer>(); // S = 0
        pQueue = new PriorityQueue<Node>(V,new Node());
    }

    // Dijkstra's Algo Implementation

    public void dijkstras_algo(List<List<Node>> adj_list, int src_vertex){
        // adj_list = graph, src_vertex = Starting Vertex
        this.adj_list = adj_list;

        // Initialize Single Source; Initial path set to Infinity ---------- --> O(n)

        for(int i = 0; i < V; i++) { // for each vertex v ∈ G.V ------- --> O(n)
            path[i] = Integer.MAX_VALUE; // v.d = ∞ (Infinity)
            parent[i] = -1; // v.π = NIL
        }

        // 1st add source vertex to Priority Queue [Construct Heap]
        pQueue.add(new Node(src_vertex, 0)); // Q = G.V ---------- --> O(n log n)

        // Distance to the source from itself is 0
        path[src_vertex] = 0; // s.d = 0

        while (visited.size() != V) { // while Q != 0
            // u is removed from Priority Queue and has min distance
            int u = pQueue.remove().vertex; // u = Extract-Min(Q) ---------------------- --> O(log n) (Called n times) = O(n log n)
                // add node to finalized list (visited)
                visited.add(u); // S = S U {u}
                relax_graph_adjacentNodes(u); // for each vertex v ∈ G.Adj[u] ---------- --> O(log n) (Called m times) = O(m log n)
        }

        List<Integer> route = new ArrayList<>();

        for(int i = 0; i < V; i++){

            if (i != src_vertex && path[i] != Integer.MAX_VALUE)
            {
                getRoute(parent, i, route);
                System.out.printf("Path (%d -> %d): Path Cost = %d, Path Route = %s\n",
                        src_vertex, i, path[i], route);
                route.clear();
            }

        }

    }

    // this method processes all neighbours of the recently visited node
    private void relax_graph_adjacentNodes(int u){ // Relax(u) ---------- --> O(log n) (Called m times) = O(m log n)
            int edgeDistance = -1; // w(u,v)
            int new_v_Distance = -1; // v.d

            // process/iterate all neighbouring nodes of u
            for(int i = 0; i < adj_list.get(u).size(); i++){
            Node v = adj_list.get(u).get(i); // v

            // proceed only if current node is not in (visited Set)
            if (!visited.contains(v.vertex)) {
                edgeDistance = v.cost; // w(u,v)
                new_v_Distance = path[u] + edgeDistance; // v.d = u.d + w(u,v)

                // compare distances
                if(new_v_Distance < path[v.vertex]){ // if v.d > u.d + w(u,v)
                    path[v.vertex] = new_v_Distance; // v.d = u.d + w(u,v)
                    parent[v.vertex] = u; // v.π = u
                }
                // add the current vertex to the Priority Queue ** Cloud [Construct Heap]
                pQueue.add(new Node(v.vertex, path[v.vertex])); // ---------- --> O(n log n)
                }
            }
    }

    public static void getRoute(int [] parent, int i, List<Integer> route) {
        if(i >= 0) {
            getRoute(parent, parent[i], route);
            route.add(i);
        }
    }

} // class Graph_priorityQ ** END **

public class Main {

    public static void main(String[] args) {

        // Adjust Number of Vertices Per Test Case, V = 5 for Calibration Test, V = 12 For Test Cases
        int V = 12;
        int source = 0;
        // adjacency list representation of graph
        List<List<Node> > adj_list = new ArrayList<List<Node> >();

        // Initialize adjacency list for every node in the graph
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj_list.add(item);
        }

        // Input graph edges Here

        // to travel from vertex [X = src] to [Y = dest], one has to cover [Z = cost] units of distance
        // adj_list.get(X).add(new Node(Y, Z));

        // Calibration TEST Case (Cormen EXAMPLE 1)

        /*

        adj_list.get(0).add(new Node(1, 10));
        adj_list.get(0).add(new Node(3, 5));

        adj_list.get(3).add(new Node(1, 3));
        adj_list.get(3).add(new Node(4, 2));
        adj_list.get(3).add(new Node(2, 9));

        adj_list.get(1).add(new Node(3, 2));
        adj_list.get(1).add(new Node(2, 1));

        adj_list.get(4).add(new Node(0, 7));
        adj_list.get(4).add(new Node(2, 6));

        */

        // to travel from vertex [X = src] to [Y = dest], one has to cover [Z = cost] units of distance
        // adj_list.get(X).add(new Node(Y, Z));

        /*

        // Test Case 1 / Graph 1 (Directed)
        adj_list.get(0).add(new Node(1, 100));
        adj_list.get(0).add(new Node(2, 50));

        adj_list.get(1).add(new Node(2, 50));
        adj_list.get(1).add(new Node(3, 50));
        adj_list.get(1).add(new Node(4, 50));
        adj_list.get(1).add(new Node(5, 25));

        adj_list.get(2).add(new Node(3, 100));

        adj_list.get(3).add(new Node(4, 75));
        adj_list.get(3).add(new Node(10, 90));

        adj_list.get(4).add(new Node(7, 75));
        adj_list.get(4).add(new Node(9, 25));

        adj_list.get(5).add(new Node(8, 15));

        adj_list.get(6).add(new Node(4, 60));
        adj_list.get(6).add(new Node(7, 30));

        adj_list.get(7).add(new Node(11, 10));

        adj_list.get(8).add(new Node(4, 70));
        adj_list.get(8).add(new Node(6, 45));

        adj_list.get(9).add(new Node(7, 60));
        adj_list.get(9).add(new Node(11, 40));

        adj_list.get(10).add(new Node(4, 95));
        adj_list.get(10).add(new Node(9, 10));
        adj_list.get(10).add(new Node(11, 85));

         */

        // to travel from vertex [X = src] to [Y = dest], one has to cover [Z = cost] units of distance
        // adj_list.get(X).add(new Node(Y, Z));

        /*

        // Test Case 2 / Graph 2 (Directed)
        adj_list.get(0).add(new Node(1, 10));
        adj_list.get(0).add(new Node(2, 4));

        adj_list.get(1).add(new Node(2, 3));
        adj_list.get(1).add(new Node(3, 3));

        adj_list.get(2).add(new Node(1, 7));
        adj_list.get(2).add(new Node(4, 9));
        adj_list.get(2).add(new Node(5, 6));

        adj_list.get(3).add(new Node(6, 7));

        adj_list.get(4).add(new Node(1, 1));
        adj_list.get(4).add(new Node(3, 8));
        adj_list.get(4).add(new Node(6, 2));
        adj_list.get(4).add(new Node(7, 4));

        adj_list.get(5).add(new Node(4, 8));
        adj_list.get(5).add(new Node(8, 3));

        adj_list.get(6).add(new Node(7, 3));
        adj_list.get(6).add(new Node(9, 7));

        adj_list.get(7).add(new Node(6, 5));
        adj_list.get(7).add(new Node(8, 1));
        adj_list.get(7).add(new Node(10, 4));

        adj_list.get(8).add(new Node(11, 2));

        adj_list.get(9).add(new Node(10, 4));

        adj_list.get(10).add(new Node(11, 6));

         */

        // to travel from vertex [X = src] to [Y = dest], one has to cover [Z = cost] units of distance
        // adj_list.get(X).add(new Node(Y, Z));

        /*

        // Test Case 3 / Graph 3 (Undirected)
        adj_list.get(0).add(new Node(1, 2));
        adj_list.get(0).add(new Node(3, 5));

        adj_list.get(1).add(new Node(0, 2));
        adj_list.get(1).add(new Node(5, 4));

        adj_list.get(2).add(new Node(4, 3));
        adj_list.get(2).add(new Node(5, 9));
        adj_list.get(2).add(new Node(6, 1));

        adj_list.get(3).add(new Node(0, 5));
        adj_list.get(3).add(new Node(4, 7));
        adj_list.get(3).add(new Node(5, 5));

        adj_list.get(4).add(new Node(2, 3));
        adj_list.get(4).add(new Node(3, 7));
        adj_list.get(4).add(new Node(5, 3));
        adj_list.get(4).add(new Node(6, 2));
        adj_list.get(4).add(new Node(7, 3));
        adj_list.get(4).add(new Node(8, 3));

        adj_list.get(5).add(new Node(1, 4));
        adj_list.get(5).add(new Node(2, 9));
        adj_list.get(5).add(new Node(3, 5));
        adj_list.get(5).add(new Node(4, 3));

        adj_list.get(6).add(new Node(2, 1));
        adj_list.get(6).add(new Node(4, 2));
        adj_list.get(6).add(new Node(8, 3));
        adj_list.get(6).add(new Node(9, 6));

        adj_list.get(7).add(new Node(4, 3));
        adj_list.get(7).add(new Node(8, 5));
        adj_list.get(7).add(new Node(10, 9));

        adj_list.get(8).add(new Node(4, 3));
        adj_list.get(8).add(new Node(6, 3));
        adj_list.get(8).add(new Node(7, 5));
        adj_list.get(8).add(new Node(10, 4));

        adj_list.get(9).add(new Node(6, 6));
        adj_list.get(9).add(new Node(10, 3));
        adj_list.get(9).add(new Node(11, 3));

        adj_list.get(10).add(new Node(7, 9));
        adj_list.get(10).add(new Node(8, 4));
        adj_list.get(10).add(new Node(9, 3));
        adj_list.get(10).add(new Node(11, 7));

        adj_list.get(11).add(new Node(9, 3));
        adj_list.get(11).add(new Node(10, 7));

         */

        // to travel from vertex [X = src] to [Y = dest], one has to cover [Z = cost] units of distance
        // adj_list.get(X).add(new Node(Y, Z));

        // Test Case 4 / Graph 4 (Undirected)
        adj_list.get(0).add(new Node(1, 50));
        adj_list.get(0).add(new Node(2, 60));

        adj_list.get(1).add(new Node(0, 50));
        adj_list.get(1).add(new Node(3, 120));
        adj_list.get(1).add(new Node(4, 90));

        adj_list.get(2).add(new Node(0, 60));
        adj_list.get(2).add(new Node(3, 20));
        adj_list.get(2).add(new Node(5, 50));

        adj_list.get(3).add(new Node(1, 120));
        adj_list.get(3).add(new Node(2, 20));
        adj_list.get(3).add(new Node(5, 80));
        adj_list.get(3).add(new Node(6, 70));

        adj_list.get(4).add(new Node(1, 90));
        adj_list.get(4).add(new Node(6, 40));
        adj_list.get(4).add(new Node(7, 75));

        adj_list.get(5).add(new Node(2, 50));
        adj_list.get(5).add(new Node(3, 80));
        adj_list.get(5).add(new Node(6, 140));
        adj_list.get(5).add(new Node(9, 40));

        adj_list.get(6).add(new Node(3, 70));
        adj_list.get(6).add(new Node(4, 40));
        adj_list.get(6).add(new Node(5, 140));
        adj_list.get(6).add(new Node(7, 50));
        adj_list.get(6).add(new Node(9, 15));
        adj_list.get(6).add(new Node(10, 40));

        adj_list.get(7).add(new Node(4, 75));
        adj_list.get(7).add(new Node(6, 50));
        adj_list.get(7).add(new Node(8, 80));

        adj_list.get(8).add(new Node(7, 80));
        adj_list.get(8).add(new Node(10, 25));
        adj_list.get(8).add(new Node(11, 20));

        adj_list.get(9).add(new Node(5, 40));
        adj_list.get(9).add(new Node(6, 15));
        adj_list.get(9).add(new Node(11, 90));

        adj_list.get(10).add(new Node(6, 40));
        adj_list.get(10).add(new Node(8, 25));
        adj_list.get(10).add(new Node(11, 60));

        adj_list.get(11).add(new Node(8, 20));
        adj_list.get(11).add(new Node(9, 90));
        adj_list.get(11).add(new Node(10, 60));

        // call Dijkstra's algo method
        Graph_priorityQ graphPQ = new Graph_priorityQ(V);
        graphPQ.dijkstras_algo(adj_list, source);

        // Print the shortest path from source node to all the nodes
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("The shortest path from the source node to other nodes:");
        System.out.println("[Source Node] ->\t\t" + "[Node #]\t\t" + "Path (Cost):\t\t" + "Parent Node:\t\t");
        for (int i = 0; i < graphPQ.path.length; i++)
            System.out.println("\t" + source + " \t\t\t\t\t\t " + i + " \t\t\t\t "  + graphPQ.path[i] + " \t\t\t\t\t "  + graphPQ.parent[i]);
    }
}

