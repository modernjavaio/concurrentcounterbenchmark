package io.modernjava.concurrentcounterbenchmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static final int ITERATIONS = 1_000_000;
    private static final int THREAD_COUNT = 8;

    public static void main(String... argv) throws Exception {
        testCounter(new OriginalCounter<>());
        testCounter(new InheritanceAtomicIntegerCounter<>());
        testCounter(new SynchronizedHashMapCounter<>());
        testCounter(new AtomicIntegerCounter<>());
        testCounter(new MapComputeCounter<>());
        testCounter(new LongAdderCounter<>());
    }

    private static void testCounter(Counter<String> map) throws InterruptedException, java.util.concurrent.ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);


        List<Future> futures = IntStream.range(0, THREAD_COUNT).mapToObj(i -> pool.submit(() -> {
            for(int j = 0; j < ITERATIONS; j++) {
                map.up("key");
            }
        })).collect(Collectors.toList());

        for(Future f :  futures) {
            f.get();
        }

        int result = map.getCount("key");

        if (result != THREAD_COUNT * ITERATIONS) {
            logger.error("Incorrect count from {}", map.getClass().getCanonicalName());
        }

        logger.info("result: {}", result);

        pool.shutdown();
    }
}
