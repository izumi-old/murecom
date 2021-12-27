package org.izumi.murecom.entity.input;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
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

@Setter
@Getter
@JmixEntity
@Table(name = "MURECOM_OPTION", indexes = {
        @Index(name = "IDX_OPTION_QUESTION_ID", columnList = "QUESTION_ID")
})
@Entity(name = "murecom_Option")
public class Option {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OneToMany(mappedBy = "option")
    private Collection<QuestionConclusion> conclusions;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Question question;

    @Column(name = "ANSWER", nullable = false, length = 511)
    private String answer;
}
