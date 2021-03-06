package io.github.yuizho.stream.reduction;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) {
        List<Product> products = List.of(
                new Product(1,  100),
                new Product(2,  200),
                new Product(2, 200),
                new Product(3, 300),
                new Product(3, 300),
                new Product(3, 300)
        );

        // 以下でProductのtypeごとにProductの金額を合算したMapを作る (key = type, value = Product obj)

        // reduce(3)
        // reduce(3)はcollect(3)と異なり、accumulatorとcombinerで結果をreturnする必要がある
        // https://docs.oracle.com/javase/jp/8/docs/api/java/util/stream/package-summary.html#Reduction
        var resultOfReduction =
                products.stream()
                .reduce(
                        // var使うとココで型を明示的に示さないとだめ
                        new HashMap<Integer, Product>(),
                        (acc, elm) -> {
                            var product = acc.getOrDefault(elm.getType(),
                                    new Product(elm.getType(), 0));
                            product.setValue(product.getValue() + elm.getValue());
                            acc.put(elm.getType(), product);
                            return acc;
                        },
                        (acc1, acc2) -> {
                            acc1.putAll(acc2);
                            return acc1;
                        }
                );
        System.out.println(resultOfReduction);

        // collect(3)
        // collect(3)はreduceとことなり、accumulatorとcombinerで結果をreturnしない
        // それぞれのaccumulatorの状態を変えていくことで、畳み込んでいく感じ
        // https://docs.oracle.com/javase/jp/8/docs/api/java/util/stream/package-summary.html#MutableReduction
        var resultOfMutableReduction =
                products.stream()
                        .collect(
                                // var使うとココで型を明示的に示さないとだめ
                                () -> new HashMap<Integer, Product>(),
                                (acc, elm) -> {
                                    var product = acc.getOrDefault(elm.getType(),
                                            new Product(elm.getType(), 0));
                                    product.setValue(product.getValue() + elm.getValue());
                                    acc.put(elm.getType(), product);
                                },
                                (acc1, acc2) -> acc1.putAll(acc2)
                        );
        System.out.println(resultOfMutableReduction);

        // 上記の操作をそれぞれ並列でやってみる
        var resultOfReductionWithParallel =
                products.parallelStream()
                        .reduce(
                                // var使うとココで型を明示的に示さないとだめ
                                new HashMap<Integer, Product>(),
                                (acc, elm) -> {
                                    var product = acc.getOrDefault(elm.getType(),
                                            new Product(elm.getType(), 0));
                                    product.setValue(product.getValue() + elm.getValue());
                                    acc.put(elm.getType(), product);
                                    return acc;
                                },
                                (acc1, acc2) -> {
                                    acc1.putAll(acc2);
                                    return acc1;
                                }
                        );
        System.out.println(resultOfReductionWithParallel);

        // collect(3)は意図通り動かない
        var resultOfMutableReductionWithParallel =
                products.parallelStream()
                        .collect(
                                // var使うとココで型を明示的に示さないとだめ
                                // ConcurrentHashMapとかにしてみてもうまくいかなかった
                                () -> new HashMap<Integer, Product>(),
                                (acc, elm) -> {
                                    var product = acc.getOrDefault(elm.getType(),
                                            new Product(elm.getType(), 0));
                                    product.setValue(product.getValue() + elm.getValue());
                                    acc.put(elm.getType(), product);
                                },
                                (acc1, acc2) -> acc1.putAll(acc2)
                        );
        System.out.println(resultOfMutableReductionWithParallel);

        // reduce以外でやるとしたら

        // grouping + reductingでも出来なくもない
        var resultsOfGroupingAndReducting =
                products
                        .stream()
                        .collect(Collectors.groupingBy(
                                product -> product.getType(),
                                Collectors.reducing(
                                        // 合算対象以外のものがあると、こんな感じでそれの初期値も指定してやらないといけない。
                                        // reducing(3)もidentityの方で指定したものに、計算結果が入っていく感じなので今回のようなケースでうまく使えない。
                                        new Product(0, 0),
                                        (acc1, acc2) -> {
                                            acc2.setValue(acc1.getValue() + acc2.getValue());
                                            return acc2;
                                        }
                                )
                        ));
        System.out.println(resultsOfGroupingAndReducting);
    }
}
