package com.linfu.musictube;

import com.github.dirkraft.dropwizard.fileassets.FileAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.views.ViewBundle;

/**
 * Created by sindhu.vadivelu on 21/07/16.
 */
public class MusictubeApplicationDev extends MusictubeApplication {
    public static void main(String[] args) throws Exception {
        new MusictubeApplicationDev().run(args);
    }

    @Override
    public void initialize(Bootstrap<MusictubeConfiguration> bootstrap) {
        bootstrap.addBundle(new FileAssetsBundle("src/main/resources/assets" , "/assets"));
        bootstrap.addBundle(new ViewBundle<MusictubeConfiguration>());
    }
}
