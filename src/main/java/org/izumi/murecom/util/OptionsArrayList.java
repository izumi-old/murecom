package org.izumi.murecom.util;

import org.izumi.murecom.entity.input.Option;
import org.izumi.murecom.entity.input.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class OptionsArrayList extends ArrayList<Option> {
    public OptionsArrayList(Question question) {
        this(question.getOptions());
    }

    public OptionsArrayList(Collection<? extends Option> c) {
        super(c);
    }

    public Optional<Option> findByAnswer(String answer) {
        for (Option option : this) {
            if (option.getAnswer().equals(answer)) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }
}
