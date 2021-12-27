package org.izumi.murecom.util;

import java.util.Collection;
import java.util.function.Predicate;

public interface SearchCollection<T> extends Collection<T> {
    SearchCollection<T> findAllLike(Predicate<T> likeFunction);
}
