package org.izumi.murecom.service;

import lombok.RequiredArgsConstructor;
import org.izumi.murecom.entity.input.Question;
import org.izumi.murecom.repository.QuestionRepository;
import org.izumi.murecom.util.RandomArrayList;
import org.izumi.murecom.util.RandomCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository repository;

    @Transactional
    @Override
    public Collection<Question> getRandom(int amount) {
        RandomCollection<Question> questions = new RandomArrayList<>(repository.findAll());
        int size = questions.size();
        if (size < amount) {
            throw new IllegalStateException("It is not enough of questions in database. " +
                    "There is " + size + " only");
        } else if (size == amount) {
            return questions;
        }

        Collection<Question> result = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            result.add(questions.pollRandomNN());
        }
        return result;
    }
}
