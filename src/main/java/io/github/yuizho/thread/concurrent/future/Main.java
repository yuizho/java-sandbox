package io.github.yuizho.thread.concurrent.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String... args) {
        final int concurrency = 5;
        final ExecutorService executor
                = Executors.newFixedThreadPool(concurrency);

        final int tasks = 10;
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < tasks; i++) {
            final int index = i;
            System.out.println(String.format("prepare %d回目", i));
            Future<String> future
                    // タスクを実行して、結果をFutureにくるんで返す
                    = executor.submit(
                            () -> String.format("%d回目 on Thread No.%d",
                                    index,
                                    Thread.currentThread().getId()
                            )
            );
            futures.add(future);
        }

        futures.forEach(
                elm -> {
                    try {
                        // 必要に応じて計算が完了するまで待機してタスクの戻り値を取得
                        System.out.println(elm.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );

        executor.shutdown();
    }
}
