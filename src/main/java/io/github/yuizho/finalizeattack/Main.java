package io.github.yuizho.finalizeattack;

import java.util.Objects;

public class Main {
    public static void main(String... args) {
        User user = AttackCode.create();
        // ここでオブジェクトが取れてしまう
        // result:
        // java.lang.NullPointerException
        // User{id=0, name='null'}
        System.out.println(user);
    }
}

/**
 * finalizeattack attackのデモコード
 */
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
        } catch (Exception e) {}
        return attackCode;
    }

    /**
     * GCされて、フィナライズキューにのったあと任意のタイミングでfinalizeが呼ばれる
     * このタイミングで中途半端な状態のthisを取得出来てしまう
     */
    @Override
    public void finalize() {
        // static fieldにthisをセットする
        attackCode = this;
    }

    AttackCode(int id, String name) {
        super(id, name);
    }
}

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
}