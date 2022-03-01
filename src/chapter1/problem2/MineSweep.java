package chapter1.problem2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MineSweep {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        String input;
        int numRows = 0;
        int numCols = 0;
        int[][] grid = null;
        int currentRow = -1;
        List<Object> fields = new ArrayList<>();
        while ((input = reader.readLine()) != null) {
            List<String> str = Arrays.stream(input.trim().split(""))
                    .filter(x -> !x.equals("")).collect(Collectors.toList());

            if (str.size() == 2) {
                if(grid != null){
                    fields.add(grid);
                }
                int firstNumber = Integer.parseInt(str.get(0));
                int secondNumber = Integer.parseInt(str.get(1));
                if (firstNumber == secondNumber && firstNumber == 0) {
                    int fieldNumber = 0;
                    for(Object o: fields) {
                        int[][] field = (int[][]) o;
                        printGrid(field.length, field[0].length,
                                (int[][])field, ++fieldNumber);
                    }
                    break;
                }


                numRows = firstNumber;
                numCols = secondNumber;
                grid = new int[numRows][numCols];
                currentRow = -1;
            }else{
                currentRow++;
                int colIndex = 0;
                for (String e : str) {
                    if("*".equals(e)){
                        grid[currentRow][colIndex] = -1; // indicating a mine
                        updateMineCountOfSurroundingSquares(currentRow, colIndex, numRows, numCols, grid);
                    }
                    colIndex++;
                }
            }
        }
    }

    private static void printGrid(int numRows, int numCols, int[][] grid, int fieldNumber) {
        // print the grid
        System.out.println("Field #"+fieldNumber+":");
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                int outputVal = grid[rowIndex][colIndex];
                System.out.print(outputVal == -1? "*":outputVal);
            }
            System.out.print(System.lineSeparator());
        }
        System.out.print(System.lineSeparator()); // empty line between fields
    }

    private static void updateMineCountOfSurroundingSquares(int rowIndex, int colIndex,
                                                            int totalRows, int totalCols,
                                                            int[][] grid) {
        // case 1 : same row, decrement a column
        if(isWithinBoundsAndNotAMine(rowIndex, colIndex - 1, grid, totalRows, totalCols)){
            grid[rowIndex][colIndex-1] = grid[rowIndex][colIndex-1]+1;
        }

        // case 2: same row, increment a column
        if(isWithinBoundsAndNotAMine(rowIndex, colIndex + 1, grid, totalRows, totalCols)){
            grid[rowIndex][colIndex+1] = grid[rowIndex][colIndex+1]+1;
        }

        // case 3: decrement a row, decrement a column (top row left)
        if(isWithinBoundsAndNotAMine(rowIndex - 1, colIndex - 1, grid, totalRows, totalCols)){
            grid[rowIndex-1][colIndex-1] = grid[rowIndex-1][colIndex-1]+1;
        }

        // case 4: decrement a row (top row center)
        if(isWithinBoundsAndNotAMine(rowIndex - 1, colIndex, grid, totalRows, totalCols)){
            grid[rowIndex-1][colIndex] = grid[rowIndex-1][colIndex]+1;
        }

        // case 5: decrement a row increment a column (top row center)
        if(isWithinBoundsAndNotAMine(rowIndex - 1, colIndex + 1, grid, totalRows, totalCols)){
            grid[rowIndex-1][colIndex + 1] = grid[rowIndex-1][colIndex + 1]+1;
        }

        // case 6: increment a row, decrement a column (top row left)
        if(isWithinBoundsAndNotAMine(rowIndex + 1, colIndex - 1, grid, totalRows, totalCols)){
            grid[rowIndex+1][colIndex-1] = grid[rowIndex+1][colIndex-1]+1;
        }

        // case 7: increment a row (top row center)
        if(isWithinBoundsAndNotAMine(rowIndex + 1, colIndex, grid, totalRows, totalCols)){
            grid[rowIndex+1][colIndex] = grid[rowIndex+1][colIndex]+1;
        }

        // case 8: increment a row, increment a column (top row center)
        if(isWithinBoundsAndNotAMine(rowIndex + 1, colIndex + 1, grid, totalRows, totalCols)){
            grid[rowIndex+1][colIndex + 1] = grid[rowIndex+1][colIndex + 1]+1;
        }

    }

    private static boolean isWithinBoundsAndNotAMine(int rowIndex, int colIndex, int[][] grid, int totalRows, int totalCols) {
        return (rowIndex < totalRows && rowIndex >= 0
        && colIndex < totalCols && colIndex >= 0 && grid[rowIndex][colIndex] != -1);
    }
}