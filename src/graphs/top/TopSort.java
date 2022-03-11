package graphs.top;

import graphs.Graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public
class TopSort {
    public
    int[] createTopologicalSort(DAGraph graph) {
        int[] indegree = new int[Graph.MAXV + 1];
        int[] sorted = new int[graph.nvertices+1];
        findIndegrees(graph, indegree);

        Stack<Integer> zeroIn = new Stack<>();
        for (int i = 1; i <= graph.nvertices; i++) {
            if (indegree[i] == 0) {
                zeroIn.add(i);
            }
        }

        int k=0;
        while (!zeroIn.isEmpty()) {
            k++;
            Integer poll = zeroIn.pop();
            sorted[k] = poll;
            for (int j = 0; j < graph.degree[poll]; j++) {
                int vertex = graph.edges[poll][j];
                indegree[vertex]--;
                if (indegree[vertex] == 0) { // indegree must be zero for a DAG
                    zeroIn.push(vertex);
                }
            }
        }
        if (k != graph.nvertices) {
            System.out.println("the graph is cyclic. Topological sort not possible.");
        }
        return sorted;
    }

    private
    void findIndegrees(DAGraph graph, int[] indegree) {
        Arrays.fill(indegree, -1);
        //initialize 
        for (int i = 1; i <= graph.nvertices; i++) {
            indegree[i] = 0;
        }
        for (int i = 1; i < graph.nvertices; i++) {
            for (int j = 0; j < graph.degree[i]; j++) {
                indegree[graph.edges[i][j]] = 0;
                indegree[graph.edges[i][j]]++;
            }
        }
    }
}
