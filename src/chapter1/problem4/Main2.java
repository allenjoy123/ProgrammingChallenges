package chapter1.problem4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main2 {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public Main2(){

    }

    private Map<Integer, Boolean[]> buildNumbersParams() {
        Map<Integer, Boolean[]> map = new HashMap<>();
        map.put(0, Arrays.asList(true, false, true, true, true, true, true).toArray(new Boolean[7]));
        map.put(1, Arrays.asList(false, false, false, false, true, false, true).toArray(new Boolean[7]));
        map.put(2, Arrays.asList(true, true, true, false, true, true, false).toArray(new Boolean[7]));
        map.put(3, Arrays.asList(true, true, true, false, true, false, true).toArray(new Boolean[7]));
        map.put(4, Arrays.asList(false, true, false, true, true, false, true).toArray(new Boolean[7]));
        map.put(5, Arrays.asList(true, true, true, true, false, false, true).toArray(new Boolean[7]));
        map.put(6, Arrays.asList(true, true, true, true, false, true, true).toArray(new Boolean[7]));
        map.put(7, Arrays.asList(true, false, false, false, true, false, true).toArray(new Boolean[7]));
        map.put(8, Arrays.asList(true, true, true, true, true, true, true).toArray(new Boolean[7]));
        map.put(9, Arrays.asList(true, true, true, true, true, false, true).toArray(new Boolean[7]));
        return  map;
    }
    /**
     * 
     * @param scale number of "-" signs for the horizontal segments and number of "|" signs for the vertical segments
     * @param isTop if there should be a top horizontal row
     * @param isCenter if there should be a center horizontal row
     * @param isBottom if there should be a bottom horizontal row
     * @param isTopLeft if there should be a top left vertical line
     * @param isTopRight if there should be a top right vertical line
     * @param isBottomLeft if there should be a bottom left vertical line
     * @param  isBottomRight if there should be a bottom right vertical line
     * @return the LCD number created from the given parameters
     */
    private String[][] buildNumber(int scale, boolean isTop, boolean isCenter, boolean isBottom,
            boolean isTopLeft, boolean isTopRight, boolean isBottomLeft, boolean isBottomRight) {
        StringBuilder builder = new StringBuilder();
        int numRows = (2*scale)+3;
        int numCols = scale + 2;
        String[][] lcdNumber = new String[numRows][numCols];
        String horizontalSegment = "-";
        String verticalSegment = "|";
        String space = " ";
        String newLine = "\n";
        int rowIndex = 0;
        int colIndex = 0;
        lcdNumber[rowIndex++] =
                builder.append(space).append(isTop ? getRepeat(scale, horizontalSegment) : getRepeat(scale, space)).append(space).toString().split("");
        builder=new StringBuilder();
        rowIndex = buildVerticalSegmentLine(scale, isTopLeft, isTopRight, builder, numCols, verticalSegment, space,
                newLine,
                lcdNumber, rowIndex);
        String segmentOrSpace = isCenter? horizontalSegment: space;
        lcdNumber[rowIndex++] =
                builder.append(space).append(getRepeat(scale, segmentOrSpace)).append(space).toString().split("");
        builder=new StringBuilder();
        rowIndex = buildVerticalSegmentLine(scale, isBottomLeft, isBottomRight, builder, numCols, verticalSegment,
                space,
                newLine, lcdNumber, rowIndex);
        lcdNumber[rowIndex++] =
                builder.append(space).append(isBottom ? getRepeat(scale, horizontalSegment) : getRepeat(scale, space)).append(space).toString().split("");
        return  lcdNumber;

    }

    private String getRepeat(int scale, String horizontalSegment) {
        StringBuilder repeat = new StringBuilder();
        for (int i = 0; i < scale; i++) {
            repeat.append(horizontalSegment);
        }
        return repeat.toString();
    }

    private int buildVerticalSegmentLine(int scale, boolean isLeft, boolean isRight, StringBuilder builder,
            int numCols, String verticalSegment, String space, String newLine, String[][] lcdNumber, int rowIndex) {
        if (isLeft && isRight) {
            for (int i = 0; i < scale; i++) {
                StringBuilder verticalSegmentLine = new StringBuilder();
                lcdNumber[rowIndex++] = verticalSegmentLine.append(verticalSegment).append(getRepeat(numCols-2,
                                space))
                        .append(verticalSegment).toString().split("");
            }
        } else if (isLeft) {
            for (int i = 0; i < scale; i++) {
                StringBuilder verticalSegmentLine = new StringBuilder();
                lcdNumber[rowIndex++] =
                        verticalSegmentLine.append(verticalSegment).append(getRepeat(numCols - 1, space)).toString().split("");
            }
        } else if (isRight) {
            for (int i = 0; i < scale; i++) {
                StringBuilder verticalSegmentLine = new StringBuilder();
                lcdNumber[rowIndex++] =
                        verticalSegmentLine.append(getRepeat(numCols - 1, space)).append(verticalSegment).toString().split("");
            }
        }
        return rowIndex;
    }

    public static void main(String[] args) throws IOException {
        Main2 lda =new Main2();
        String input;
        List<String[][]> outputs = new ArrayList<>();
        while ((input = reader.readLine()) != null) {

            List<String> str = Arrays.stream(input.trim().split(" "))
                    .filter(x -> !x.equals("")).collect(Collectors.toList());
            if ("0".equals(str.get(0)) && "0".equals(str.get(1))) {
                break;
            }
            int originalScale = Integer.parseInt(str.get(0));
            String[] numbers = str.get(1).split("");
            Map<Integer, Boolean[]> numbersParams = lda.buildNumbersParams();
            int blankColumnCount = numbers.length - 1;
            int colCountOfNumbers = (originalScale + 2) * numbers.length;
            int numOfRows = (2 * originalScale) + 3;
            int numColumns = originalScale + 2;
            String[][] lcdNumbers = new String[numOfRows][colCountOfNumbers + blankColumnCount];
            int numCount= 0;
            int colIndex = 0;
            // get numbers for scale 1
            int scale = 1;
            for (String number : numbers) {
                Boolean[] numberParams = numbersParams.get(Integer.parseInt(number));
                String[][] lcdNumber = lda.buildNumber(scale, numberParams[0], numberParams[1], numberParams[2],
                    numberParams[3],
                        numberParams[4], numberParams[5], numberParams[6]);
                for (int j = 0; j < lcdNumber[0].length; j++) {
                    for (int i = 0; i < lcdNumber.length; i++) {
                        lcdNumbers[i][colIndex] = lcdNumber[i][j];
                    }
                    colIndex++;
                }
                numCount++;
                if(numCount<numbers.length){
                    for (int i = 0; i < numOfRows; i++) {
                        lcdNumbers[i][colIndex] = " "; // space
                    }
                    colIndex++;
                }

            }

            outputs.add(lcdNumbers);



        }
        for (String[][] lcdNumbers : outputs) {
            int numberOfCols = lcdNumbers[0].length;
            int numberOfRows = lcdNumbers.length;
            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfCols; j++) {
                    System.out.print(lcdNumbers[i][j] == ""? " ": lcdNumbers[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}