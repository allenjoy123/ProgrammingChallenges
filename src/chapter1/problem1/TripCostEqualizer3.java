package chapter1.problem1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.partitioningBy;

public class TripCostEqualizer3 {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        String input;
        int currentTripStudentCount = 0;
        List<String> outputs = new ArrayList<>();
        while ((input = reader.readLine()) != null) {
            currentTripStudentCount = Integer.parseInt(input);
            if(currentTripStudentCount == 0){
                break;
            }
            long totalAmount = 0l;
            long[] amounts = new long[currentTripStudentCount];
            for(int studentNumber = 1; studentNumber <= currentTripStudentCount; studentNumber++){
                amounts[studentNumber-1] = Long.parseLong(reader.readLine().replaceAll("\\.",""));
                totalAmount += amounts[studentNumber-1];
            }
            long average = totalAmount/currentTripStudentCount;
            long remainder = totalAmount%currentTripStudentCount;
            Map<Boolean, List<Long>> diff =
                    stream(amounts).map(x -> x - average).boxed().collect(partitioningBy(x -> x > 0));
            Long reduced = diff.get(false).stream().reduce(Long::sum).get();
            long sum = abs(reduced);
            long len = diff.get(true).size();
            remainder = len <= remainder ? remainder - len : 0;
            outputs.add("$"+ BigDecimal.valueOf(sum + remainder, 2));


        }
        outputs.forEach(System.out::println);
    }

    private static long getAmountToChangeHands(long[] amounts, long average, long remainder) {

        long amountToChangeHands = 0;

        int numOfStudentsWhoSpentMoreThanAvg = 0;
        for(long amount: amounts){
            if(amount > average) {
                amountToChangeHands += (amount - average);
                numOfStudentsWhoSpentMoreThanAvg++;
            }
        }
        // distribute remainder among all of the students
        remainder = remainder > numOfStudentsWhoSpentMoreThanAvg? remainder - numOfStudentsWhoSpentMoreThanAvg: 0;

        return amountToChangeHands + remainder;

    }
}