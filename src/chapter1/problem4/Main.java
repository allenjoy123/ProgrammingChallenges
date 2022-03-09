package chapter1.problem4;
/* @JUDGE_ID:  1272379  706  Java  "Easy algorithm" */
/* @BEGIN_OF_SOURCE_CODE */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static final String SPACE = " ";
    public static final String PIPE = "|";
    public static final String DASH = "-";
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public Main(){

    }

    private Map<Integer, Integer> buildNumbersParams() {
        Map<Integer, Integer> map = new HashMap<>();
        // 0 = 11 101 11 = 0x77
        map.put(0, 0x77);
        // 1 = 00 000 11 = 0x03
        map.put(1, 0x03);
        // 2 = 10 111 10 = 0x5E
        map.put(2, 0x5E);
        // 3 = 00 111 11 = 0x1F
        map.put(3, 0x1F);
        // 4 = 01 010 11 = 0x2B
        map.put(4, 0x2B);
        // 5 = 01 111 01 = 0x3D
        map.put(5, 0x3D);
        // 6 = 11 111 01 = 0x7D
        map.put(6, 0x7D);
        // 7 = 00 100 11 = 0x13
        map.put(7, 0x13);
        // 8 = 11 111 11 = 0x7F
        map.put(8, 0x7F);
        // 9 = 01 111 11 = 0x3F
        map.put(9, 0x3F);
        return  map;
    }

    public static void main(String[] args) throws IOException {
        Main lda =new Main();
        String input;
        List<List<List<String>>> outputs = new ArrayList<>();
        StringTokenizer idata;
        String a, b;
        while ((input = reader.readLine()) != null) {

            idata = new StringTokenizer (input);
            a = (idata.nextToken());
            b = (idata.nextToken());
            
            if (a.equals(b) && "0".equals(a)) {
                break;
            }
            int originalScale = Integer.parseInt(a);
            String[] numbers = String.valueOf(b).split("");
            Map<Integer, Integer> segmentInfo = lda.buildNumbersParams();
            int numCount= 0;
            List<List<String>> listOfColumns = new ArrayList<>();
            for (String number : numbers) {
                Integer info = segmentInfo.get(Integer.parseInt(number));
                
                listOfColumns.add(determineLeftSegments(info, originalScale));
                for (int i = 0; i < originalScale; i++) {
                    listOfColumns.add(determineCenterSegments(info, originalScale));
                }
                listOfColumns.add(determineRightSegments(info, originalScale));
                
                
                
                numCount++;
                if(numCount<numbers.length){
                    // there has to be exactly one columns of blanks between two digits
                    int numberOfRows = (2*originalScale)+3;
                    listOfColumns.add(Collections.nCopies(numberOfRows, SPACE));
                }

            }

            outputs.add(listOfColumns);



        }
        int outputCount = 0;
        for (List<List<String>> listOfColumns : outputs) {
            StringBuilder row = new StringBuilder();
            int numberOfRows = listOfColumns.get(0).size();
            int numberOfColumns = listOfColumns.size();
            int rowIndex = 0;
            while(rowIndex< numberOfRows){
                for (List<String> columnOfChars : listOfColumns) {
                    // construct a string using the strings in each index
                    row.append(columnOfChars.get(rowIndex));
                }
                System.out.println(row);
                row.setLength(0);
                rowIndex++;
            }
           // Collections.nCopies(1, SPACE).forEach(System.out::print);// blank line between outputs
            System.out.println();
            
        }
    }

    private static List<String> determineRightSegments(Integer info, int scale) {
        List<String> rightSegments = new ArrayList<>();
        rightSegments.add(SPACE);
        if ((info & 0x02) == 0x02) {
            rightSegments.addAll(Collections.nCopies(scale,PIPE));
        }else{
            rightSegments.addAll(Collections.nCopies(scale,SPACE));
        }
        rightSegments.add(SPACE);
        if (((info & 0x01) == 0x01)) {
            rightSegments.addAll(Collections.nCopies(scale,PIPE));
        }else{
            rightSegments.addAll(Collections.nCopies(scale,SPACE));
        }
        rightSegments.add(SPACE);
        return rightSegments;
    }

    /**
     * 
     * @param info hex integer where 1's represent vertical segment
     * @param scale
     * @return
     */
    private static List<String> determineLeftSegments(Integer info, int scale) {
        // for a hex of 7 digits , to determine what the last two digits are, bitwise AND with 1000000 and 0100000
        // and check if result is 1000000 and 0100000 respectively. Based on that we know what the left segments are.
        List<String> leftSegments = new ArrayList<>();
        leftSegments.add(SPACE);
        if (((info & 0x20) == 0x20)) {
            leftSegments.addAll(Collections.nCopies(scale,PIPE));
        }else{
            leftSegments.addAll(Collections.nCopies(scale,SPACE));
        }
        leftSegments.add(SPACE);
        if ((info & 0x40) == 0x40) {
            leftSegments.addAll(Collections.nCopies(scale,PIPE));
        }else{
            leftSegments.addAll(Collections.nCopies(scale,SPACE));
        }
        leftSegments.add(SPACE);
        return leftSegments;
    }

    /**
     *
     * @param info hex integer where 1's represent horizontal segment
     * @param scale
     * @return
     */
    private static List<String> determineCenterSegments(Integer info, int scale) {
        // for a hex of 7 digits , to determine what the center three digits are, bitwise AND with 0010000,
        // 0001000, 0000100 respectively. Based on that we know what the center segments 
        // are.
        List<String> centerSegments = new ArrayList<>();
        
        if ((info & 0x10) == 0x10) {
            centerSegments.addAll(Collections.nCopies(1, DASH));
        }else{
            centerSegments.addAll(Collections.nCopies(1,SPACE));
        }
        centerSegments.addAll(Collections.nCopies(scale,SPACE));
        if (((info & 0x08) == 0x08)) {
            centerSegments.addAll(Collections.nCopies(1,DASH));
        }else{
            centerSegments.addAll(Collections.nCopies(1,SPACE));
        }
        centerSegments.addAll(Collections.nCopies(scale,SPACE));
        if (((info & 0x04) == 0x04)) {
            centerSegments.addAll(Collections.nCopies(1,DASH));
        }else{
            centerSegments.addAll(Collections.nCopies(1,SPACE));
        }
        return centerSegments;
    }

}
/* @END_OF_SOURCE_CODE */


