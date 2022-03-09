package graphs.bfs;

import graphs.Graph;

import java.util.PriorityQueue;
import java.util.Queue;

public
class BFS {
    public
    void bfs(Graph graph, int start) {
        boolean[] discovered = new boolean[graph.nvertices+1];
        boolean[] processed = new boolean[graph.nvertices+1];
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
                }
            }
            
        }
      
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
