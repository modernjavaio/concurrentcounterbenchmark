package io.modernjava.concurrentcounterbenchmark;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderCounter<K> implements Counter<K> {

    private Map<K, LongAdder> counters = new ConcurrentHashMap<>();

    public int up(K key) {
        counters.computeIfAbsent(key, k -> new LongAdder()).increment();
        return 0;
    }

    public int getCount(K key) {
        return (int)counters.getOrDefault(key, new LongAdder()).sum();
    }
}
