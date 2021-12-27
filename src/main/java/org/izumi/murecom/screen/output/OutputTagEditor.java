package org.izumi.murecom.screen.output;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.output.OutputTag;

@UiController("murecom_OutputTag.edit")
@UiDescriptor("output-tag-editor.xml")
@EditedEntityContainer("outputTagDc")
public class OutputTagEditor extends StandardEditor<OutputTag> {
    DataContext getDataContext() {
        return getScreenData().getDataContext();
    }
}
