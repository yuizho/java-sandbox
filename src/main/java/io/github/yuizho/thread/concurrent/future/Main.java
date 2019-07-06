package io.github.yuizho.thread.concurrent.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String... args) {
        final int concurrency = 5;
        final ExecutorService executor
                = Executors.newFixedThreadPool(concurrency);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < concurrency; i++) {
            final int index = i;
            System.out.println(String.format("prepare %d回目", i));
            Future<String> future
                    = executor.submit(() -> String.format("%d回目", index));
            futures.add(future);
        }

        futures.forEach(
                elm -> {
                    try {
                        System.out.println(elm.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );

        executor.shutdown();
    }
}
