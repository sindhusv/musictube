package com.linfu.musictube.resource;

import com.codahale.metrics.annotation.Timed;
import com.linfu.musictube.model.Artist;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Track;
import com.linfu.musictube.service.MusictubeService;
import com.linfu.musictube.view.AlbumView;
import com.linfu.musictube.view.TrackView;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindhu.vadivelu on 07/07/16.
 */

@Path("/musictube")
@Timed
@Slf4j
public class MusictubeResource {

    private MusictubeService musictubeService;

    public MusictubeResource(MusictubeService musictubeService) {
        this.musictubeService = musictubeService;
    }

    @GET
    @Path("/sample")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sample() {
        return "sample";
    }

    @POST
    @Path("/album/{albumId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addAlbum(@PathParam("albumId") String albumId, ArrayList<String> validatedTrackIds) {
        try {
            Album album = musictubeService.getAlbumByAlbumId(albumId);
            List<Track> tracks = musictubeService.getTracks(albumId, album.getTitle());
            List<Track> validatedTracks = new ArrayList<Track>();

            for (Track track : tracks) {
                if (validatedTrackIds.contains(track.getId())) {
                    track.setLock(true);
                    validatedTracks.add(track);
                }
            }
            album.setTracks(validatedTracks);
            musictubeService.addAlbum(album);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/artist")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addArtist(Artist artist) {
        try {
            musictubeService.addArtist(artist);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("{albumId}/track")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addTrack(@PathParam("albumId") String albumId, ArrayList<String> validatedTrackIds) {
        try {
            List<Track> tracks = musictubeService.getTracks(albumId, null);
            List<Track> validatedTracks = new ArrayList<Track>();

            for (Track track : tracks) {
                if (validatedTrackIds.contains(track.getId())) {
                    validatedTracks.add(track);
                }
            }
            musictubeService.addTrack(validatedTracks);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/album/{artistName}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public AlbumView getAlbumDetails(@PathParam("artistName") String artistName) {
        log.info(String.format("getAlbumDetails - Request: ", artistName));
        try {
            List<Album> albums = musictubeService.getAlbumnsByArtistName(artistName);
            return new AlbumView(albums);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/track/{albumId}/{albumName}/{artistName}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public TrackView getTrackDetails(@PathParam("albumId") String albumId, @PathParam("albumName") String albumName, @PathParam("artistName") String artistName) {
        try {
            List<Track> tracks = musictubeService.getTracks(albumId, albumName);
            return new TrackView(albumId, albumName, artistName, tracks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
