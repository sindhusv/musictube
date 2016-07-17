package com.linfu.musictube;

import com.linfu.musictube.resource.MusictubeResource;
import com.linfu.musictube.service.MusictubeService;
import com.linfu.musictube.util.GitUtil;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

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
    public void run(MusictubeConfiguration musictubeConfiguration, Environment environment) throws Exception {
        GitUtil gitUtil = new GitUtil();

        environment.jersey().register(new MusictubeResource(new MusictubeService(gitUtil)));
    }
}
