package org.izumi.murecom.repository;

import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import org.izumi.murecom.entity.Rule;
import org.izumi.murecom.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface RuleRepository {
    Collection<Rule> findAllByUserId(@NotNull FetchPlan plan, @NotNull Id<User> userId);
}
