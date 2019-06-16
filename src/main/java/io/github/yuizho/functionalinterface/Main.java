package io.github.yuizho.functionalinterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class Main {
    public static void main(String... args) {
        // 自分で定義した関数インターフェースを実装
        MyFunctionalInterface mfi = (n) -> n * 2;
        System.out.println(mfi.apply(1));

        // 値を供給するための関数インターフェース
        Supplier supplier = () -> 0;
        System.out.println(supplier.get());

        // 大体各関数インタフェースでプリミティブ型用のものが用意されている
        // Boxingのコスト考慮して、int,long,doubleはこれを使うべき
        LongSupplier longSupplier = () -> 1L;
        System.out.println(longSupplier.getAsLong());

        // 引数を消費(処理)するための関数インターフェース
        // 引数の状態を変える感じで使うっぽい
        StringBuilder sb = new StringBuilder();
        Consumer<StringBuilder> consumer = (s) -> s.append("consumed");
        consumer.accept(sb);
        System.out.println(sb.toString());

        // 引数の判定を行いbooleanを返す
        Predicate<String> predicate = (s) -> s.equals("expected");
        System.out.println(predicate.test("expected"));

        // 引数を受け取り、変換して別の値を返す
        // 引数の型と戻り値の型が異なる場合に使う
        Function<String, List<String>> function =
                (s) -> Arrays.asList(s.split(""));
        System.out.println(function.apply("aaa"));

        // 引数を受け取り、変換して別の値を返す
        // 引数の型と戻り値の型が同じ場合に使う
        UnaryOperator<String> unaryOperator =
                (s) -> s + s;
        System.out.println(unaryOperator.apply("test"));

        // 2つ引数を受け取り、変換して別の値を返す
        // 引数の型と戻り値の型が同じ場合に使うが、引数を2つとる
        // reduceとかで使われている
        BinaryOperator<String> binaryOperator =
                (s1, s2) -> s1 + s2;
        System.out.println(binaryOperator.apply("a", "b"));

        // 引数を2つとるConsumer
        // Consumerと同様基本的に副作用を介して動作する
        // collect(3)の第2, 3引数はこの関数インターフェースが使われてる
        StringBuilder stringBuilder = new StringBuilder();
        BiConsumer<StringBuilder, String> biConsumer =
                (sb1, s) -> sb1.append(s);
        biConsumer.accept(stringBuilder, "appended");
        System.out.println(stringBuilder.toString());

        // 引数を2つ取って、違う型へ変換して返す
        // Functionの亜種という感じ
        // reduce(3)の第2引数はこの関数インターフェースが使われている
        BiFunction<Integer, Long, String> biFunction =
                (i1, i2) -> String.valueOf(i1 + i2);
        System.out.println(biFunction.apply(1, 2L));
    }
}
