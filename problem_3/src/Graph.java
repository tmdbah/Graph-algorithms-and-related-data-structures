/**
 * Strongly-Connected-Components Algorithm == O(n + m) or O(V + E)
 *
 * For Directed Graphs
 *
 * Test Cases are included in Driver Method
 *
 * @authors TJ Bah & Ria Banerjee
 */

import java.util.*;
import java.util.LinkedList;

/////////////////////////////////////////////////////////////// Graph Class

class Graph { // Graph
    public int V; // Total # of Vertices (Nodes)
    public int count = 1;
    public LinkedList<Integer>[] adj_list; // Adjacency List Data Structure

    // Constructor

    public Graph(int v) { // Graph
        V = v;
        adj_list = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj_list[i] = new LinkedList<>();
        }
    }

    // aux f(x) to add an edge into the graph
    void addEdge(int v, int w) {
        adj_list[v].add(w);
    }

    // Print DFS starting from v (recursively)
    void DFS(int v, boolean[] visited){ // ---------- --> O(n + m) or O(V + E)

        // Flag the current node as visited and print it
        visited[v] = true;
        System.out.print(v + " ");

        int n;

        // Process all adjacent vertices to this current vertex (recursively)
        for (Integer integer : adj_list[v]) { // ---------- --> O(m) or O(E)
            n = integer;
            if (!visited[n])
                DFS(n, visited); // ---------- --> O(n + m) or O(V + E)
        }
    } // void DFS(int v, boolean visited[]) ** END **

    // f(x) that returns the transpose of this graph

    Graph returnTranspose(){ // ---------- --> O(n + m) or O(V + E)

        Graph g = new Graph(V);
        for (int v = 0; v < V; v++) { // ---------- --> O(n) or O(V)
            // Process all vertices adjacent to this vertex (recursive)
            for (Integer integer : adj_list[v]) g.adj_list[integer].add(v); // ---------- --> O(m) or O(E)
        }
        return g;
    }

    void stackOrder(int v, boolean visited[], Stack stack){ // ---------- --> O(m) or O(E)
        // Flag the current node as visited and print it
        visited[v] = true;

        // Process all adjacent vertices to this current vertex (recursively)
        for (int n : adj_list[v]) { // ---------- --> O(m) or O(E)
            if (!visited[n])
                stackOrder(n, visited, stack);
        }

        // At this point All vertices reachable from v are processed
        // push v to Stack
        stack.push(v); // ---------- --> O(1)
    } //  void stackOrder(int v, boolean visited[], Stack stack) ** END **

    // The main f(x) that finds and prints all Strongly-Connected-Components
    void printSCCs() {
        Stack stack = new Stack();

        // For first DFS, Flag all the vertices as not visited.
        boolean visited[] = new boolean[V];
        for(int i = 0; i < V; i++) { // ---------- --> O(n) or O(V)
            visited[i] = false;
        }

        // Populate vertices in stack by their finishing times
        for (int i = 0; i < V; i++) { // ---------- --> O(n) or O(V)
            if (!visited[i]) {
                stackOrder(i, visited, stack); // ---------- --> O(m) or O(E)
            }
        }

        // Create a reversed graph
        Graph gt = returnTranspose(); // ---------- --> O(n + m) or O(V + E)

        // For second DFS, Flag all the vertices as not visited.
        for (int i = 0; i < V; i++) // ---------- --> O(n) or O(V)
            visited[i] = false;

        // Process all vertices in order defined by Stack
        while (!stack.empty())
        {
            // Pop a vertex from stack
            int v = (int)stack.pop(); // ---------- --> O(1)

            // Print Strongly connected component of the popped vertex
            if (!visited[v]) {
                gt.DFS(v, visited); // ---------- --> O(n + m) or O(V + E)
                System.out.println("  <-- (Strongly Connected Component #: " + count + ")");
                count++; // ---------- --> O(1)
            }
        }
    }

    // Driver
    public static void main(String[] args) {

        // Test Case 4
        // Create a graph
        Graph g = new Graph(11);
        g.addEdge(0, 1);

        g.addEdge(1, 5);
        g.addEdge(1, 3);

        g.addEdge(2, 6);

        g.addEdge(3, 2);
        g.addEdge(3, 4);
        g.addEdge(3, 5);

        g.addEdge(4, 2);

        g.addEdge(5, 0);

        g.addEdge(6, 7);

        g.addEdge(7, 2);
        g.addEdge(7, 9);

        g.addEdge(8, 9);
        g.addEdge(8, 6);


        g.addEdge(10, 1);
        g.addEdge(10, 7);



        /*

        // Test Case 3
        // Create a graph
        Graph g = new Graph(12);
        g.addEdge(0, 1);
        g.addEdge(0, 11);
        g.addEdge(1, 3);
        g.addEdge(2, 1);
        g.addEdge(3, 2);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(4, 6);

        g.addEdge(5, 7);
        g.addEdge(5, 8);

        g.addEdge(6, 2);
        g.addEdge(6, 3);
        g.addEdge(6, 7);


        g.addEdge(8, 10);

        g.addEdge(9, 8);

        g.addEdge(10, 9);

        g.addEdge(11, 5);
        g.addEdge(11, 0);


         */

/*
        // Test Case 2
        // Create a graph
        Graph g = new Graph(13);
        g.addEdge(0, 1);
        g.addEdge(1, 3);
        g.addEdge(1, 7);
        g.addEdge(1, 0);
        g.addEdge(2, 3);
        g.addEdge(2, 6);
        g.addEdge(3, 4);
        g.addEdge(4, 2);
        g.addEdge(5, 0);
        g.addEdge(5, 4);
        g.addEdge(6, 2);
        g.addEdge(6, 7);
        g.addEdge(6, 8);
        g.addEdge(6, 12);
        g.addEdge(7, 2);
        g.addEdge(8, 9);
        g.addEdge(9, 10);
        g.addEdge(10, 8);
        g.addEdge(10, 11);
        g.addEdge(11, 12);
        g.addEdge(12, 10);

 */

/*
        // Test Case 1
        // Create a graph
        Graph g = new Graph(12);
        g.addEdge(0, 3);

        g.addEdge(1, 0);
        g.addEdge(1, 2);

        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);

        g.addEdge(3, 1);
        g.addEdge(3, 4);

        g.addEdge(4, 5);
        g.addEdge(4, 6);

        g.addEdge(5, 7);
        g.addEdge(5, 9);

        g.addEdge(6, 7);

        g.addEdge(7, 10);

        g.addEdge(8, 5);
        g.addEdge(8, 9);

        g.addEdge(9, 11);

        g.addEdge(10, 9);

        g.addEdge(11, 8);


 */
        System.out.println("Strongly Connected Components (Separated by new line):");
        g.printSCCs();
    }
}