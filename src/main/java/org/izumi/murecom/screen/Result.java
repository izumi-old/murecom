package org.izumi.murecom.screen;

import io.jmix.core.Id;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.StandardOutcome;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.izumi.murecom.UserSource;
import org.izumi.murecom.entity.Fact;
import org.izumi.murecom.entity.output.Song;
import org.izumi.murecom.repository.FactRepository;
import org.izumi.murecom.repository.SongRepository;
import org.izumi.murecom.util.Facts;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UiController("murecom_Result")
@UiDescriptor("result.xml")
public class Result extends Screen {

    @Autowired
    private TextArea<String> resultArea;

    @Autowired
    private FactRepository factRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserSource userSource;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        Facts facts = new Facts(factRepository.findTop(Id.of(userSource.getEffective()), 3));
        Collection<Song> songs = songRepository.findAllByTagIn(facts.stream()
                .map(Fact::getName)
                .collect(Collectors.toList()));
        List<SongRating> rating = songs.stream()
                .map(song -> rate(facts, song))
                .sorted()
                .limit(10)
                .collect(Collectors.toList());

        StringBuilder result = new StringBuilder("Вероятно вам понравятся:\n");
        for (int i = 0; i < rating.size(); i++) {
            result.append('\t').append(i + 1).append(". ").append(rating.get(i).song.getName()).append('\n');
        }
        resultArea.setValue(result.toString());
    }

    private SongRating rate(Facts facts, Song song) {
        int occurrences = 0;
        Collection<String> tags = song.getTags().stream()
                .map(tag -> tag.getTag().getValue())
                .collect(Collectors.toSet());
        for (String tag : tags) {
            if (facts.containsByName(tag)) {
                occurrences++;
            }
        }
        return new SongRating(occurrences, song);
    }

    @Subscribe("closeBtn")
    private void onCloseBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static final class SongRating implements Comparable<SongRating> {
        final int occurrences;
        final Song song;

        @Override
        public int compareTo(@Nonnull SongRating o) {
            return Comparator.comparing(SongRating::getOccurrences, Integer::compareTo)
                    .thenComparing(s -> s.getSong().getName(), String::compareTo)
                    .compare(this, o);
        }
    }
}
