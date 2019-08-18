package collections.stream_sort_performance;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A comparison of methods for sorting an ArrayList.
 * <p>
 * Best performance for very large sets (10,000,000): Parallel Stream.
 * Best performance for moderate sets (100,000): Collections.sort() or list.sort(Comparator).
 *
 * Notes:
 * The non-parallel stream is never recommended.
 *
 * @author Christian Wesseler
 */
public class StreamSort {
    /**
     * Configures and generates varying numbers of randomly generated integers.
     */
    @State(Scope.Benchmark)
    public static class Data {
        @Param({"100000", "10000000"})
        private int numElements;
        List<Integer> arrayList = new ArrayList<>(numElements);
        List<Integer> toSort;

        /**
         * Generates a list of random numbers.
         */
        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < numElements; i++) {
                arrayList.add(RandomUtils.nextInt());
            }
        }

        /**
         * Copies the unsorted array every iteration.
         */
        @Setup(Level.Iteration)
        public void copyList() {
            toSort = new ArrayList<>(arrayList);
        }

    }
    /**
     * Sorts the array by creating a stream.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void streamSort(Data data, Blackhole blackhole) {
        blackhole.consume(data.toSort.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
    }

    /**
     * Sorts the array by creating a parallel stream.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void parallelStreamSort(Data data, Blackhole blackhole) {
        blackhole.consume(data.toSort.parallelStream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
    }

    /**
     * Sorts the array using the default sort with comparator.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void defaultSortComparator(Data data, Blackhole blackhole) {
        data.toSort.sort(Comparator.naturalOrder());
    }

    /**
     * Sorts the array using the default sort from the collections api.
     *
     * @param data The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void defaultSortCollections(Data data, Blackhole blackhole) {
        Collections.sort(data.toSort);
    }
}
