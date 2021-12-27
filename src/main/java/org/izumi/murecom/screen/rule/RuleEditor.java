package org.izumi.murecom.screen.rule;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.murecom.UserSource;
import org.izumi.murecom.entity.RuleConclusion;
import org.izumi.murecom.entity.Condition;
import org.izumi.murecom.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("murecom_Rule.edit")
@UiDescriptor("rule-editor.xml")
@EditedEntityContainer("ruleDc")
public class RuleEditor extends StandardEditor<Rule> {

    @Autowired
    private UserSource userSource;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        Rule rule = getEditedEntity();
        rule.setCreator(userSource.getEffective());
    }

    @Install(to = "conditionsTable.create", subject = "initializer")
    private void conditionsTableCreateInitializer(Condition condition) {
        condition.setRule(getEditedEntity());
    }

    @Install(to = "conclusionsTable.create", subject = "initializer")
    private void conclusionsTableCreateInitializer(RuleConclusion ruleConclusion) {
        ruleConclusion.setRule(getEditedEntity());
    }

    @Install(to = "conditionsTable.create", subject = "screenConfigurer")
    private void conditionsTableCreateScreenConfigurer(ConditionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }

    @Install(to = "conditionsTable.edit", subject = "screenConfigurer")
    private void conditionsTableEditScreenConfigurer(ConditionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }

    @Install(to = "conclusionsTable.create", subject = "screenConfigurer")
    private void conclusionsTableCreateScreenConfigurer(RuleConclusionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }

    @Install(to = "conclusionsTable.edit", subject = "screenConfigurer")
    private void conclusionsTableEditScreenConfigurer(RuleConclusionEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }
}
