package org.izumi.murecom.screen.input;

import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.input.Question;

@UiController("murecom_Question.browse")
@UiDescriptor("question-browse.xml")
@LookupComponent("questionsTable")
public class QuestionBrowse extends StandardLookup<Question> {
}
