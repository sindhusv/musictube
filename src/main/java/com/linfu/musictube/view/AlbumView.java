package com.linfu.musictube.view;

import com.linfu.musictube.model.Album;
import io.dropwizard.views.View;

import java.util.List;

/**
 * Created by sindhu.vadivelu on 21/07/16.
 */
public class AlbumView extends View {

    private List<Album> albums;

    public AlbumView(List<Album> albums) {
        super("album.ftl");
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return this.albums;
    }
}
