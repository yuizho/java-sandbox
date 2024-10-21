package io.github.yuizho.concurrencypractice;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.*;

import static java.time.temporal.ChronoField.*;

public class ResultCache {
    public static void main(String... args) {
        int concurrency = 10;
        try (ExecutorService executor = Executors.newFixedThreadPool(concurrency);) {
            Memolizer memolizer = new Memolizer();
            for (int i = 1; i <= concurrency; i++) {
                executor.execute(() -> {
                    try {
                        System.out.printf("before compute on %d%n", Thread.currentThread().threadId());
                        System.out.printf(
                                memolizer.compute(Instant.now().get(MILLI_OF_SECOND)) +
                                        " on " + Thread.currentThread().threadId() + "%n"
                        );
                        System.out.printf("after compute on %d%n", Thread.currentThread().threadId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}

class Memolizer {
    // ここをConcurrentHashMap<Integer, Integer>にすると、複数のスレッドから同時にアクセスされるため、
    //偽chache missが増えすぎる
    private final Map<Integer, Future<Integer>> cache = new ConcurrentHashMap<>();

    // ここをsynchronizedするとおそすぎる
    public synchronized Integer compute(Integer arg) throws InterruptedException {
        System.out.println("key: " + arg +  ", compute on " + Thread.currentThread().threadId());
        Future<Integer> chachedFutureTask = cache.get(arg);
        if (chachedFutureTask == null) {
            System.out.println("cache miss on " + Thread.currentThread().threadId());
            FutureTask<Integer> futureTask = new FutureTask<>(() -> {
                // heavy calculation
                Thread.sleep(1000);
                return arg % 2;
            });
            cache.putIfAbsent(arg, futureTask);
            chachedFutureTask = futureTask;
            futureTask.run();
        } else {
            System.out.println("cache hit on " + Thread.currentThread().threadId());
        }
        try {
            return chachedFutureTask.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
