package com.linfu.musictube.service;

import com.linfu.musictube.model.Artist;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Track;
import com.linfu.musictube.util.GitUtil;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sindhu.vadivelu on 07/07/16.
 */
public class MusictubeService {

    private AudioDbService audioDbService;
    private GitUtil git;

    public MusictubeService(AudioDbService audioDbService, GitUtil gitUtil) {
        this.audioDbService = audioDbService;
        git = gitUtil;
    }

    public void addArtist(Artist artist) throws IOException, GitAPIException {
        git.pull();
        git.addArtist(artist);
        git.add();
        git.commit("Added artist");
        git.push();
    }

    public List<Track> getTracks(String artistId, String albumnId, String albumnName) throws IOException {
        List<Track> audioDbTracks = audioDbService.getTracksByAlbumnId(albumnId, albumnName);
        List<Track> localTracks = git.getTrackIds(artistId, albumnId);

        List<String> localTrackIds = new ArrayList<String>();
        if (localTracks != null) {
            for (Track localTrack : localTracks) {
                localTrackIds.add(localTrack.getId());
            }
        }

        if (audioDbTracks != null) {
            for (Track track : audioDbTracks) {
                if (localTrackIds.contains(track.getId()))
                    track.setLock(true);
            }
        }

        return audioDbTracks;
    }

    public Album getAlbumByAlbumId(String albumId) throws IOException {
        return audioDbService.getAlbumByAlbumId(albumId);
    }

    public List<Album> getAlbumnsByArtistName(String albumnName) throws IOException {
        return audioDbService.getAlbumsByArtistName(albumnName);
    }

    public Artist getArtistByArtistId(String artistId) throws IOException {
        return audioDbService.getArtistByArtistId(artistId);
    }

    public List<Artist> getArtistsBySearchKey(String searchKey) throws IOException {
        return audioDbService.getArtistsBySearchKey(searchKey);
    }
}
