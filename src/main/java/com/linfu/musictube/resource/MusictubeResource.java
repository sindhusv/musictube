package com.linfu.musictube.resource;

import com.codahale.metrics.annotation.Timed;
import com.linfu.musictube.model.Artist;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Track;
import com.linfu.musictube.service.MusictubeService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by sindhu.vadivelu on 07/07/16.
 */

@Path("/musictube")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
@Slf4j
public class MusictubeResource {

    private MusictubeService musictubeService;

    public MusictubeResource(MusictubeService musictubeService) {
        this.musictubeService = musictubeService;
    }

    @GET
    @Path("/sample")
    public String sample() {
        return "sample";
    }

    @POST
    @Path("/album")
    public void addAlbum(Album album) {
        try {
            musictubeService.addAlbum(album);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/artist")
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
    @Path("/track")
    public void addTrack(Track track) {
        try {
            musictubeService.addTrack(track);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
