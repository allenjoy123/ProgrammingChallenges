package graphs.wheels.failure;
// @JUDGE_ID:  1272379  706  Java  "Easy algorithm"

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
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
        int numberOfCases = 0;
        int initial= 0;
        int target = 0;
        int[] forbidden;
        int forbiddenCount = 0;
        List<Integer> outputs = new ArrayList<>();
        while ((input = Main.ReadLn (255)) != null)
        {
            lineNumber++;
            idata = new StringTokenizer (input);
            if (lineNumber == 1) {
                numberOfCases = Integer.parseInt (idata.nextToken());
                continue;
            }
            if (idata.countTokens() == 0 && numberOfCases == 0) {
                break;
            } else if (idata.countTokens() == 0) {
                forbiddenCount = 0;
                initial= 0;
                target = 0;
                input = Main.ReadLn(255);
                initial = Integer.parseInt(input.replaceAll(" ",""));
                input = Main.ReadLn(255);
                target = Integer.parseInt(input.replaceAll(" ",""));
                
                continue;
            } else if (idata.countTokens() == 1) {
                forbiddenCount = Integer.parseInt (idata.nextToken());
                forbidden = new int[forbiddenCount];
                for (int i = 0; i < forbiddenCount; i++) {
                    input = Main.ReadLn(255);
                    forbidden[i] = Integer.parseInt (input.replaceAll(" ", ""));
                }
                outputs.add(calculateButtonPresses(initial, target, forbidden));
                numberOfCases--;
                continue;
            }
            
        }
    }

    private
    Integer calculateButtonPresses(int initial, int target, int[] forbidden) {
        int verticeCount = target > initial? target - initial: initial - target;
        int[][] graph = new int[verticeCount][5];

        
        graph = constructGraph(initial, target, forbidden);
        
        return 0;
    }

    private
    int[][] constructGraph(int initial, int target, int[] forbidden) {
        int verticeCount = target > initial? target - initial: initial - target;
        List<Integer[]> graph = new ArrayList<>();
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(initial);
        List<Integer> processed = new ArrayList<>();
        Map<Integer, Integer> parentMap = new HashMap<>();
        boolean targetFound = false;
        int numberOfPresses = 0;
        int lastFormedNumber = -1;
        while (!targetFound) {
            if (queue.isEmpty()) {
                System.out.println("test");
            }
            Integer pop = queue.poll();
            Integer[] children = new Integer[5];
            children[0] = pop;
            
            if (pop == target) {
                parentMap.put(target, pop);
                Integer node = target;
                while (node != null) {
                    System.out.printf("%d ", node);
                    node = parentMap.get(node);
                }
                targetFound = true;
                break;
            }else if(isForbidden(forbidden, pop)){
                numberOfPresses = -1;
                break;
            }
            
            for (int j = 1; j < 5; j++) {
                if (!queue.isEmpty()) {
                    break;
                }
                int parent = children[0];
                int parentDigit = (parent / (int)(Math.pow(10, j-1))) % 10;
                int targetDigit = (target / (int)(Math.pow(10, j-1))) % 10;
                if (parentDigit == targetDigit) {
                    continue;
                }
                int childDigit = -1;
                int childNumber = -1;
                childDigit = parentDigit;
                boolean isForbidden = false;
                
                while (childDigit != targetDigit) {
                    
                    if (Math.abs(targetDigit - parentDigit) > 5) {
                        if (targetDigit > parentDigit) {
                            childDigit = childDigit-1;
                        }else{
                            childDigit = childDigit+1;
                        }
                    }else if (Math.abs(targetDigit - parentDigit) <= 5){
                        if (targetDigit > parentDigit) {
                            childDigit = childDigit+1;
                        }else{
                            childDigit = childDigit-1;
                        }
                    }
                    numberOfPresses++;
                    int prefix = parent / (int) Math.pow(10, j);
                    int prefixPlusNewDigit = (prefix * 10) + childDigit;
                    int numberOfRemainingPlaces = 4 - getNumberOfDigits(prefixPlusNewDigit);
                    int remainder = parent % (int) Math.pow(10, j);
                    int numberOfDigitsOfRemainder = getNumberOfDigits(remainder);
                    int lastNMinus1DigitsOfR = numberOfDigitsOfRemainder == numberOfRemainingPlaces ?
                            remainder:
                            remainder%(int)Math.pow(10,
                                    (numberOfDigitsOfRemainder -1) );

                    if (numberOfDigitsOfRemainder <= numberOfRemainingPlaces) { // that means remainder has one more 
                        // zero prefix
                        lastNMinus1DigitsOfR = remainder;
                    }else if(numberOfDigitsOfRemainder > numberOfRemainingPlaces){
                        lastNMinus1DigitsOfR = remainder%(int)Math.pow(10,
                                (numberOfDigitsOfRemainder -1) );
                    }

                    childNumber =
                            prefixPlusNewDigit * (int)Math.pow(10, numberOfRemainingPlaces) + lastNMinus1DigitsOfR;

                    isForbidden = isForbidden(forbidden, childNumber);
                    if (isForbidden) {
                        break;
                    }
                    children[j] = childNumber;
                    parentMap.put(childNumber, parent);
                    lastFormedNumber = childNumber;
                    if (childNumber == target) {
                        Integer node = childNumber;
                        while (node != null) {
                            System.out.printf("%s ", node.toString());
                            node = parentMap.get(node);
                        }
                        targetFound = true;
                        break;
                    }
                }
                if (targetFound) {
                    break;
                }
                if (!isForbidden) {
                    queue.add(childNumber);
                } else if (lastFormedNumber != -1) {
                    queue.add(lastFormedNumber);
                }
                
            }
            processed.add(pop);
            if (targetFound) {
                break;
            }
            graph.add(children);
        }
        System.out.println(numberOfPresses);

        return  null;
    }

    private
    boolean isForbidden(int[] forbidden, int childNumber) {
        boolean isForbidden = false;
        for (int bidden : forbidden) {
            if (childNumber == bidden) {
                isForbidden = true;
                break;
            }
        }
        return isForbidden;
    }

    private
    int getNumberOfDigits(int remainder) {
        int quotient = -1;
        int numberOfDigitsInRemainder = 1;
        while ((quotient = remainder / 10) != 0) {
            remainder = quotient;
            numberOfDigitsInRemainder++;
            
        }
        return numberOfDigitsInRemainder;
    }
}
