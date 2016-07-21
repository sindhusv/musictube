package com.linfu.musictube;

import com.linfu.musictube.resource.HomePageResource;
import com.linfu.musictube.resource.MusictubeResource;
import com.linfu.musictube.service.AudioDbService;
import com.linfu.musictube.service.MusictubeService;
import com.linfu.musictube.service.YoutubeService;
import com.linfu.musictube.util.GitUtil;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.apache.http.client.HttpClient;

/**
 * Created by sindhu.vadivelu on 07/07/16.
 */
public class MusictubeApplication extends Application<MusictubeConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MusictubeApplication().run(args);
    }

    @Override
    public String getName() {
        return "Musictube Server";
    }

    @Override
    public void initialize(final Bootstrap<MusictubeConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new ViewBundle<MusictubeConfiguration>());
    }

    @Override
    public void run(MusictubeConfiguration musictubeConfiguration, Environment environment) throws Exception {
        GitUtil gitUtil = new GitUtil();

        final HttpClient httpClient = new HttpClientBuilder(environment).using(musictubeConfiguration.getHttpClientConfiguration())
                .build(getName());

        environment.jersey().register(
                new MusictubeResource(
                        new MusictubeService(
                                new AudioDbService(
                                        httpClient,
                                        new YoutubeService(httpClient)
                                ),
                                gitUtil
                        )
                )
        );

        environment.jersey().register(
                new HomePageResource(httpClient)
        );
    }
}
