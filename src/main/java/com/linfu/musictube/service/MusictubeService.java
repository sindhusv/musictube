package com.linfu.musictube.service;

import com.linfu.musictube.core.Artist;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;


/**
 * Created by sindhu.vadivelu on 07/07/16.
 */
public class MusictubeService {

    public void addArtist(Artist artist) throws IOException, GitAPIException {
        String localPath = "~/flipkart/personal/mytest";
        String remotePath = "git@github.com:sindhusv/musictube.git";
        Repository localRepo = new FileRepository(localPath + "/.git");
        Git git = new Git(localRepo);

        File myfile = new File(localPath + "/myfile");
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }
}
