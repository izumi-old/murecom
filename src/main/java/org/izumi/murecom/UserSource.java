package org.izumi.murecom;

import org.izumi.murecom.entity.User;

import java.util.Optional;

public interface UserSource {
    User getAuthenticated();
    boolean hasSubstituted();
    Optional<User> getSubstituted();
    User getEffective();
}
