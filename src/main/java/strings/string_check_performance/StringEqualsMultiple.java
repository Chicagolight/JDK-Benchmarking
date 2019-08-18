package strings.string_check_performance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A comparison of methods for using string comparison as a switch (ie if inputString is either A || B || C).
 * <p>
 * Best performance: inputString.equals
 *
 * @author Christian Wesseler
 */
public class StringEqualsMultiple {
    /**
     * Defines and instantiates the string to check for, and a precompiled pattern.
     */
    @State(Scope.Benchmark)
    public static class Data {
        String stringInput;
        Pattern pattern;

        /**
         * Sets the string to evaluate, and compiles a regex pattern.
         */
        @Setup(Level.Trial)
        public void setUp() {
            stringInput = "Path1";
            pattern = Pattern.compile("Path1|Path2|Path3");
        }
    }

    /**
     * Checks the string using regex with runtime compilation.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void matchesRuntimeCompile(Data data, Blackhole blackhole) {
        blackhole.consume(data.stringInput.matches("Path1|Path2|Path3"));
    }

    /**
     * Checks the string with precompiled regex.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void matchesPrecompiled(Data data, Blackhole blackhole) {
        blackhole.consume(data.pattern.matcher(data.stringInput).matches());
    }

    /**
     * Checks the string utilizing contains against a list.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void stringArrayContains(Data data, Blackhole blackhole) {
        blackhole.consume(Arrays.asList("Path1", "Path2", "Path3").contains(data.stringInput));
    }

    /**
     * Checks the string utilizing streams and anyMatch.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void stringStreamMatch(Data data, Blackhole blackhole) {
        blackhole.consume(Stream.of("Path1", "Path2", "Path3").anyMatch(data.stringInput::equals));
    }

    /**
     * Checks the string using default equals method.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void stringEquals(Data data, Blackhole blackhole) {
        blackhole.consume(data.stringInput.equals("Path1") || data.stringInput.equals("Path2") || data.stringInput.equals("Path3"));
    }
}
