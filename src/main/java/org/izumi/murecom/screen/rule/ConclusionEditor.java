package org.izumi.murecom.screen.rule;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.Conclusion;
import org.springframework.context.ApplicationContext;

@UiController("murecom_Conclusion.edit")
@UiDescriptor("conclusion-editor.xml")
@EditedEntityContainer("conclusionDc")
public class ConclusionEditor extends StandardEditor<Conclusion> {
    DataContext getDataContext() {
        return getScreenData().getDataContext();
    }
}
