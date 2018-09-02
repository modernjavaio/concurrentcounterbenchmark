package io.modernjava.concurrentcounterbenchmark;

import java.util.concurrent.ConcurrentHashMap;

public class OriginalCounter<K> extends ConcurrentHashMap<K, OriginalCounter.MutableInteger> implements Counter<K>  {

    @Override
    public int up(K key) {
        MutableInteger initValue = new MutableInteger(1);
        MutableInteger oldValue = super.put(key, initValue);

        if(oldValue != null) {
            initValue.set(oldValue.get() + 1);
        }

        return initValue.get();
    }

    @Override
    public int getCount(K key) {
        return get(key).get();
    }

    public static class MutableInteger {
        private int val;

        public MutableInteger() {
            this(0);
        }

        MutableInteger(int val) {
            this.val = val;
        }


        public int get() {
            return val;
        }

        void set(int val) {
            this.val = val;
        }
    }
}
