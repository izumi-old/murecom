package org.izumi.murecom.screen.fact;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.data.TableItems;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.*;
import org.izumi.murecom.UserSource;
import org.izumi.murecom.entity.Fact;
import org.izumi.murecom.repository.FactRepository;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("murecom_Fact.browse")
@UiDescriptor("fact-browse.xml")
@LookupComponent("factsTable")
public class FactBrowse extends StandardLookup<Fact> {

    @Autowired
    private CollectionContainer<Fact> factsDc;

    @Autowired
    private DataLoader factsDl;

    @Autowired
    private UserSource userSource;

    @Autowired
    private FactRepository factRepository;

    @Autowired
    private GroupTable<Fact> factsTable;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        factsDl.setParameter("userId", userSource.getEffective().getId());
        getScreenData().loadAll();
    }

    @Install(to = "factsTable.create", subject = "initializer")
    private void factsTableCreateInitializer(Fact fact) {
        fact.setCreator(userSource.getEffective());
    }

    @Subscribe("removeAllBtn")
    public void onRemoveAllBtnClick(Button.ClickEvent event) {
        factRepository.remove(factsDc.getItems());
        getScreenData().loadAll();
    }

    @Install(to = "factsTable.removeAll", subject = "enabledRule")
    private boolean factsTableRemoveAllEnabledRule() {
        TableItems<Fact> items = factsTable.getItems();
        if (items == null) {
            return false;
        }

        return items.size() != 0;
    }
}
