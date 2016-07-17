package com.linfu.musictube.service;

import com.linfu.musictube.model.Artist;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Track;
import com.linfu.musictube.util.GitUtil;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;


/**
 * Created by sindhu.vadivelu on 07/07/16.
 */
public class MusictubeService {

    private GitUtil git;

    public MusictubeService(GitUtil gitUtil) {
        git = gitUtil;
    }

    public void addAlbum(Album album) throws IOException, GitAPIException {
        git.addAlbum(album);
    }

    public void addArtist(Artist artist) throws IOException, GitAPIException {
        git.addArtist(artist);
    }

    public void addTrack(Track track) throws IOException, GitAPIException {
        git.addTrack(track);
    }
}
