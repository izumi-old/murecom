package org.izumi.murecom.entity.output;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Setter
@Getter
@JmixEntity
@Table(name = "MURECOM_OUTPUT")
@Entity(name = "murecom_Output")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Output {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OneToMany(mappedBy = "output")
    private Collection<OutputTag> tags;

    @InstanceName
    @Column(name = "NAME", nullable = false, length = 511)
    private String name;
}
