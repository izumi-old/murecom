package org.izumi.murecom.screen.fact;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.entity.Fact;
import org.izumi.murecom.repository.FactRepository;
import org.izumi.murecom.util.SearchCollection;
import org.izumi.murecom.util.SearchHashSet;
import org.izumi.murecom.util.SearchLazyCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@UiController("murecom_Fact.edit")
@UiDescriptor("fact-editor.xml")
@EditedEntityContainer("factDc")
public class FactEditor extends StandardEditor<Fact> {

    @Autowired
    private FactRepository factRepository;

    private SearchCollection<String> searchCollection;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        searchCollection = new SearchLazyCollection<>(
                new SearchHashSet<>(), factRepository::findAllDistinctFactRelatedNames);

        Fact fact = getEditedEntity();
        if (fact.getLeveled() == null) {
            fact.setLeveled(false);
        }

        if (fact.getWeight() == null) {
            fact.setWeight(0.0);
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
