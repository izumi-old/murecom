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
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@JmixEntity
@Table(name = "MURECOM_FACT", indexes = {
        @Index(name = "IDX_FACT_CREATOR_ID", columnList = "CREATOR_ID")
})
@Entity(name = "murecom_Fact")
public class Fact {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "CREATOR_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User creator;

    @InstanceName
    @Column(name = "NAME", nullable = false, unique = true, length = 511)
    private String name;

    @Column(name = "LEVELED", nullable = false)
    private Boolean leveled;

    @Column(name = "WEIGHT", nullable = false)
    private Double weight;
}
