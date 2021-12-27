package org.izumi.murecom.entity.input;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Setter
@Getter
@JmixEntity
@Table(name = "MURECOM_QUESTION")
@Entity(name = "murecom_Question")
public class Question {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "BODY", nullable = false, length = 511)
    private String body;

    @OneToMany(mappedBy = "question")
    private Collection<Option> options;
}
