package io.github.yuizho.thread.concurrent.synchronizer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchSample {
    public static void main(String... args) throws InterruptedException {
        final int concurrency = 5;
        final ExecutorService executor
                = Executors.newFixedThreadPool(concurrency);
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                // 初期値から-1していく (awaitするとこれが0になるまで待機する)
                ready.countDown();
                System.out.println("prepare task: " + Thread.currentThread().getId());
                try {
                    // start#countDownが実行されるまでここで待機
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
        // 0になるまで待機する
        ready.await();

        System.out.println("before start countDown");
        // すべてのスレットが準備OK状態でここに来る
        // startのcount値は1なので、このcountDownがトリガーになり、
        // 全スレッドが処理を開始する。
        start.countDown();

        System.out.println("before done await");
        // 各処理が完了するのをここで待ち受ける
        done.await();

        System.out.println("all of tasks are done");
        executor.shutdown();
    }
}
