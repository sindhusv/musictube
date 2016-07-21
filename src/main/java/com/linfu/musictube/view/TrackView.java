package com.linfu.musictube.view;

import com.linfu.musictube.model.Track;
import io.dropwizard.views.View;

import java.util.List;

/**
 * Created by sindhu.vadivelu on 21/07/16.
 */
public class TrackView extends View{

    private List<Track> tracks;
    private String albumId;
    private String albumName;
    private String artistName;

    public TrackView(String albumId, String albumName, String artistName, List<Track> tracks) {
        super("track.ftl");
        this.tracks = tracks;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistName = artistName;
    }

    public List<Track> getTracks() {
        return this.tracks;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }
}
