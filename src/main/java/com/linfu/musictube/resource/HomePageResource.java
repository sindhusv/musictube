package com.linfu.musictube.resource;

import com.codahale.metrics.annotation.Timed;
import com.linfu.musictube.view.HomePageView;
import org.apache.http.client.HttpClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sindhu.vadivelu on 21/07/16.
 */

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomePageResource {
    private HttpClient httpClient;

    public HomePageResource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GET
    @Timed
    public HomePageView getHomePage() {
        return new HomePageView();
    }
}
