package org.izumi.murecom.screen.fact;

import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.*;
import org.izumi.murecom.UserSource;
import org.izumi.murecom.entity.Fact;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("murecom_Fact.browse")
@UiDescriptor("fact-browse.xml")
@LookupComponent("factsTable")
public class FactBrowse extends StandardLookup<Fact> {

    @Autowired
    private DataLoader factsDl;

    @Autowired
    private UserSource userSource;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        factsDl.setParameter("userId", userSource.getEffective().getId());
    }

    @Install(to = "factsTable.create", subject = "initializer")
    private void factsTableCreateInitializer(Fact fact) {
        fact.setCreator(userSource.getEffective());
    }
}
