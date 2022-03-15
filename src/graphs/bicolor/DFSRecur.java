package graphs.bicolor;

import graphs.Graph;

public
class DFSRecur {
    private int[] parent;
    boolean[] discovered;
    boolean[] processed;
    public static int WHITE = 1;
    public static int BLACK = 2;
    int[] color;

    public
    DFSRecur(Graph graph) {
        parent = new int[graph.nvertices];
        discovered = new boolean[graph.nvertices];
        processed = new boolean[graph.nvertices];
        color = new int[graph.nvertices];
        initializeSearch(graph, discovered, processed, parent);
    }
    public
    boolean isBiColorViaDFS(Graph graph, int start) {

        
        discoveredVertex(start);

        int vertex = start;
        
        for (int j = 0; j < graph.degree[vertex]; j++) {
            int adjVertex = graph.edges[vertex][j];
            if(!discovered[adjVertex]){
                discovered[adjVertex] = true;
                discoveredVertex(adjVertex);
                if(parent[adjVertex] == -1){
                    parent[adjVertex] = vertex; // to get the closest parent to the root    
                }
                boolean isBiColor = isBiColorViaDFS(graph, adjVertex);
                if (!isBiColor) {
                    return false;
                }
            }else if(!processed[adjVertex]){
                
                boolean cycleFound = processEdge(vertex, adjVertex);
                if (cycleFound) {
                    if (color[vertex] == color[adjVertex]) {
                        return false;
                    }
                }
            }
            
        }
        processed[vertex] = true;
        
        return true;
      
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
        for (int i = 0; i < graph.nvertices; i++) {
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
        if (parent[vertex] != adjVertex) {
            // cycle found
            return true;
        }
        return false;
    }

    private
    void discoveredVertex(int vertex) {

        discovered[vertex] = true;
        if (parent[vertex] == -1) {
            color[vertex] = WHITE;
        }else if(color[parent[vertex]] == WHITE){
            color[vertex] = BLACK;
        } else if(color[parent[vertex]] == BLACK) {
            color[vertex] = WHITE;
        }
    }

    private
    void processVertex(int vertex) {



    }
}
