package io.github.yuizho.thread.concurrent.synchronizer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreSample {
    public static void main(String... args) {
        final int concurrency = 5;
        final int permits = 2;
        final Semaphore semaphore = new Semaphore(permits);
        final ExecutorService executor = Executors.newFixedThreadPool(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                try {
                    System.out.println("Semaphore#aquire: " + Thread.currentThread().threadId());
                    semaphore.acquire();
                    System.out.println("start task: " + Thread.currentThread().threadId());
                    Thread.sleep(1000);
                    System.out.println("end task: " + Thread.currentThread().threadId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release();
                    System.out.println("Semaphore#release: " + Thread.currentThread().threadId());
                }
            });
        }

        executor.shutdown();
    }
}
