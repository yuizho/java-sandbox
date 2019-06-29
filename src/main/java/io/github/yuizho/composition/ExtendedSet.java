package io.github.yuizho.composition;

import java.util.Collection;
import java.util.HashSet;

public class ExtendedSet<E> extends HashSet<E> {
    // 要素の挿入回数を記録する変数
    private int addedCount = 0;

    public ExtendedSet() {}

    public ExtendedSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E elm) {
        addedCount += 1;
        return super.add(elm);
    }

    @Override
    public boolean addAll(Collection<? extends E> elms) {
        addedCount += elms.size();
        return super.addAll(elms);
    }

    public int getAddedCount() {
        return addedCount;
    }
}
