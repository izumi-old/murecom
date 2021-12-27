package org.izumi.murecom.repository.impl;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.input.Question;
import org.izumi.murecom.repository.QuestionRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@RequiredArgsConstructor
@Repository
public class QuestionRepositoryImpl implements QuestionRepository {
    private final DataManager dataManager;

    @Override
    public Collection<Question> findAll() {
        return dataManager.load(Question.class)
                 .query("SELECT q FROM murecom_Question q")
                 .fetchPlan(builder -> builder.addFetchPlan(FetchPlan.BASE)
                         .add("options", builder1 -> builder1.addFetchPlan(FetchPlan.BASE)
                                 .add("conclusions", builder2 -> builder2.addFetchPlan(FetchPlan.BASE))))
                .list();
    }
}
