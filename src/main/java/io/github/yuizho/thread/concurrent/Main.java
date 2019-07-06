package io.github.yuizho.thread.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String... args) throws InterruptedException {
        final int concurrency = 5;
        final ExecutorService executor
                = Executors.newFixedThreadPool(concurrency);
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown();
                System.out.println("prepare task: " + Thread.currentThread().getId());
                try {
                    start.await();
                    System.out.println("do task: " + Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                    System.out.println("finalize task: " + Thread.currentThread().getId());
                }
            });
        }

        System.out.println("before starting tasks");

        System.out.println("before ready await");
        ready.await();

        System.out.println("before start countDown");
        start.countDown();

        System.out.println("before done await");
        done.await();

        System.out.println("all of tasks are done");
        executor.shutdown();
    }
}
