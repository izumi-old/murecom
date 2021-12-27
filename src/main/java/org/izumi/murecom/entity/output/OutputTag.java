package org.izumi.murecom.entity.output;

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
@Table(name = "MURECOM_OUTPUT_TAG", indexes = {
        @Index(name = "IDX_OUTPUTTAG_OUTPUT_ID", columnList = "OUTPUT_ID"),
        @Index(name = "IDX_OUTPUTTAG_TAG_ID", columnList = "TAG_ID")
})
@Entity(name = "murecom_OutputTag")
public class OutputTag {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "OUTPUT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Output output;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "TAG_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Tag tag;
}
