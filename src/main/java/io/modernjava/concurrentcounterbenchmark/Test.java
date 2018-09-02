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
    public static final int COUNT = 1000000;

    public static void main(String... argv) throws Exception {
        int threadCount = 2;
        testCounter(threadCount,  new OriginalCounter<>());
        testCounter(threadCount,  new InheritanceAtomicIntegerCounter<>());
        testCounter(threadCount,  new SynchronizedHashMapCounter<>());
        testCounter(threadCount,  new AtomicIntegerCounter<>());
        testCounter(threadCount,  new MapComputeCounter<>());
        testCounter(threadCount,  new LongAdderCounter<>());
    }

    private static void testCounter(int threadCount, Counter<String> map) throws InterruptedException, java.util.concurrent.ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);


        List<Future> futures = IntStream.range(0, threadCount).mapToObj(i -> pool.submit(() -> {
            for(int j = 0; j < COUNT; j++) {
                map.up("key");
            }
        })).collect(Collectors.toList());

        for(Future f :  futures) {
            f.get();
        }

        int result = map.getCount("key");

        if (result != threadCount * COUNT) {
            logger.error("Incorrect count from {}", map.getClass().getCanonicalName());
        }

        logger.info("result: {}", result);
    }
}
