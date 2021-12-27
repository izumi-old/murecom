package org.izumi.murecom.screen.output;

import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.output.OutputTag;
import org.izumi.murecom.entity.output.Song;

@UiController("murecom_Song.edit")
@UiDescriptor("song-editor.xml")
@EditedEntityContainer("songDc")
public class SongEditor extends StandardEditor<Song> {

    @Install(to = "tagsTable.create", subject = "screenConfigurer")
    private void tagsTableCreateScreenConfigurer(OutputTagEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }

    @Install(to = "tagsTable.edit", subject = "screenConfigurer")
    private void tagsTableEditScreenConfigurer(OutputTagEditor editor) {
        editor.getDataContext().setParent(getScreenData().getDataContext());
    }

    @Install(to = "tagsTable.create", subject = "initializer")
    private void tagsTableCreateInitializer(OutputTag outputTag) {
        outputTag.setOutput(getEditedEntity());
    }
}
