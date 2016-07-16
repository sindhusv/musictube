package com.linfu.musictube;

import com.linfu.musictube.resource.MusictubeResource;
import com.linfu.musictube.service.MusictubeService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Created by sindhu.vadivelu on 07/07/16.
 */
public class MusictubeApplication extends Application<MusictubeConfiguration> {

    @Override
    public void run(MusictubeConfiguration musictubeConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new MusictubeResource(new MusictubeService()));
    }
}
