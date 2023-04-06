package copying;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static copying.JacksonDeepClone.deepCloneJackson;
import static copying.SerialDeepClone.deepCloneSerial;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * A comparison of contains calls on different collection types.
 * <p>
 * Best performance: HashSet
 *
 * @author Christian Wesseler
 */
public class DeepCopy
{
    /**
     * Configures and generates varying numbers of randomly generated strings.
     */
    @State(Scope.Benchmark)
    public static class Data
    {

        private ComplexObject parent;

        /**
         * Generates a list of random strings.
         * HashSet functionality prevents duplicates, meaning sample may be less than numElements (but same size for all collections).
         */
        @Setup(Level.Trial)
        public void setUp()
        {
            parent = new ComplexObject("Parent", 40, new ArrayList<>(), null, new ArrayList<>());
            ComplexObject child1 = new ComplexObject("Child1", 10, new ArrayList<>(), parent, new ArrayList<>());
            ComplexObject child2 = new ComplexObject("Child2", 8, new ArrayList<>(), parent, new ArrayList<>());
            List<ComplexObject> children = new ArrayList<>();
            children.add(child1);
            children.add(child2);
            parent.setChildren(children);
            parent.getHobbies().add("Fishing");
            parent.getHobbies().add("Gardening");
            child1.getHobbies().add("Reading");
            child1.getHobbies().add("Drawing");
            child2.getHobbies().add("Playing soccer");
            child2.getHobbies().add("Watching TV");
            child1.setParent(parent);
            child2.setParent(parent);
        }
    }

    /**
     * @param data      The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void jacksonCopy(Data data, Blackhole blackhole) throws Exception
    {
        blackhole.consume(deepCloneJackson(data.parent));
    }

    /**
     * @param data      The provided state for the current benchmark run.
     * @param blackhole The method's consumer to prevent automatic optimization.
     */
    @Benchmark
    @Warmup(iterations = 5)
    @BenchmarkMode(Mode.Throughput)
    @SuppressWarnings("all")
    public static void serialCopy(Data data, Blackhole blackhole) throws IOException, ClassNotFoundException
    {
        blackhole.consume(deepCloneSerial(data.parent));
    }

}


