package io.modernjava.concurrentcounterbenchmark;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapComputeCounter<K> implements Counter<K> {

    private final Map<K, MutableInteger> counters = new ConcurrentHashMap<>();

    @Override
    public int up(K key) {
        return counters.compute(
                key,
                (k, mutableInt) -> mutableInt == null ? new MutableInteger(1) : mutableInt.set(mutableInt.get() + 1)
        ).get();
    }

    @Override
    public int getCount(K key) {
        return counters.get(key).get();
    }

    public class MutableInteger {
    private int val;

    public MutableInteger(int val) {
        this.val = val;
    }

    public int get() {
        return val;

    }

    public MutableInteger set(int val) {
        this.val = val;
        return this;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}

}
