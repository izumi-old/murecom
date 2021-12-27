package org.izumi.murecom.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ExtendedArrayList<T> extends ArrayList<T> {
    public ExtendedArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public ExtendedArrayList() {
    }

    @SafeVarargs
    public ExtendedArrayList(T... ts) {
        this(Arrays.asList(ts));
    }

    public ExtendedArrayList(Collection<? extends T> c) {
        super(c);
    }
}
