package org.izumi.murecom.screen;

import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Window;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("murecom_Root")
@UiDescriptor("root-screen.xml")
@Route(path = "root", root = true)
public class Root extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    private void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }
}
