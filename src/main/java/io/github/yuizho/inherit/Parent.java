package io.github.yuizho.inherit;

public abstract class Parent {
    protected void greeting() {
        System.out.println("hello this is parent");
    }

    protected abstract Bean getBean();

    protected static class Bean {
        private final String name;
        // constructorをpublicにしないと別パッケージの小クラスでnewできない
        public Bean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
