package graphs.dfs;

import graphs.Graph;

public
class DFSRecursive {
    private int[] parent;
    boolean[] discovered;
    boolean[] processed;

    public
    DFSRecursive(Graph graph) {
        parent = new int[graph.nvertices+1];
        discovered = new boolean[graph.nvertices+1];
        processed = new boolean[graph.nvertices+1];
        initializeSearch(graph, discovered, processed, parent);
    }
    public
    void dfsRecursive(Graph graph, int start) {

        discovered[start] = true;
        discoveredVertex(start);

        int vertex = start;
        processVertex(vertex);
        
        for (int j = 0; j < graph.degree[vertex]; j++) {
            int adjVertex = graph.edges[vertex][j];
            if(!discovered[adjVertex]){
                discovered[adjVertex] = true;
                discoveredVertex(adjVertex);
                if(parent[adjVertex] == -1){
                    parent[adjVertex] = vertex; // to get the closest parent to the root    
                }
                dfsRecursive(graph, adjVertex);
            }else if(!processed[adjVertex]){
                discoveredVertex(adjVertex);
                boolean cycleFound = processEdge(vertex, adjVertex);
                if (cycleFound) {
                    System.out.printf( "Cycle Found: ");
                    printShortestPathToRoot(adjVertex, vertex);
                    System.out.println();
                    
                }
            }
            
        }
        processed[vertex] = true;
      
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

    /**
     * 
     * @param vertex start vertex of edge
     * @param adjVertex endpoint for edge
     * @return true if cycle found
     */
    private
    boolean processEdge(int vertex, int adjVertex) {
        System.out.println("processed edge: "+vertex+"-"+adjVertex);
        if (parent[vertex] != adjVertex) {
            // cycle found
            return true;
        }
        return false;
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
