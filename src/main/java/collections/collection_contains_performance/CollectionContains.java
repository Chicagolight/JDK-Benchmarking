package collections.collection_contains_performance;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;

/**
 * A comparison of contains calls on different collection types.
 * <p>
 * Best performance: HashSet
 *
 * @author Christian Wesseler
 */
public class CollectionContains {
    private static final int STRING_SIZE = 5;
    private static final String toFind = "FINDME";

    /**
     * Configures and generates varying numbers of randomly generated strings.
     */
    @State(Scope.Benchmark)
    public static class Data {
        @Param({"10", "100", "1000"})
        private int numElements;

        List<String> arrayList = new ArrayList<>(numElements);
        List<String> linkedList = new LinkedList<>();
        List<String> vector = new Vector<>(numElements);
        Set<String> hashSet = new HashSet<>(numElements);
        Set<String> linkedHashSet = new LinkedHashSet<>(numElements);
        Set<String> treeSet = new TreeSet<>();

        /**
         * Generates a list of random strings.
         * HashSet functionality prevents duplicates, meaning sample may be less than numElements (but same size for all collections).
         */
        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < numElements; i++) {
                hashSet.add(RandomStringUtils.random(STRING_SIZE));
            }

            hashSet.add(toFind);

            arrayList.addAll(hashSet);
            linkedList.addAll(hashSet);
            vector.addAll(hashSet);
            linkedHashSet.addAll(hashSet);
            treeSet.addAll(hashSet);
        }
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void arrayListContains(Data data, Blackhole blackhole) {
        blackhole.consume(data.arrayList.contains(toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void LinkedListContains(Data data, Blackhole blackhole) {
        blackhole.consume(data.linkedList.contains(toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void VectorContains(Data data, Blackhole blackhole) {
        blackhole.consume(data.vector.contains(toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void HashSetContains(Data data, Blackhole blackhole) {
        blackhole.consume(data.hashSet.contains(toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void LinkedHashSetContains(Data data, Blackhole blackhole) {
        blackhole.consume(data.linkedHashSet.contains(toFind));
    }

    /**
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void TreeSetContains(Data data, Blackhole blackhole) {
        blackhole.consume(data.treeSet.contains(toFind));
    }
}
