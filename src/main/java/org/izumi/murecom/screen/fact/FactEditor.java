package org.izumi.murecom.screen.fact;

import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.Fact;

@UiController("murecom_Fact.edit")
@UiDescriptor("fact-editor.xml")
@EditedEntityContainer("factDc")
public class FactEditor extends StandardEditor<Fact> {
}
