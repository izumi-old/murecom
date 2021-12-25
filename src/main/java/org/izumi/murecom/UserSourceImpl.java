package org.izumi.murecom;

import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserSourceImpl implements UserSource {
    private final CurrentUserSubstitution substitution;

    @Override
    public User getAuthenticated() {
        return (User) substitution.getAuthenticatedUser();
    }

    @Override
    public boolean hasSubstituted() {
        return substitution.getSubstitutedUser() != null;
    }

    @Override
    public Optional<User> getSubstituted() {
        if (!hasSubstituted()) {
            return Optional.empty();
        }

        return Optional.of((User) substitution.getEffectiveUser());
    }

    @Override
    public User getEffective() {
        return (User) substitution.getEffectiveUser();
    }
}
