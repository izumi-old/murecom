package org.izumi.murecom.screen;

import io.jmix.core.Id;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.ui.util.OperationResult;
import org.izumi.murecom.RulesLogic;
import org.izumi.murecom.UserSource;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("murecom_Primary")
@UiDescriptor("primary.xml")
public class Primary extends Screen {

    @Autowired
    private DataLoader rulesDl;

    @Autowired
    private UserSource userSource;

    @Autowired
    private RulesLogic rulesLogic;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Messages messages;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        rulesDl.setParameter("userId", userSource.getEffective().getId());
    }

    @Subscribe("runBtn")
    private void onRunBtnClick(Button.ClickEvent event) {
        OperationResult result = rulesLogic.proceed(Id.of(userSource.getEffective()));
        if (result.getStatus() == OperationResult.Status.SUCCESS) {
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption(messages.getMessage("ruleLogic.success.caption"))
                    .withDescription(messages.getMessage("ruleLogic.success.description"))
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
        } else {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messages.getMessage("ruleLogic.fail.caption"))
                    .withDescription(messages.getMessage("ruleLogic.fail.description"))
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
        }
    }
}
