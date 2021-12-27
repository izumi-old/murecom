package org.izumi.murecom.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

public class SearchHashSet<T> extends HashSet<T> implements SearchCollection<T> {
    public SearchHashSet() {
    }

    public SearchHashSet(Collection<? extends T> c) {
        super(c);
    }

    public SearchHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SearchHashSet(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public SearchHashSet<T> findAllLike(Predicate<T> likeFunction) {
        SearchHashSet<T> searchHashSet = new SearchHashSet<>();
        for (T t : this) {
            if (likeFunction.test(t)) {
                searchHashSet.add(t);
            }
        }
        return searchHashSet;
    }
}
