package graphs.wheels;
/* @JUDGE_ID:  1272379  10067  Java  "BFS" */

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Main
{

    public static final String SPACE = " ";

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

        String input = "";
        
        int numberOfCases = 0;
        Node nodeStart = null;
        Node nodeEnd = null;
        Node[] forbidden;
        
        List<Integer> outputs = new ArrayList<>();
        
        input = Main.ReadLn(255);
        while(input != null && input.trim().equals("")){
            input = Main.ReadLn(255);
        }
        numberOfCases = Integer.parseInt(input);
        while (numberOfCases > 0) {
            nodeStart = null;
            nodeEnd = null;
            forbidden = null;
            input = Main.ReadLn(255);
            if (input == null) {
                break;
            }
            while(input != null && input.trim().equals("")){
                input = Main.ReadLn(255);
            }
            if (input == null) {
                break;
            }
            String initial = input;
            String[] numbers = initial.split(SPACE);
            nodeStart = new Node(Arrays.asList(Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1]),
                    Integer.parseInt(numbers[2]),
                    Integer.parseInt(numbers[3])));
            String target = Main.ReadLn(255);
            numbers = target.split(SPACE);
            nodeEnd = new Node(Arrays.asList(Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1]),
                    Integer.parseInt(numbers[2]),
                    Integer.parseInt(numbers[3])));
            int numberOfForbidden = Integer.parseInt(Main.ReadLn(255));
            forbidden = new Node[numberOfForbidden];
            for (int j = 0; j < numberOfForbidden; j++) {
                numbers = Main.ReadLn(255).split(SPACE);
                forbidden[j] = new Node(Arrays.asList(Integer.parseInt(numbers[0]),
                        Integer.parseInt(numbers[1]),
                        Integer.parseInt(numbers[2]),
                        Integer.parseInt(numbers[3])));
            }
            Integer buttonPresses = calculateButtonPresses(nodeStart, nodeEnd, forbidden);
            int answer = buttonPresses;
            outputs.add(answer);
            System.out.println(answer);
            numberOfCases--;
        }

        if (outputs.isEmpty()) {
            System.out.println();
        }
        
    }

    private
    Integer calculateButtonPresses(Node initial, Node target, Node[] forbidden) {

        if (Arrays.equals(initial.node, target.node)) {
            return 0;
        }

        Deque<Node> queue = new ArrayDeque<>();
        Map<Node, Node> parentMap = new HashMap<>();
        queue.addLast(initial);
        parentMap.put(initial, null);
        
        while (!queue.isEmpty()) {

            Node node = queue.pollFirst();

            for (Node adjacent : Node.getAdjancentNodes(node)) {
                if (adjacent.equals(target)) {

                    if (isForbidden(adjacent, forbidden)) {
                        return -1;
                    }
                    parentMap.put(adjacent, node);
                    return findNumberOfNodesToParent(parentMap, target);
                }
                
                if(!parentMap.containsKey(adjacent) && !isForbidden(adjacent, forbidden)){
                    queue.addLast(adjacent);
                    parentMap.put(adjacent, node);
                    
                }
                
            }
        }
        return -1;
    }

    private
    Integer findNumberOfNodesToParent(Map<Node, Node> parentMap, Node target) {
        Node parent = parentMap.get(target);
        if (parent == null) {
            return 0;
        }else{
            return 1 + findNumberOfNodesToParent(parentMap, parent);
        }
    }

    private
    boolean isForbidden(Node node, Node[] forbidden) {
        
        boolean isForbidden = false;
        for (Node f : forbidden) {
            if (f.equals(node)) {
                isForbidden = true;
                break;
            }
        }
        return isForbidden;
    }

    static class Node{
        public int[] node;
        public boolean isVisited; // this will be overridden with a new object equal to it. it needs to be outside the 
        // object

        @Override
        public
        boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Node node1 = (Node) o;
            return Arrays.equals(node, node1.node);
        }

        @Override
        public
        int hashCode() {
            return Arrays.hashCode(node);
        }

        Node(List<Integer> values) {
            int i = 0;
            node = new int[values.size()];
            for (Integer value : values) {
                node[i++] = value;
            }
            this.isVisited = false;
        }

        private
        static Node adjacentNode(Node node, int index, int p) {
            Node node1 = new Node(Arrays.asList(node.node[0], node.node[1], node.node[2], node.node[3]));
            node1.node[index] += p;

            if (node1.node[index] < 0) {
                node1.node[index] = 9;
            } else if (node1.node[index] > 9) {
                node1.node[index] %= 10;
            }
            
            return  node1;
        }

        private
        static List<Node> getAdjancentNodes(Node node) {
            List<Node> adjancents = new ArrayList<>();

            for (int i = 0; i < node.node.length; i++) {
                adjancents.add(adjacentNode(node, i, 1));
                adjancents.add(adjacentNode(node, i, -1));
            }
            
            return adjancents;
        }
    }

}

