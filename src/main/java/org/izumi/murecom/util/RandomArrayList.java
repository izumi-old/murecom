package org.izumi.murecom.util;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

public class RandomArrayList<T> extends ExtendedArrayList<T> implements RandomCollection<T> {
    private final Random random = new SecureRandom();

    public RandomArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public RandomArrayList() {
    }

    @SafeVarargs
    public RandomArrayList(T... ts) {
        super(ts);
    }

    public RandomArrayList(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public Optional<T> getRandom() {
        if (isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(get(random.nextInt(size())));
    }

    @Override
    public T getRandomNN() {
        if (isEmpty()) {
            throw new IllegalArgumentException("This list is empty. Cannot return a random element");
        }

        return get(random.nextInt(size()));
    }

    @Override
    public Optional<T> pollRandom() {
        if (isEmpty()) {
            return Optional.empty();
        }

        int index = random.nextInt(size());
        T t = get(index);
        remove(index);
        return Optional.of(t);
    }

    @Override
    public T pollRandomNN() {
        if (isEmpty()) {
            throw new IllegalArgumentException("This list is empty. Cannot poll a random element");
        }

        int index = random.nextInt(size());
        T t = get(index);
        remove(index);
        return t;
    }
}
