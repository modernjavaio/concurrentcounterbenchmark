package io.modernjava.concurrentcounterbenchmark;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InheritanceAtomicIntegerCounter<K>  extends ConcurrentHashMap<K, AtomicInteger> implements Counter<K> {

    @Override
    public int up(K key) {
        return computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet();
    }

    @Override
    public int getCount(K key) {
        return getOrDefault(key, new AtomicInteger()).get();
    }

}
