package org.izumi.murecom.repository.impl;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.Rule;
import org.izumi.murecom.entity.User;
import org.izumi.murecom.repository.RuleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@RequiredArgsConstructor
@Repository
public class RuleRepositoryImpl implements RuleRepository {
    private final DataManager dataManager;

    @Validated
    @Override
    public Collection<Rule> findAllByUserId(FetchPlan plan, Id<User> userId) {
        return dataManager.load(Rule.class)
                .query("SELECT r FROM murecom_Rule r WHERE r.creator.id = :userId")
                .fetchPlan(plan)
                .parameter("userId", userId.getValue())
                .list();
    }
}
