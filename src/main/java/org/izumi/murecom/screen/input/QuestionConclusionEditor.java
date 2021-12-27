package org.izumi.murecom.screen.input;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.entity.input.QuestionConclusion;
import org.izumi.murecom.repository.FactRepository;
import org.izumi.murecom.util.SearchCollection;
import org.izumi.murecom.util.SearchHashSet;
import org.izumi.murecom.util.SearchLazyCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@UiController("murecom_QuestionConclusion.edit")
@UiDescriptor("question-conclusion-editor.xml")
@EditedEntityContainer("questionConclusionDc")
public class QuestionConclusionEditor extends StandardEditor<QuestionConclusion> {

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
    }

    @Install(to = "valueField", subject = "searchExecutor")
    private List<String> valueFieldSearchExecutor(String searchString,
                                                  @SuppressWarnings("unused") Map<String, Object> searchParams) {
        Collection<String> collection = searchCollection.findAllLike(s -> s.contains(searchString));
        collection.add(searchString);
        return new LinkedList<>(collection);
    }
}
