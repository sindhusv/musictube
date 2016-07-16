package com.linfu.musictube.resource;

import com.codahale.metrics.annotation.Timed;
import com.linfu.musictube.core.Artist;
import com.linfu.musictube.service.MusictubeService;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

    @POST
    @Path("/artist")
    public void addArtist(Artist artist) {

    }
}
