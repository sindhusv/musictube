package com.linfu.musictube.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Artist;
import com.linfu.musictube.model.Track;
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

    public GitUtil() throws IOException, GitAPIException {
        localPath = "/Users/sindhu.vadivelu/flipkart/personal/mytest";
        remotePath = "https://github.com/sindhusv/musictube.git";
        localRepo = new FileRepository(localPath + "/.git");
        git = new Git(localRepo);
        cloneRepo();
    }

    private void cloneRepo() throws IOException, GitAPIException {
        //TODO: check if repo already exist, if exist do only pull else clone
        Git.cloneRepository().setURI(remotePath)
                .setDirectory(new File(localPath)).call();
    }

    private void commit(String message) throws IOException, GitAPIException, JGitInternalException {
        git.commit().setMessage(message).call();
    }

    private void push() throws IOException, JGitInternalException, GitAPIException {
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.push().setCredentialsProvider(cp).call();
    }

    private void pull() throws IOException, JGitInternalException, GitAPIException {
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.pull().setCredentialsProvider(cp).call();
    }

    public void addAlbum(Album album) throws IOException, GitAPIException {
        pull();

        File myfile = new File(localPath + "/src/main/resources/album.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Album> albums = mapper.readValue(myfile, new TypeReference<List<Album>>(){});
        albums.add(album);
        mapper.writeValue(myfile, albums);

        git.add().addFilepattern("album.json").call();
        commit("Added album");
        push();
    }

    public void addArtist(Artist artist) throws IOException, GitAPIException {
        pull();

        File myfile = new File(localPath + "/src/main/resources/artist.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Artist> artists = mapper.readValue(myfile, new TypeReference<List<Artist>>(){});
        artists.add(artist);
        mapper.writeValue(myfile, artists);

        git.add().addFilepattern("artist.json").call();
        commit("Added artist");
        push();
    }

    public void addTrack(Track track) throws IOException, GitAPIException {
        pull();

        File myfile = new File(localPath + "/src/main/resources/track.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Track> tracks = mapper.readValue(myfile, new TypeReference<List<Track>>(){});
        tracks.add(track);
        mapper.writeValue(myfile, tracks);

        git.add().addFilepattern("track.json").call();
        commit("Added track");
        push();
    }
}
