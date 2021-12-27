package org.izumi.murecom.repository;

import io.jmix.core.Id;
import org.izumi.murecom.entity.Fact;
import org.izumi.murecom.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface FactRepository {

    @Validated
    Set<String> findAllDistinctFactRelatedNames();

    @Validated
    Fact add(@NotNull Fact fact);

    @Validated
    Collection<Fact> add(@NotEmpty Collection<Fact> facts);

    @Validated
    Fact update(@NotNull Fact fact);

    /**
     * Base FetchPlan with base_ FetchPlan to all related entities is used implicitly
     */
    @Validated
    Optional<Fact> findByName(@NotBlank String name);

    /**
     * Base FetchPlan with base_ FetchPlan to all related entities is used implicitly
     */
    @Validated
    Collection<Fact> findByNameIn(@NotEmpty Collection<String> names);

    @Validated
    void removeIfFindIn(@NotBlank Collection<String> names);

    @Validated
    void remove(@NotNull Fact fact);

    @Validated
    void remove(@NotBlank Collection<Fact> facts);

    /**
     * Base FetchPlan with base_ FetchPlan to all related entities is used implicitly
     */
    @Validated
    Collection<Fact> findTop(@NotNull Id<User> userId, @Positive int amount);
}
