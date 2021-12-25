package org.izumi.murecom;

import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.core.Id;
import io.jmix.core.Metadata;
import io.jmix.ui.util.OperationResult;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.Conclusion;
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
            if (condition.getMode() == Mode.AND && fact.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private void executeRule(Rule rule) {
        Collection<Conclusion> conclusions = rule.getConclusions();
        for (Conclusion conclusion : conclusions) {
            Optional<Fact> factOptional = factRepository.findByName(conclusion.getName());
            boolean conclusionAlreadyExists = factOptional.isPresent();
            if (conclusionAlreadyExists) {
                continue;
            }

            Fact fact = metadata.create(Fact.class);
            fact.setCreator(rule.getCreator());
            fact.setName(conclusion.getName());
            factRepository.save(fact);
        }
    }

    private FetchPlan createRulePlan() {
        return plans.builder(Rule.class)
                .addFetchPlan(FetchPlan.BASE)
                .add("creator", builder -> builder.addFetchPlan(FetchPlan.BASE))
                .add("conditions", builder -> builder.addFetchPlan(FetchPlan.BASE))
                .add("conclusions", builder -> builder.addFetchPlan(FetchPlan.BASE))
                .build();
    }
}
