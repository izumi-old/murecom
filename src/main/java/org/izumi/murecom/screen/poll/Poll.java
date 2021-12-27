package org.izumi.murecom.screen.poll;

import io.jmix.core.Metadata;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.RadioButtonGroup;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.StandardOutcome;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.UserSource;
import org.izumi.murecom.entity.Fact;
import org.izumi.murecom.entity.User;
import org.izumi.murecom.entity.input.Option;
import org.izumi.murecom.entity.input.Question;
import org.izumi.murecom.entity.input.QuestionConclusion;
import org.izumi.murecom.repository.FactRepository;
import org.izumi.murecom.repository.QuestionRepository;
import org.izumi.murecom.service.QuestionService;
import org.izumi.murecom.util.ExtendedArrayList;
import org.izumi.murecom.util.OptionsArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@UiController("murecom_Poll")
@UiDescriptor("Poll.xml")
@EditedEntityContainer("conclusionsDc")
public class Poll extends Screen {

    @Autowired
    private RadioButtonGroup<String> question1;

    @Autowired
    private RadioButtonGroup<String> question2;

    @Autowired
    private RadioButtonGroup<String> question3;

    @Autowired
    private RadioButtonGroup<String> question4;

    @Autowired
    private RadioButtonGroup<String> question5;

    @Autowired
    private Metadata metadata;

    @Autowired
    private FactRepository factRepository;

    @Autowired
    private UserSource userSource;

    @Autowired
    private Notifications notifications;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    private final Map<RadioButtonGroup<String>, Question> pollToQuestion = new HashMap<>();
    private Collection<RadioButtonGroup<String>> polls;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        Collection<Question> questions = questionService.getRandom(5);
        polls = new ExtendedArrayList<>(question1, question2, question3, question4, question5);
        pollToQuestion.clear();
        Iterator<Question> questionIterator = questions.iterator();
        for (RadioButtonGroup<String> poll : polls) {
            Question question = questionIterator.next();
            pollToQuestion.put(poll, question);
            initPoll(poll, question);
        }
    }

    @Subscribe("proceedBtn")
    private void onProceedBtnClick(Button.ClickEvent event) {
        ValidationErrors errors = validate();
        if (!errors.isEmpty()) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .withDescription(errors.getAll().stream()
                            .map(item -> item.description)
                            .collect(Collectors.joining()))
                    .withHideDelayMs(-1)
                    .show();
            return;
        }

        deleteOldFacts();
        saveNewFacts();
        close(StandardOutcome.COMMIT);
    }

    private void initPoll(RadioButtonGroup<String> group, Question q) {
        group.setCaption(q.getBody());
        group.setOptionsList(q.getOptions().stream()
                .map(Option::getAnswer)
                .sorted(String::compareTo)
                .collect(Collectors.toList()));
    }

    private void deleteOldFacts() {
        questionRepository.findAll().forEach(question -> {
            Collection<Option> options = question.getOptions();
            Collection<String> names = new LinkedList<>();
            for (Option option : options) {
                names.addAll(option.getConclusions().stream()
                        .map(QuestionConclusion::getValue)
                        .collect(Collectors.toList()));
            }
            factRepository.removeIfFindIn(names);
        });
    }

    private void saveNewFacts() {
        Collection<Fact> toSave = new LinkedList<>();
        User creator = userSource.getEffective();
        polls.forEach(poll -> {
            Question question = pollToQuestion.get(poll);
            OptionsArrayList options = new OptionsArrayList(question);
            Optional<Option> optionOptional = options.findByAnswer(poll.getValue());
            if (optionOptional.isPresent()) {
                Option chosen = optionOptional.get();
                toSave.addAll(chosen.getConclusions().stream().map(conclusion -> {
                    Fact fact = metadata.create(Fact.class);
                    fact.setCreator(creator);
                    fact.setName(conclusion.getValue());
                    fact.setLeveled(false);
                    fact.setWeight(0.0);
                    return fact;
                }).collect(Collectors.toList()));
            }
        });
        factRepository.add(toSave);
    }

    private ValidationErrors validate() {
        ValidationErrors errors = new ValidationErrors();
        for (RadioButtonGroup<String> poll : polls) {
            if (poll.isEmpty()) {
                errors.add("Необходимо ответить на все вопросы");
                break;
            }
        }
        return errors;
    }
}
