package io.github.yuizho.generics.commoncontainer;

import java.util.*;

public class Main {
    public static void main(String... args) {
        Favorites favorites = new Favorites();
        favorites.putFavorite(String.class, "aaa");
        favorites.putFavorite(Integer.class, 1);
        System.out.println(favorites);
        System.out.println(favorites.getFavorite(String.class));
    }

    public static class Favorites {
        private final Map<Class<?>, Object> favoriteByClass = new HashMap<>();

        public <T> void putFavorite(Class<T> type, T instance) {
            favoriteByClass.put(
                    Objects.requireNonNull(type),
                    // 仮にkeyと異なる方のオブジェクトが入ってきた場合に
                    // ClassCastExceptionで実行時エラーにできる
                    type.cast(instance));
        }

        public <T> Optional<T> getFavorite(Class<T> type) {
            return Optional.ofNullable(
                    type.cast(favoriteByClass.get(type))
            );
        }

        @Override
        public String toString() {
            return "Favorites{" +
                    "favoriteByClass=" + favoriteByClass +
                    '}';
        }
    }
}
