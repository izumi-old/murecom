package org.izumi.murecom;

import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.core.Id;
import io.jmix.core.Metadata;
import io.jmix.ui.util.OperationResult;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.RuleConclusion;
import org.izumi.murecom.entity.Condition;
import org.izumi.murecom.entity.Fact;
import org.izumi.murecom.entity.Mode;
import org.izumi.murecom.entity.Rule;
import org.izumi.murecom.entity.User;
import org.izumi.murecom.repository.FactRepository;
import org.izumi.murecom.repository.RuleRepository;
import org.izumi.murecom.util.Facts;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RulesLogicImpl implements RulesLogic {
    private final RuleRepository ruleRepository;
    private final FactRepository factRepository;
    private final FetchPlans plans;
    private final Metadata metadata;

    @Override
    public OperationResult proceed(Id<User> userId) {
        try {
            Collection<Rule> rules = ruleRepository.findAllByUserId(createRulePlan(), userId);
            rules.stream().filter(this::isRuleMustBeExecuted).forEach(this::executeRule);
            return OperationResult.success();
        } catch (Exception ex) {
            return OperationResult.fail();
        }
    }

    private boolean isRuleMustBeExecuted(Rule rule) {
        Collection<Condition> conditions = rule.getConditions();
        Collection<String> conditionsNames = conditions.stream()
                .map(Condition::getName)
                .collect(Collectors.toList());

        Facts facts = new Facts(factRepository.findByNameIn(conditionsNames));
        for (Condition condition : conditions) {
            String name = condition.getName();
            Optional<Fact> fact = facts.findByName(name);
            if (condition.getNegation() != null && condition.getNegation()) {
                if (fact.isPresent() && condition.getMode() == Mode.AND) {
                    return false;
                }
            } else {
                if (fact.isEmpty() && condition.getMode() == Mode.AND) {
                    return false;
                }
            }
        }

        return true;
    }

    private Collection<Fact> getPlayedFacts(Rule rule) {
        Collection<Condition> conditions = rule.getConditions();
        Collection<String> conditionsNames = conditions.stream()
                .map(Condition::getName)
                .collect(Collectors.toList());

        Facts facts = new Facts(factRepository.findByNameIn(conditionsNames));
        Collection<Fact> played = new LinkedList<>();
        for (Condition condition : conditions) {
            String name = condition.getName();
            Optional<Fact> fact = facts.findByName(name);
            if (condition.getNegation() != null && condition.getNegation()) {
                if (fact.isPresent() && condition.getMode() == Mode.AND) {
                    return Collections.emptyList();
                }
            } else {
                if (fact.isEmpty() && condition.getMode() == Mode.AND) {
                    return Collections.emptyList();
                }
            }
            fact.ifPresent(played::add);
        }

        return played;
    }

    private void executeRule(Rule rule) {
        Collection<RuleConclusion> ruleConclusions = rule.getRuleConclusions();
        Collection<Fact> played = getPlayedFacts(rule);
        for (RuleConclusion ruleConclusion : ruleConclusions) {
            Optional<Fact> factOptional = factRepository.findByName(ruleConclusion.getName());
            boolean conclusionAlreadyExists = factOptional.isPresent();
            if (conclusionAlreadyExists) {
                Fact fact = factOptional.get();
                if (ruleConclusion.getLeveling()) {
                    fact.setLeveled(true);
                    fact.setWeight(0.0);
                } else {
                    double weight = fact.getWeight();
                    fact.setWeight(weight + (((double) played.size()) / ruleConclusions.size()));
                }
                factRepository.update(fact);
                continue;
            }

            Fact fact = metadata.create(Fact.class);
            fact.setCreator(rule.getCreator());
            fact.setName(ruleConclusion.getName());
            fact.setLeveled(false);
            fact.setWeight(((double) played.size()) / ruleConclusions.size());
            factRepository.add(fact);
        }
    }

    private FetchPlan createRulePlan() {
        return plans.builder(Rule.class)
                .addFetchPlan(FetchPlan.BASE)
                .add("creator", builder -> builder.addFetchPlan(FetchPlan.BASE))
                .add("conditions", builder -> builder.addFetchPlan(FetchPlan.BASE))
                .add("ruleConclusions", builder -> builder.addFetchPlan(FetchPlan.BASE))
                .build();
    }
}
