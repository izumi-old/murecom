package org.izumi.murecom.security;

import org.izumi.murecom.entity.User;
import io.jmix.securitydata.user.AbstractDatabaseUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collection;

@Primary
@Component("murecom_UserRepository")
public class DatabaseUserRepository extends AbstractDatabaseUserRepository<User> {

    @Nonnull
    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void initSystemUser(User systemUser) {
        Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(FullAccess.CODE)
                .build();
        systemUser.setAuthorities(authorities);
    }

    @Override
    protected void initAnonymousUser(User anonymousUser) {
    }
}
