package org.izumi.murecom.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@JmixEntity
@Table(name = "MURECOM_CONCLUSION")
@Entity(name = "murecom_Conclusion")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Conclusion {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "LEVELING", nullable = false)
    private Boolean leveling = false;

    @InstanceName
    @Column(name = "NAME", nullable = false, length = 511)
    private String name;
}
