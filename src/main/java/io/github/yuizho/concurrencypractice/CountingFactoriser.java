package io.github.yuizho.concurrencypractice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class CountingFactoriser {
    private final AtomicLong count = new AtomicLong(0);

    public void increment() {
        count.getAndIncrement();
    }

    public long getCount() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final int concurrency = 10;
        final CountDownLatch done = new CountDownLatch(concurrency);

        try (ExecutorService executor = Executors.newFixedThreadPool(concurrency);) {
            CountingFactoriser factoriser = new CountingFactoriser();
            for (int i = 0; i < concurrency; i++) {
                executor.execute(() -> {
                    System.out.println("before increment: " + factoriser.getCount());
                    factoriser.increment();
                    System.out.println("after increment: " + factoriser.getCount());
                    done.countDown();
                });
            }

            done.await();
            System.out.println("-------------------");
            System.out.println(factoriser.getCount());
        }
    }
}
