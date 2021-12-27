package org.izumi.murecom.screen.input;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.entity.input.Option;
import org.izumi.murecom.entity.input.Question;

@UiController("murecom_Question.edit")
@UiDescriptor("question-editor.xml")
@EditedEntityContainer("questionDc")
public class QuestionEditor extends StandardEditor<Question> {

    @Install(to = "optionsTable.create", subject = "screenConfigurer")
    private void optionsTableCreateScreenConfigurer(OptionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }

    @Install(to = "optionsTable.create", subject = "initializer")
    private void optionsTableCreateInitializer(Option option) {
        option.setQuestion(getEditedEntity());
    }

    @Install(to = "optionsTable.edit", subject = "screenConfigurer")
    private void optionsTableEditScreenConfigurer(OptionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }
}
