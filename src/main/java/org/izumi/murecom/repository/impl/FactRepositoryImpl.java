package org.izumi.murecom.repository.impl;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.Id;
import io.jmix.core.SaveContext;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.Condition;
import org.izumi.murecom.entity.Fact;
import org.izumi.murecom.entity.RuleConclusion;
import org.izumi.murecom.entity.User;
import org.izumi.murecom.entity.input.QuestionConclusion;
import org.izumi.murecom.entity.output.Tag;
import org.izumi.murecom.repository.FactRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class FactRepositoryImpl implements FactRepository {
    private final DataManager dataManager;

    @Transactional
    @Override
    public Set<String> findAllDistinctFactRelatedNames() {
        Collection<String> fromFacts = dataManager.load(Fact.class)
                .query("SELECT f FROM murecom_Fact f")
                .fetchPlan(builder -> builder.add("name"))
                .list()
                .stream()
                .map(Fact::getName)
                .collect(Collectors.toSet());
        Collection<String> fromRulesConclusions = dataManager.load(RuleConclusion.class)
                .query("SELECT c FROM murecom_RuleConclusion c")
                .fetchPlan(builder -> builder.add("name"))
                .list()
                .stream()
                .map(RuleConclusion::getName)
                .collect(Collectors.toSet());
        Collection<String> fromQuestionsConclusions = dataManager.load(QuestionConclusion.class)
                .query("SELECT c FROM murecom_QuestionConclusion c")
                .fetchPlan(builder -> builder.add("value"))
                .list()
                .stream()
                .map(QuestionConclusion::getValue)
                .collect(Collectors.toSet());
        Collection<String> fromConditions = dataManager.load(Condition.class)
                .query("SELECT c FROM murecom_Condition c")
                .fetchPlan(builder -> builder.add("name"))
                .list()
                .stream()
                .map(Condition::getName)
                .collect(Collectors.toSet());
        Collection<String> fromTags = dataManager.load(Tag.class)
                .query("SELECT t FROM murecom_Tag t")
                .fetchPlan(builder -> builder.add("value"))
                .list()
                .stream()
                .map(Tag::getValue)
                .collect(Collectors.toSet());
        Set<String> result = new HashSet<>();
        result.addAll(fromFacts);
        result.addAll(fromRulesConclusions);
        result.addAll(fromQuestionsConclusions);
        result.addAll(fromConditions);
        result.addAll(fromTags);
        return result;
    }

    @Override
    public Fact add(Fact fact) {
        return dataManager.save(fact);
    }

    @Override
    public Collection<Fact> add(Collection<Fact> facts) {
        return dataManager.save(new SaveContext().saving(facts)).stream()
                .map(o -> (Fact) o)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Fact update(Fact fact) {
        remove(fact);
        return add(fact);
    }

    @Override
    public Optional<Fact> findByName(String name) {
        return dataManager.load(Fact.class)
                .query("SELECT f FROM murecom_Fact f WHERE f.name = :name")
                .fetchPlan(this::getBaseFetchPlan)
                .parameter("name", name)
                .optional();
    }

    @Override
    public Collection<Fact> findByNameIn(Collection<String> names) {
        return dataManager.load(Fact.class)
                .query("SELECT f FROM murecom_Fact f WHERE f.name IN :names")
                .fetchPlan(this::getBaseFetchPlan)
                .parameter("names", names)
                .list();
    }

    @Override
    public void removeIfFindIn(Collection<String> names) {
        dataManager.remove(dataManager.load(Fact.class)
                .query("SELECT f FROM murecom_Fact f WHERE f.name IN :names")
                .parameter("names", names)
                .fetchPlan(builder -> builder.add("id"))
                .list());
    }

    @Override
    public void remove(Fact fact) {
        dataManager.remove(Id.of(fact));
    }

    @Override
    public void remove(Collection<Fact> facts) {
        dataManager.remove(facts);
    }

    @Override
    public Collection<Fact> findTop(Id<User> userId, int amount) {
        return dataManager.load(Fact.class)
                .query("SELECT f FROM murecom_Fact f " +
                        "WHERE f.creator.id = :userId ORDER BY f.weight DESC")
                .fetchPlan(this::getBaseFetchPlan)
                .maxResults(amount)
                .parameter("userId", userId.getValue())
                .list();
    }

    private void getBaseFetchPlan(FetchPlanBuilder builder) {
        builder.addFetchPlan(FetchPlan.BASE)
                .add("creator", builder1 -> builder1.addFetchPlan(FetchPlan.BASE))
                .build();
    }
}
