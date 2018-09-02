package io.modernjava.concurrentcounterbenchmark;

public interface Counter<K> {
    int up(K key);

    int getCount(K key);
}
