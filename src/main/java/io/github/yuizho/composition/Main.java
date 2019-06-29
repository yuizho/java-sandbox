package io.github.yuizho.composition;

import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String... args) {
        ExtendedSet<String> extendedSet = new ExtendedSet<>();
        extendedSet.addAll(List.of("a", "b", "c"));
        // ここは6になってしまう。
        // ExtendedSet#addAll内でHashSet#addAllを読んでいるが
        // HashSet#addAllは内部でaddをよんでいる
        // このadd呼び出しの際、ExtendedSet#addが呼ばれることになるので、
        // 結果は6になってしまう。
        // HashSetの実装に、悪い意味で依存してしまう例
        System.out.println(extendedSet.getAddedCount());

        CompositionSetWrapper<String> compositionSet
                = new CompositionSetWrapper<>(new HashSet<>());
        compositionSet.addAll(List.of("a", "b", "c"));
        // CompositionSetWrapperでもExtendedSetとほぼ同様の実装をしているが、
        // 継承もとのCompositionSetはフィールドのSetをしようしている。
        // このため、意図せず親クラスからサブクラスの実装を呼び出してしまうことがなく、
        // 処理を実行できる。
        System.out.println(compositionSet.getAddedCount());
    }
}
