package org.izumi.murecom.repository;

import org.izumi.murecom.entity.input.Question;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

public interface QuestionRepository {

    /**
     * base_ Fetch plan is used implicitly for the entity and all related entity-attributes
     */
    @Validated
    Collection<Question> findAll();
}
