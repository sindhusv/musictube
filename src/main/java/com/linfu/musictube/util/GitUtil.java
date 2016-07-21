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
       // cloneRepo();
    }

    private void cloneRepo() throws IOException, GitAPIException {
        //TODO: check if repo already exist, if exist do only pull else clone
        Git.cloneRepository().setURI(remotePath)
                .setDirectory(new File(localPath)).call();
    }

    private void add() throws GitAPIException {
        git.add().addFilepattern(".").call();
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
        /*
            PATH: musictube-data/tracks/<artistId>/<albumId.json>

            albumnId.json
            {
                {
                      "id": "2263135",
                      "artistId": "118522",
                      "name": "Theri",
                      "artist": "G.V. Prakash Kumar",
                      "albumArt": "http://media.theaudiodb.com/images/media/album/thumb/tqvrtq1461646043.jpg",
                      "year": 2016,
                      "tracks": [
                            {
                              "duration": "289000",
                              "id": "34587718",
                              "title": "Jithu Jilladi",
                              "trackNumber": "1",
                              "youtubeLink": "9lkT6HnwCzQ"
                            },
                            {
                              "duration": "290000",
                              "id": "34587719",
                              "title": "Chellaakutty",
                              "trackNumber": "2",
                              "youtubeLink": "N65aMlDJWOE"
                            },
                            {
                              "duration": "320000",
                              "id": "34587720",
                              "title": "Yen Jeevan",
                              "trackNumber": "3",
                              "youtubeLink": "H3GhtM8V-d"
                            }
                          ]
                        }
            }
         */

        pull();

        File myfile = new File(localPath + "/src/main/resources/data/tracks/" + album.getArtistId() + "/" + album.getId() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        List<Album> albums = mapper.readValue(myfile, new TypeReference<List<Album>>(){});
        albums.add(album);
        mapper.writerWithDefaultPrettyPrinter().writeValue(myfile, albums);

        add();
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

        add();
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

        add();
        commit("Added track");
        push();
    }
}
