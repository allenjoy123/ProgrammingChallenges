package graphs.bfs;

import graphs.Graph;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public
class BFS {
    private int[] parent;
    public BFS(Graph graph, int start) {
        boolean[] discovered = new boolean[graph.nvertices+1];
        boolean[] processed = new boolean[graph.nvertices+1];
        parent = new int[graph.nvertices+1];
        initializeSearch(graph, discovered, processed, parent);
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(start);
        discovered[start] = true;
        discoveredVertex(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            processVertex(vertex);
            processed[vertex] = true;
            for (int j = 0; j < graph.degree[vertex]; j++) {
                int adjVertex = graph.edges[vertex][j];
                if(!discovered[adjVertex]){
                    queue.add(adjVertex);
                    discovered[adjVertex] = true;
                    discoveredVertex(adjVertex);
                    if(parent[adjVertex] == -1){
                        parent[adjVertex] = vertex; // to get the closest parent to the root    
                    }
                }
                processEdge(vertex, adjVertex);
            }
            
        }

        
      
    }

    public
    void printShortestPathToRoot(int start, int end) {

        
        int root = 0;

        int index = 0;
        for (int p : parent) {
            if (p == -1) {
                root = index;
                break;
            }
            index++;
        }

        if (start != root) {
            System.out.println("The shortest path tree obtained from BFS is only useful if BFS was performed with " +
                               "'start' as the root of the search.");
            return;
        }

        if (start == end || end == -1) {
            System.out.printf("%d ", start);
        }else{
            printShortestPathToRoot(start, parent[end]);
            System.out.printf("%d ", end);
        }
        
    }

    private
    void initializeSearch(Graph graph, boolean[] discovered, boolean[] processed, int[] parent) {
        for (int i = 1; i <= graph.nvertices; i++) {
            discovered[i] = processed[i] = false;
            parent[i] = -1;
        }
    }

    private
    void processEdge(int vertex, int adjVertex) {
        System.out.println("processed edge: "+vertex+"-"+adjVertex);
    }

    private
    void discoveredVertex(int start) {
        System.out.println("discovered: "+start);
    }

    private
    void processVertex(int vertex) {

        System.out.println("processed vertex: "+vertex);
    }
}
