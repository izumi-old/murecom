package org.izumi.murecom.entity.output;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@JmixEntity
@Table(name = "MURECOM_TAG")
@Entity(name = "murecom_Tag")
public class Tag {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "VALUE", nullable = false, unique = true, length = 511)
    private String value;
}
