package chapter1.problem4.functions;
// @JUDGE_ID:  1272379  706  Java  "bit vector"
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.util.Collections.nCopies;
import static java.util.stream.Stream.of;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import java.util.function.Function;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String INPUT_END = "0 0";
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final byte[] pattern = new byte[] {
            0x77, 0x03, 0x5d, 0x1f, 0x2b, 0x3e, 0x7e, 0x07, 0x7f, 0x3f
    };
    
    /**
     * this method will call the method called segments which takes two arguments, the digits string and scale. It will
     * return a list of strings which we can simply output to the console.
     */
    public static void main(String[] args) throws IOException {
        String currentLine = INPUT_END;
        while ((currentLine = reader.readLine()) != null && !currentLine.trim().equalsIgnoreCase(INPUT_END)) {
            List<String> input = Arrays.stream(currentLine.trim().split(SPACE))
                    .filter(x -> !x.equals("")).collect(Collectors.toList());
            segments(input.get(1), Integer.valueOf(input.get(0))).stream()
                    .forEach(System.out::println);
            System.out.println();
        }
    }

    private static List<String> segments(final String digits, final int scale) {
        // Helpers
        Function<String, String> segmentScaler = x -> join(EMPTY, nCopies(scale, x));
        BiFunction<Stream<Integer>, Byte, Stream<String>> bitToSegmentConverter =
                (maskStream, pattern) -> maskStream.map(mask -> (pattern & mask) > 0 ? "|" : SPACE); // only puts 
        // spaces where the segments otherwise should be
        BiFunction<Stream<Integer>, Byte, String> columnConstructor =
                (maskStream, bitPattern) -> SPACE + bitToSegmentConverter.apply(maskStream, bitPattern)
                        .map(segment -> segmentScaler.apply(segment)).collect(Collectors.joining(SPACE))+ SPACE;  
        // (above) joins segments by spaces using Collectors.joining(SPACE)
        
        final int digitHeight = scale * 2 + 3;
        Function<Byte, Stream<String>> digitPatternToLCDColumns = digitPattern -> Arrays.asList(
                of(columnConstructor.apply(of(0x40, 0x20), digitPattern)),
                        nCopies(scale, bitToSegmentConverter.apply(of(0x10, 0x08, 0x04), digitPattern)
                                        .collect(Collectors.joining(segmentScaler.apply(SPACE)))
                                        .replace('|', '-')).stream()
                                ,
                of(columnConstructor.apply(of(0x20, 0x01), digitPattern)),
                of(join(EMPTY, nCopies(digitHeight, SPACE))))
                        .stream().reduce(Stream::concat).get();
        
        // Process
        List<String> segments = digits.chars().map(digitPattern -> digitPattern - '0').boxed()
                .flatMap(digitPattern -> digitPatternToLCDColumns.apply(pattern[digitPattern]))
                .collect(Collectors.toList());
        // Return
        return rangeClosed(1, digitHeight).boxed()
                .map(j -> digitHeight - j)
                .map(j -> range(0, segments.size() -1).boxed()
                        .map(i->Character.toString(segments.get(i).charAt(j)))
                        .collect(joining())).collect(Collectors.toList());
    }

}
