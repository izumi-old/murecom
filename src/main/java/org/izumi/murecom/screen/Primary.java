package org.izumi.murecom.screen;

import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.UserSource;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("murecom_Primary")
@UiDescriptor("primary.xml")
public class Primary extends Screen {

    @Autowired
    private DataLoader rulesDl;

    @Autowired
    private UserSource userSource;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        rulesDl.setParameter("userId", userSource.getEffective().getId());
    }
}
