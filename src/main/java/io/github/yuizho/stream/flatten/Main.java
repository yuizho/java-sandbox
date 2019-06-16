package io.github.yuizho.stream.flatten;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        List<Item> list1 =
                List.of(
                        new Item(1, 1000),
                        new Item(2, 2000),
                        new Item(3, 3000)
                );
        List<Item> list2 =
                List.of(
                        new Item(4, 4000),
                        new Item(5, 5000),
                        new Item(5, 6000)
                );

        // 以下のようにやれば、リストのなかの各リストをフラットにして処理できる
        var totalValueOfItems = Stream.of(list1, list2)
                .flatMap(items -> items.stream())
                .mapToInt(item -> item.getValue())
                .sum();
        System.out.println(totalValueOfItems);

        // builderで要素を追加していくときは、一回builderを明示的に型をしていした変数で受けないと
        // buildしたときの戻り値の型が、Stream<Object>にってしまう。
        Stream.Builder<List<Item>> itemsStreamBuuilder = Stream.builder();
        var totalValueOfItemsWithBuilder =
                itemsStreamBuuilder
                        .add(list1)
                        .add(list2)
                        .build()
                        .flatMap(items -> items.stream())
                        .mapToInt(item -> item.getValue())
                        .sum();
        System.out.println(totalValueOfItemsWithBuilder);

        // リストが2つならこれでもよさそう
        var totalValueOfItemsWithConcat = Stream.concat(list1.stream(), list2.stream())
                .mapToInt(item -> item.getValue())
                .sum();
        System.out.println(totalValueOfItemsWithConcat);
    }

    @Getter
    @ToString
    static class Item {
        private final int id;
        private final int value;
        public Item(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }
}
