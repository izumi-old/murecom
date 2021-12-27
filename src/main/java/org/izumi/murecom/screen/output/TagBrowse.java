package org.izumi.murecom.screen.output;

import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.output.Tag;

@UiController("murecom_Tag.browse")
@UiDescriptor("tag-browse.xml")
@LookupComponent("tagsTable")
public class TagBrowse extends StandardLookup<Tag> {
}
