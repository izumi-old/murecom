package org.izumi.murecom.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class SearchLazyCollection<T> implements SearchCollection<T> {
    private final SearchCollection<T> collection;
    private final Supplier<Collection<T>> valuesSupplier;
    private boolean initiated = false;

    public SearchLazyCollection(SearchCollection<T> collection, Supplier<Collection<T>> valuesSupplier) {
        this.collection = collection;
        this.valuesSupplier = valuesSupplier;
    }

    @Override
    public SearchCollection<T> findAllLike(Predicate<T> likeFunction) {
        if (!initiated) {
            initiate();
        }

        return collection.findAllLike(likeFunction);
    }

    @Override
    public int size() {
        if (!initiated) {
            initiate();
        }

        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        if (!initiated) {
            initiate();
        }

        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (!initiated) {
            initiate();
        }

        return collection.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        if (!initiated) {
            initiate();
        }

        return collection.iterator();
    }

    @Override
    public Object[] toArray() {
        if (!initiated) {
            initiate();
        }

        return collection.toArray();
    }

    @Override
    public <T1> T1[] toArray(@Nonnull T1[] a) {
        if (!initiated) {
            initiate();
        }

        return collection.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (!initiated) {
            initiate();
        }

        return collection.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (!initiated) {
            initiate();
        }

        return collection.remove(o);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        if (!initiated) {
            initiate();
        }

        return collection.remove(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends T> c) {
        if (!initiated) {
            initiate();
        }

        return collection.addAll(c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        if (!initiated) {
            initiate();
        }

        return collection.removeAll(c);
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        if (!initiated) {
            initiate();
        }

        return collection.retainAll(c);
    }

    @Override
    public void clear() {
        initiated = true;
        collection.clear();
    }

    private void initiate() {
        collection.addAll(valuesSupplier.get());
        initiated = true;
    }
}
