package graphs.top;

public
class DAGraph {
    public static final int MAXV = 100; // MAXIMUM NUMBER OF VERTICES
    public static final int MAXDEGREE = 50; // MAXIMUM VERTEX OUTDEGREE

    public int[][]
            edges
            = new int[MAXV + 1][MAXDEGREE];
            //adjacency info
    
    public int[] degree = new int[MAXV+1]; // outdegree of each vertex
    public int nvertices; // number of vertices in graph
    public int nedges; // number of edges in graph

    public
    DAGraph() {
        int i ; //counter
        nvertices = 0;
        nedges = 0;

        for (
                i
                = 1; i <= MAXV; i++) {
            degree[i] = 0;
        }
    }
}
