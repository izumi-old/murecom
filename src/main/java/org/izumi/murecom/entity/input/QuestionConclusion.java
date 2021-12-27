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
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@JmixEntity
@Table(name = "MURECOM_QUESTION_CONCLUSION", indexes = {
        @Index(name = "IDX_QUESTIONCONCLUSION", columnList = "QUESTION_ID"),
        @Index(name = "IDX_QUESTIONCONCLUSION", columnList = "OPTION_ID")
})
@Entity(name = "murecom_QuestionConclusion")
public class QuestionConclusion {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "OPTION_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Option option;

    @Column(name = "VALUE", nullable = false, length = 511)
    private String value;
}
