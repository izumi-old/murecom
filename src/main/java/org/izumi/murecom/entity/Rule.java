package org.izumi.murecom.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@JmixEntity
@Table(name = "MURECOM_RULE", indexes = {
        @Index(name = "IDX_RULE_CREATOR_ID", columnList = "CREATOR_ID")
})
@Entity(name = "murecom_Rule")
public class Rule {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "CREATOR_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User creator;

    @OneToMany(mappedBy = "rule")
    private Collection<Condition> conditions;

    @OneToMany(mappedBy = "rule")
    private Collection<RuleConclusion> ruleConclusions;

    @InstanceName
    @Column(name = "NAME", unique = true)
    private String name;
}
