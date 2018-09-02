package io.modernjava.concurrentcounterbenchmark;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapAtomicIntegerCounter<K> implements Counter<K> {

    private Map<K, AtomicInteger> counters = new HashMap<>();

    @Override
    public int up(K key) {

        return counters.computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet();
    }

    @Override
    public int getCount(K key) {
        return counters.getOrDefault(key, new AtomicInteger()).get();
    }

}
