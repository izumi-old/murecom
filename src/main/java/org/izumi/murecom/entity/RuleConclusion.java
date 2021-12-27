package org.izumi.murecom.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.function.Supplier;

@Getter
@Setter
@JmixEntity
@Table(name = "MURECOM_RULE_CONCLUSION")
@Entity(name = "murecom_RuleConclusion")
public class RuleConclusion extends Conclusion {

    @OnDelete(value = DeletePolicy.CASCADE)
    @JoinColumn(name = "RULE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Rule rule;

    public static class Builder {
        private final RuleConclusion ruleConclusion;
        public Builder(Supplier<RuleConclusion> initializer) {
            this.ruleConclusion = initializer.get();
        }

        public Builder withName(String name) {
            ruleConclusion.setName(name);
            return this;
        }

        public Builder withRule(Rule rule) {
            ruleConclusion.setRule(rule);
            return this;
        }

        public RuleConclusion build() {
            return ruleConclusion;
        }
    }
}
