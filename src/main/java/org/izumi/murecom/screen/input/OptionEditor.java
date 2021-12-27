package org.izumi.murecom.screen.input;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.entity.input.Option;
import org.izumi.murecom.entity.input.QuestionConclusion;

@UiController("murecom_Option.edit")
@UiDescriptor("option-editor.xml")
@EditedEntityContainer("optionDc")
public class OptionEditor extends StandardEditor<Option> {
    DataContext getDataContext() {
        return getScreenData().getDataContext();
    }

    @Install(to = "conclusionsTable.create", subject = "initializer")
    private void conclusionsTableCreateInitializer(QuestionConclusion conclusion) {
        conclusion.setOption(getEditedEntity());
    }

    @Install(to = "conclusionsTable.create", subject = "screenConfigurer")
    private void conclusionsTableCreateScreenConfigurer(QuestionConclusionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }

    @Install(to = "conclusionsTable.edit", subject = "screenConfigurer")
    private void conclusionsTableEditScreenConfigurer(QuestionConclusionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }
}
