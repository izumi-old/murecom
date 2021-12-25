package org.izumi.murecom.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum Mode implements EnumClass<Integer> {
    AND(10),
    OR(20);

    private final Integer id;

    Mode(Integer value) {
        this.id = value;
    }

    @Nonnull
    public Integer getId() {
        return id;
    }

    @Nullable
    public static Mode fromId(Integer id) {
        for (Mode at : Mode.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
