package com.linfu.musictube.resource;

import com.codahale.metrics.annotation.Timed;
import com.linfu.musictube.model.Artist;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Track;
import com.linfu.musictube.service.MusictubeService;
import com.linfu.musictube.view.AlbumView;
import com.linfu.musictube.view.ArtistView;
import com.linfu.musictube.view.TrackView;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @POST
    @Path("/artist/{artistId}/album/{albumId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addArtist(@PathParam("artistId") String artistId, @PathParam("albumId") String albumId, ArrayList<String> validatedTrackIds) {
        try {
            Album album = musictubeService.getAlbumByAlbumId(albumId);

            Artist artist = musictubeService.getArtistByArtistId(artistId);

            List<Track> tracks = musictubeService.getTracks(artistId, albumId, album.getTitle());
            List<Track> validatedTracks = new ArrayList<Track>();

            for (Track track : tracks) {
                if (validatedTrackIds.contains(track.getId())) {
                    track.setLock(true);
                    validatedTracks.add(track);
                }
            }
            album.setTracks(validatedTracks);
            artist.setAlbums(Arrays.asList(album));

            musictubeService.addArtist(artist);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return false;
    }

    @GET
    @Path("/artist/{searchKey}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArtistView getArtistDetails(@PathParam("searchKey") String searchKey) {
        log.info("getAlbumDetails - Request: " + searchKey);
        try {
            List<Artist> artists = musictubeService.getArtistsBySearchKey(searchKey);
            return new ArtistView(artists);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/album/{artistName}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public AlbumView getAlbumDetails(@PathParam("artistName") String artistName) {
        log.info("getAlbumDetails - Request: " + artistName);
        try {
            List<Album> albums = musictubeService.getAlbumnsByArtistName(artistName);
            return new AlbumView(albums);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/{artistId}/{artistName}/{albumId}/{albumName}/track")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public TrackView getTrackDetails(@PathParam("artistId") String artistId, @PathParam("artistName") String artistName, @PathParam("albumId") String albumId, @PathParam("albumName") String albumName) {
        try {
            List<Track> tracks = musictubeService.getTracks(artistId, albumId, albumName);
            return new TrackView(artistId, artistName, albumId, albumName, tracks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
