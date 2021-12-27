package org.izumi.murecom.service;

import org.izumi.murecom.entity.input.Question;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import java.util.Collection;

public interface QuestionService {

    /**
     * base_ Fetch plan is used implicitly for the entity and all related entity-attributes
     */
    @Validated
    Collection<Question> getRandom(@Positive int amount);
}
