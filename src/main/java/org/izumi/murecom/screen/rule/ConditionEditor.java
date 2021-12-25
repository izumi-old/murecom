package org.izumi.murecom.screen.rule;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.Condition;
import org.izumi.murecom.entity.Mode;

@UiController("murecom_Condition.edit")
@UiDescriptor("condition-editor.xml")
@EditedEntityContainer("conditionDc")
public class ConditionEditor extends StandardEditor<Condition> {
    DataContext getDataContext() {
        return getScreenData().getDataContext();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        Condition condition = getEditedEntity();
        if (condition.getMode() == null) {
            condition.setMode(Mode.AND);
        }
    }
}
