package org.izumi.murecom.repository.impl;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.output.Song;
import org.izumi.murecom.repository.SongRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@RequiredArgsConstructor
@Repository
public class SongRepositoryImpl implements SongRepository {
    private final DataManager dataManager;

    @Override
    public Collection<Song> findAllByTagIn(Collection<String> tagsValues) {
        return dataManager.load(Song.class)
                .query("SELECT s FROM murecom_Song s " +
                        "INNER JOIN murecom_OutputTag ot ON ot.output = s " +
                        "INNER JOIN murecom_Tag t ON ot.tag = t " +
                        "WHERE t.value IN :values")
                .parameter("values", tagsValues)
                .fetchPlan(builder -> builder.addFetchPlan(FetchPlan.BASE)
                        .add("tags", builder1 -> builder1.addFetchPlan(FetchPlan.BASE)
                                .add("tag", builder2 -> builder2.addFetchPlan(FetchPlan.BASE))))
                .list();
    }
}
