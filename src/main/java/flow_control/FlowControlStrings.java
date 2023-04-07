package flow_control;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static flow_control.StringConstants.concatStringConstants;
import static flow_control.StringConstants.getRandomWords;
import static flow_control.StringSwitch.evaluateSwitch;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;

/**
 * @author Christian Wesseler
 */
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 5)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FlowControlStrings
{
    /**
     * Configures and generates varying numbers of randomly generated strings.
     */
    @State(Scope.Benchmark)
    public static class Data {
        private List<String> randomWords;
        private final String matchString = concatStringConstants();
        private String toFind;
        private int iteration = 0;

        @Setup(Level.Trial)
        public void trialSetup(BenchmarkParams params)
        {
            randomWords  = getRandomWords(params.getMeasurement().getCount() + params.getWarmup().getCount());
        }

        /**
         * Generates a random word from the StringConstants list.
         */
        @Setup(Level.Iteration)
        public void setup() {
            toFind = randomWords.get(iteration);
            iteration++;
        }
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @SuppressWarnings("all")
    public static void switchFlow(Data data, Blackhole blackhole) {
        blackhole.consume(evaluateSwitch(data.toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @SuppressWarnings("all")
    public static void listContainsFlow(Data data, Blackhole blackhole) {
        blackhole.consume(Arrays.asList(StringConstants.WORD_LIST).contains(data.toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @SuppressWarnings("all")
    public static void setContainsFlow(Data data, Blackhole blackhole) {
        blackhole.consume(Set.of(StringConstants.WORD_LIST).contains(data.toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @SuppressWarnings("all")
    public static void matchesFlow(Data data, Blackhole blackhole) {
        blackhole.consume(data.toFind.matches(data.matchString));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @SuppressWarnings("all")
    public static void matchingAnyListFlow(Data data, Blackhole blackhole) {
        blackhole.consume(StringSwitch.anyList(data.toFind, "HVOW", "PJWW", "FHEX", "GSZY", "XTOR", "HVOX", "PJWX", "XHEX", "XSZY", "XTOX"));
    }
}
