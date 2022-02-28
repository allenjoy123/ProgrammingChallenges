package chapter1.problem1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripCostEqualizer {
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
            double totalAmount = 0d;
            double[] amounts = new double[currentTripStudentCount];
            for(int studentNumber = 1; studentNumber <= currentTripStudentCount; studentNumber++){
                amounts[studentNumber-1] = Double.parseDouble(reader.readLine());
                totalAmount += amounts[studentNumber-1];
            }
            double average = totalAmount/currentTripStudentCount;
            outputs.add("$"+getAmountToChangeHands(amounts, average));


        }
        outputs.forEach(System.out::println);
    }

    private static double getAmountToChangeHands(double[] amounts, double average) {

        double amountToChangeHands = 0d;

        for(double amount: amounts){
            if(amount > average) {
                amountToChangeHands += (amount - average);
            }
        }
        return amountToChangeHands;

    }
}