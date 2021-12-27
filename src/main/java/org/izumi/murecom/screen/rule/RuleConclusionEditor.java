package org.izumi.murecom.screen.rule;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.entity.RuleConclusion;
import org.izumi.murecom.repository.FactRepository;
import org.izumi.murecom.util.SearchCollection;
import org.izumi.murecom.util.SearchHashSet;
import org.izumi.murecom.util.SearchLazyCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@UiController("murecom_RuleConclusion.edit")
@UiDescriptor("rule-conclusion-editor.xml")
@EditedEntityContainer("conclusionDc")
public class RuleConclusionEditor extends StandardEditor<RuleConclusion> {

    @Autowired
    private FactRepository factRepository;

    private SearchCollection<String> searchCollection;

    DataContext getDataContext() {
        return getScreenData().getDataContext();
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        searchCollection = new SearchLazyCollection<>(
                new SearchHashSet<>(), factRepository::findAllDistinctFactRelatedNames);

        RuleConclusion ruleConclusion = getEditedEntity();
        if (ruleConclusion.getLeveling() == null) {
            ruleConclusion.setLeveling(false);
        }
    }

    @Install(to = "nameField", subject = "searchExecutor")
    private List<String> nameFieldSearchExecutor(String searchString,
                                                 @SuppressWarnings("unused") Map<String, Object> searchParams) {
        Collection<String> collection = searchCollection.findAllLike(s -> s.contains(searchString));
        collection.add(searchString);
        return new LinkedList<>(collection);
    }
}
