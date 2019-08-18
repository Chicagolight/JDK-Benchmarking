package strings.string_concat_performance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * A comparison of methods for concatenating strings.
 * <p>
 * Best performance: StringBuilder.
 *
 * Notes:
 * Providing an initial size improves performance with a sufficient number of concatenations.
 * String.format is excluded in these tests, but performs worse than all provided options. Use sparingly.
 *
 * @author Christian Wesseler
 */
public class StringConcat {
    private static final int STRING_SIZE = 3;
    
    /**
     * Configures and generates varying numbers of randomly generated strings.
     */
    @State(Scope.Benchmark)
    public static class Data {
        @Param({"1", "10", "100", "1000"})
        private int numElements;
        
        List<String> dataList = new ArrayList<>(numElements);
        
        /**
         * Generates a list of random strings.
         */
        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < numElements; i++) {
                dataList.add(RandomStringUtils.random(STRING_SIZE));
            }
        }
    }
    
    /**
     * Tests string concatanation using StringBuilder.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void stringBuilderAppend(Data data, Blackhole blackhole) {
        StringBuilder testString = new StringBuilder();
        
        // Generate final string from list.
        for (String entry : data.dataList) {
            testString.append(entry);
        }
        
        // Final state must be a usable string.
        blackhole.consume(testString.toString());
    }
    
    /**
     * Tests string concatanation using StringBuilder and a provided capacity.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void stringBuilderAppendWithSize(Data data, Blackhole blackhole) {
        StringBuilder testString = new StringBuilder(STRING_SIZE * data.numElements);
        
        // Generate final string from list.
        for (String entry : data.dataList) {
            testString.append(entry);
        }
        
        // Final state must be a usable string.
        blackhole.consume(testString.toString());
    }
    
    /**
     * Tests default string concatanation.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void stringConcat(Data data, Blackhole blackhole) {
        String testString = "";
        
        for (String entry : data.dataList) {
            testString += entry;
        }

        blackhole.consume(testString);
    }
}
