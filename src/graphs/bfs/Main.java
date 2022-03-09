package graphs.bfs;// @JUDGE_ID:  1272379  706  Java  "Easy algorithm"

import graphs.Graph;

import java.io.IOException;
import java.util.StringTokenizer;

class Main
{
    static String ReadLn (int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte [maxLg];
        int lg = 0, car = -1;
        String line = "";

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
        int a, b, min, max, num, n, cycle, cyclemax;

        int lineNumber = 0;
        int m =0; // number of edges
        int x, y; // vertices in edge (x,y)
        Graph graph = new Graph();
        
        boolean directed = false;
        while ((input = Main.ReadLn (255)) != null)
        {
            ++lineNumber;
            idata = new StringTokenizer (input);
            a = Integer.parseInt (idata.nextToken());
            b = Integer.parseInt (idata.nextToken());
            if (lineNumber == 1) {
                graph.nvertices = a;
                m = b;
                continue;
            }
            insertEdge(graph, a, b, directed    );
            if (lineNumber > m) {
                break;
            }
        }

        printGraph(graph);
        BFS bfs = new BFS();
        bfs.bfs(graph, 1);
    }

    private
    void printGraph(Graph graph) {
        for (int
             i
             = 1; i <= graph.nvertices; i++) {
            if (graph.degree[i] == 0) {
                continue;
            }
            System.out.printf("%s: ", i);
            for (int
                    j
                    = 0; j < graph.degree[i]; j++) {
                System.out.printf(" %d", graph.edges[i][j]);
            }
            System.out.println();
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
