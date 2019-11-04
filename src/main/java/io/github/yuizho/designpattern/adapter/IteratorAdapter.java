package io.github.yuizho.designpattern.adapter;

import java.util.Enumeration;
import java.util.Iterator;

public class IteratorAdapter<T> implements Enumeration<T> {
    private Iterator<T> delegator;
    public IteratorAdapter(Iterator<T> delegator) {
        this.delegator = delegator;
    }

    @Override
    public boolean hasMoreElements() {
        return delegator.hasNext();
    }

    @Override
    public T nextElement() {
        return delegator.next();
    }
}
