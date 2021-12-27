package org.izumi.murecom.util;

import org.izumi.murecom.entity.Fact;

import java.util.Collection;
import java.util.Optional;

public final class Facts extends ExtendedArrayList<Fact> {
    public Facts(Collection<? extends Fact> c) {
        super(c);
    }

    public boolean containsByName(String name) {
        for (Fact fact : this) {
            if (fact.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public Optional<Fact> findByName(String name) {
        for (Fact fact : this) {
            if (fact.getName().equals(name)) {
                return Optional.of(fact);
            }
        }

        return Optional.empty();
    }
}
