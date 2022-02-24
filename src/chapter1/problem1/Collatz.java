package chapter1.problem1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Collatz {
    private static int MAX = 1000000;
    private int[] cycleLengths = new int[MAX];

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Collatz() {
        Deque<Long> cycleStack = new ArrayDeque<>();
        // assuming that a cycle-length will not overflow a 32-bit integer value
        // but a member of a cycle could possibly overflow a 32-bit integer value
        HashMap<Long, Integer> surplus = new HashMap<>();
        // calculate for all numbers the cycle-length of each number
        int cycleLenTotal = 0;
        cycleLengths[0] = 1;
        for (int member = 2; member <= MAX; member++) {
            long n = member;

            int cycleLength = 2;
            while (n != 1) {
                cycleStack.push(n);
                int previous = getCycleLength(n, surplus);
                if (previous > 0) {
                    cycleLength = previous;
                    break;
                }
                n = n % 2 == 0 ? n / 2 : n * 3 + 1;
            }
            cycleLenTotal = cycleStack.size() + cycleLength;
            while (!cycleStack.isEmpty()) {
                setCycleLength(cycleStack.pop(), cycleLength++, surplus);
            }


        }


    }

    private void setCycleLength(long member, int cycleLength, HashMap<Long, Integer> surplus) {
        if(member < MAX){
            cycleLengths[ (int) member -1] = cycleLength;
        }else{
            surplus.put(member, cycleLength);
        }
    }

    private int getCycleLength(long index, HashMap<Long, Integer> surplus) {
        return (index < MAX)? cycleLengths[(int) index - 1 ]:
                (surplus.getOrDefault(index, 0));
    }

    public static void main(String[] args) throws IOException {
        Collatz collatz = new Collatz();
        String input;
        while ((input = reader.readLine()) != null &&
                !input.trim().equalsIgnoreCase("")) {
            List<String> str = Arrays.stream(input.trim().split(" "))
                    .filter(x -> !x.equals("")).collect(Collectors.toList());
            int x[] = new int[]{Integer.parseInt(str.get(0)), Integer.parseInt(str.get(1))};
            System.out.println(x[0] + " " + x[1] + " " +
                    IntStream.rangeClosed(Math.min(x[0], x[1]), Math.max(x[0], x[1])).map(v -> collatz.cycleLengths[v-1])
                    .max().getAsInt());
        }
    }
}
