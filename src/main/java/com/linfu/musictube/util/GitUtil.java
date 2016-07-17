package com.linfu.musictube.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfu.musictube.model.Albumn;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by sindhu.vadivelu on 17/07/16.
 */
public class GitUtil {
    private String localPath, remotePath;
    private Repository localRepo;
    private Git git;

    public GitUtil() throws IOException {
        localPath = "/Users/sindhu.vadivelu/flipkart/personal/mytest";
        remotePath = "https://github.com/sindhusv/musictube.git";
        localRepo = new FileRepository(localPath + "/.git");
        git = new Git(localRepo);
    }

    public void cloneRepo() throws IOException, GitAPIException {
        Git.cloneRepository().setURI(remotePath)
                .setDirectory(new File(localPath)).call();
    }

    public void commit() throws IOException, GitAPIException, JGitInternalException {
        git.commit().setMessage("Added myfile").call();
    }

    public void push() throws IOException, JGitInternalException, GitAPIException {
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.push().setCredentialsProvider(cp).call();
    }

    public void pull() throws IOException, JGitInternalException, GitAPIException {
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.pull().setCredentialsProvider(cp).call();
    }

    public void addAlbumn() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile");

        ObjectMapper mapper = new ObjectMapper();
        List<Albumn> albumns = mapper.readValue(myfile, new TypeReference<List<Albumn>>(){});

        Albumn newAlbumn = new Albumn();
        newAlbumn.setId("12345");
        newAlbumn.setArtistId("67890");
        newAlbumn.setName("Sindhu");
        newAlbumn.setArtist("Livi");
        newAlbumn.setAlbumArt("http://sjfgdg");
        newAlbumn.setYear("2016");
        albumns.add(newAlbumn);

        mapper.writeValue(myfile, albumns);

        git.add().addFilepattern("myfile").call();
    }


}
