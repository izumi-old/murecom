package org.izumi.murecom.repository;

import org.izumi.murecom.entity.Fact;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface FactRepository {
    Fact save(@NotNull Fact fact);
    Collection<Fact> save(@NotEmpty Collection<Fact> facts);

    /**
     * Base FetchPlan is used implicitly
     */
    Optional<Fact> findByName(@NotBlank String name);

    /**
     * Base FetchPlan is used implicitly
     */
    Collection<Fact> findByNameIn(@NotEmpty Collection<String> names);
}
