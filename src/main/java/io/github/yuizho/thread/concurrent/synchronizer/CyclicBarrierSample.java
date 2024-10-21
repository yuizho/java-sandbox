package io.github.yuizho.thread.concurrent.synchronizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierSample {
    public static void main(String... args) {
        // 5つのスレッドが同時に処理を開始する
        final int concurrency = 5;
        final CyclicBarrier barrier = new CyclicBarrier(concurrency);
        final ExecutorService executor = Executors.newFixedThreadPool(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                System.out.println("prepare task: " + Thread.currentThread().getId());
                try {
                    Thread.sleep(1000);
                    // すべてのスレッドがここで待機する
                    barrier.await();
                    System.out.println("do task: " + Thread.currentThread().getId());
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
