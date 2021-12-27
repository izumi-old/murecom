package org.izumi.murecom.repository;

import org.izumi.murecom.entity.output.Song;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface SongRepository {

    /**
     * base_ FetchPlan is used implicitly for the entity and all related entities
     */
    @Validated
    Collection<Song> findAllByTagIn(@NotBlank Collection<String> tagsValues);
}
