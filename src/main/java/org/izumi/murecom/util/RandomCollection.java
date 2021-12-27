package org.izumi.murecom.util;

import java.util.Collection;
import java.util.Optional;

public interface RandomCollection<T> extends Collection<T> {
    Optional<T> getRandom();
    T getRandomNN();
    Optional<T> pollRandom();
    T pollRandomNN();
}
