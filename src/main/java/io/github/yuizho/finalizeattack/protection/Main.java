package io.github.yuizho.finalizeattack.protection;

import java.util.Objects;

public class Main {
    public static void main(String... args) {
        User user = AttackCode.create();
        System.out.println(user);
    }
}

class AttackCode extends User {
    public static AttackCode attackCode;

    public static AttackCode create() {
        try {
            new AttackCode(0, null);
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            int i = 0;
            while (attackCode == null) {
                System.gc();
                Thread.sleep(10);
                i += 1;
                if (i > 100) {
                    return null;
                }
            }
        } catch (Exception e) { return null; }
        return attackCode;
    }

    AttackCode(int id, String name) {
        super(id, name);
    }
}

/**
 * finalizeattack attack対策のデモコード
 */
class User {
    final int id;
    final String name;
    // nameが nullだとぬるぽ
    User(int id, String name) {
        Objects.requireNonNull(name);
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // finalizeをfinalで定義しておけば、サブクラスで実装されることはない
    public final void finalize() {
    }
}