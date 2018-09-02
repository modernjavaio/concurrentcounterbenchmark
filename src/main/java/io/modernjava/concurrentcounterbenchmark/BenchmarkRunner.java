package io.modernjava.concurrentcounterbenchmark;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@Measurement(batchSize = 10_000)
public class BenchmarkRunner {

    private static Counter<String> original = new OriginalCounter<>();
    private static Counter<String> composition = new InheritanceAtomicIntegerCounter<>();
    private static Counter<String> synchronizedCounter = new SynchronizedHashMapCounter<>();
    private static Counter<String> atomicInteger = new AtomicIntegerCounter<>();
    private static Counter<String> mapAtomicInteger = new MapAtomicIntegerCounter<>();
    private static Counter<String> mapCompute = new MapComputeCounter<>();
    private static Counter<String> longAdder = new LongAdderCounter<>();

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public static int testOriginal() {
        return original.up("key");
    }
    @Benchmark
    public static int testInheritance() {
        return composition.up("key");
    }
    @Benchmark
    public static int testSynchonized() {
        return synchronizedCounter.up("key");
    }
    @Benchmark
    public static int testAtomicInteger() {
        return atomicInteger.up("key");
    }
    @Benchmark
    public static int testMapAtomicInteger() {
        return mapAtomicInteger.up("key");
    }
    @Benchmark
    public static int testMapCompute() {
        return mapCompute.up("key");
    }
    @Benchmark
    public static int testLongAdder() {
        return longAdder.up("key");
    }
}
