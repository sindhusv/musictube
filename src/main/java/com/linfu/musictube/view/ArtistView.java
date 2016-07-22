package com.linfu.musictube.view;

import com.linfu.musictube.model.Artist;
import io.dropwizard.views.View;

import java.util.List;

/**
 * Created by sindhu.vadivelu on 22/07/16.
 */
public class ArtistView extends View {
    private List<Artist> artists;

    public ArtistView(List<Artist> artists) {
        super("artist.ftl");
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return this.artists;
    }
}
