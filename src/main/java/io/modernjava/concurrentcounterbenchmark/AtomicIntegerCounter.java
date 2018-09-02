package io.modernjava.concurrentcounterbenchmark;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter<K> implements Counter<K> {

    private Map<K, AtomicInteger> counters = new ConcurrentHashMap<>();

    @Override
    public int up(K key) {

        return counters.computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet();
    }

    @Override
    public int getCount(K key) {
        return counters.getOrDefault(key, new AtomicInteger()).get();
    }

}
