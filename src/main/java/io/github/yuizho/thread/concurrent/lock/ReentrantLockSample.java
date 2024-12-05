package io.github.yuizho.thread.concurrent.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSample {
    public static void main(String[] args) {
        int concurrency = 5;
        final CyclicBarrier barrier = new CyclicBarrier(concurrency);
        Counter counter = new Counter();

        try (ExecutorService executor = Executors.newFixedThreadPool(concurrency);) {
            for (int i = 0; i < concurrency; i++) {
                executor.execute(() -> {
                    try {
                        // 全部のスレッドが準備OKになるまで待機
                        barrier.await();
                        System.out.println("準備OK threadId: " + Thread.currentThread().threadId());

                        // 順々にインクリメント
                        for (int j = 0; j < 5; j++) {
                            counter.increment();
                        }
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }
    }
}

class Counter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
            System.out.println("increment: " + count + " on " + Thread.currentThread().threadId());
        } finally {
            lock.unlock();
        }
    }
}