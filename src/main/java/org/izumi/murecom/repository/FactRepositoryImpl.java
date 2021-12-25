package org.izumi.murecom.repository;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.Fact;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class FactRepositoryImpl implements FactRepository {
    private final DataManager dataManager;

    @Validated
    @Override
    public Fact save(Fact fact) {
        return dataManager.save(fact);
    }

    @Validated
    @Override
    public Collection<Fact> save(Collection<Fact> facts) {
        return dataManager.save(facts);
    }

    @Validated
    @Override
    public Optional<Fact> findByName(String name) {
        return dataManager.load(Fact.class)
                .query("SELECT f FROM murecom_Fact f WHERE f.name = :name")
                .fetchPlan(FetchPlan.BASE)
                .parameter("name", name)
                .optional();
    }

    @Override
    public Collection<Fact> findByNameIn(Collection<String> names) {
        return dataManager.load(Fact.class)
                .query("SELECT f FROM murecom_Fact f WHERE f.name IN :names")
                .fetchPlan(FetchPlan.BASE)
                .parameter("names", names)
                .list();
    }
}
