# Concurrent Counter Benchmark

This repository includes a basic test runner for the various counter implementations. 
This doesn't use JUnit, because I didn't want to complicate things, and 
we know that the original implementation is not thread-safe, so would 
break the build.

There's also a JMH-based benchmark runner, and a script to run it with
various number of threads.

There is a simple `Counter` interface to make sure each has the same API.

The implementations are:

* `OriginalCounter`: The code as presented in the original article
* `MapComputeCounter`: This uses `Map.compute()` with `MutableInteger`
* `AtomicIntegerCounter`: This uses `AtomicInteger` in a `ConcurrentHashMap`
* `LongAdderCounter`: This uses `LongAdder` inside a `ConcurrentHashMap`. This 
        version returns 0 from `up()`, as any real result would be expensive to compute.
* `InteritanceAtomicIntegerCounter`: Modified `AtomicIntegerCounter` to use
        inheritance instead of composition to see if there's any perform
        differences.
* `SynchronizedHashMapCounter`: Modified `AtomicIntegerCounter` to use 
        `Collections.synchronizedMap()` wrapping a `HashMap` to see the differences
        in performance with a ConcurrentHashMap.
    
## Running tests    
The simplest way to run the tests is to run

    # mvn clean package
    # ./run-all.sh
    
It takes roughly two hours to run on a four-core AMD 2200G processor. You can 
pass the -q flag to `run-all.sh` to run one warm up and one measured run for 
testing purposes.

Alternatively, you can run the command directly with

    # java -jar target/benchmark.jar
    
Use the `-h` argument to find out other options.      