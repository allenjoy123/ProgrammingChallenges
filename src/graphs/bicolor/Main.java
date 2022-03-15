package graphs.bicolor;
// @JUDGE_ID:  1272379  10004  Java  "Depth First Search"

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Main
{
    static String ReadLn (int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte [maxLg];
        int lg = 0, car = -1;

        try
        {
            while (lg < maxLg)
            {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin [lg++] += car;
            }
        }
        catch (IOException e)
        {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String (lin, 0, lg));
    }

    public static void main (String args[])  // entry point from OS
    {
        Main myWork = new Main();  // create a dinamic instance
        myWork.Begin();            // the true entry point
    }

    void Begin()
    {
        String input;
        StringTokenizer idata;
        int a, b;

        int lineNumber = 0;
        int m =0; // number of edges
        Graph graph = new Graph();
        
        boolean isDirected = false;
        int startVertex = 0;
        List<String> outputs = new ArrayList<>();
        while ((input = Main.ReadLn (255)) != null)
        {
            ++lineNumber;
            idata = new StringTokenizer (input);
            if (lineNumber == 1) {
                a = Integer.parseInt (idata.nextToken());
                if (a == 0) {
                    break;
                }
                graph.nvertices = a;
                continue;
            }
            if (lineNumber == 2) {
                b = Integer.parseInt (idata.nextToken());
                if (b == 0) {
                    break;
                }
                m = b;
                continue;
            }

            a = Integer.parseInt (idata.nextToken());
            if (!idata.hasMoreTokens() && a == 0) {
                break;
            }
            if (lineNumber == 3) {
                startVertex = a;
            }
            
            b = Integer.parseInt (idata.nextToken());

            insertEdge(graph, a, b, isDirected    );

            if (lineNumber == m+2) {
                DFSRecursive dfsRecursive = new DFSRecursive(graph);
                if(dfsRecursive.isBiColorViaDFS(graph, startVertex)){
                    outputs.add("BICOLORABLE.");
                }else {
                    outputs.add("NOT BICOLORABLE.");
                }
                lineNumber = 0;
                m =0; // number of edges
                graph = new Graph();
                continue;
            }

        }
        for (String output : outputs) {
            System.out.println(output);
        }

    }

    private
    void insertEdge(Graph graph, int x, int y, boolean directed) {
        if (graph.degree[x] >= Graph.MAXDEGREE) {
            System.out.println("Warning: insertion exceeds max degree");
        }
        int vertexDegree = graph.degree[x];
        graph.edges[x][vertexDegree] =y;
        graph.degree[x] = ++vertexDegree;

        if (directed == false) {
            insertEdge(graph, y, x, true);
        } else{
            graph.nedges++;
        }
    }
}

class DFSRecursive {
    private int[] parent;
    boolean[] discovered;
    boolean[] processed;
    public static int WHITE = 1;
    public static int BLACK = 2;
    int[] color;

    public
    DFSRecursive(Graph graph) {
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

class Graph {
    public static final int MAXV = 200; // MAXIMUM NUMBER OF VERTICES
    public static final int MAXDEGREE = 199; // MAXIMUM VERTEX OUTDEGREE

    public int[][]
            edges
            = new int[MAXV][MAXDEGREE];
    //adjacency info

    public int[] degree = new int[MAXV]; // outdegree of each vertex
    public int nvertices; // number of vertices in graph
    public int nedges; // number of edges in graph

    public
    Graph() {
        int i ; //counter
        nvertices = 0;
        nedges = 0;

        for (
                i
                        = 0; i < MAXV; i++) {
            degree[i] = 0;
        }
    }
}
