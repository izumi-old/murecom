package org.izumi.murecom.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@JmixEntity
@Table(name = "MURECOM_CONDITION")
@Entity(name = "murecom_Condition")
public class Condition {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "MODE", nullable = false)
    private Integer mode;

    @Column(name = "NEGATION", nullable = false)
    private Boolean negation;

    @InstanceName
    @Column(name = "NAME", nullable = false, length = 511)
    private String name;

    @OnDelete(value = DeletePolicy.CASCADE)
    @JoinColumn(name = "RULE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Rule rule;

    public Mode getMode() {
        return mode != null ? Mode.fromId(mode) : null;
    }

    public void setMode(Mode mode) {
        this.mode = mode != null ? mode.getId() : null;
    }
}
