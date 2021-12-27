package org.izumi.murecom.screen.output;

import io.jmix.ui.screen.*;
import org.izumi.murecom.entity.output.Song;

@UiController("murecom_Song.browse")
@UiDescriptor("song-browse.xml")
@LookupComponent("songsTable")
public class SongBrowse extends StandardLookup<Song> {
}
