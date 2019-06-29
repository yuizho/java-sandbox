package io.github.yuizho.composition;

import java.util.Collection;
import java.util.Set;

public class CompositionSetWrapper<E> extends CompositionSet<E> {
    private int addedCount = 0;

    public CompositionSetWrapper(Set<E> set) {
        super(set);
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
