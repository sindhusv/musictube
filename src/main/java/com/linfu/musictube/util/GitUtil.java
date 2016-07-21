package com.linfu.musictube.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Artist;
import com.linfu.musictube.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindhu.vadivelu on 17/07/16.
 */

@Slf4j
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

    public void add() throws GitAPIException {
        git.add().addFilepattern(".").call();
    }

    public void commit(String message) throws IOException, GitAPIException, JGitInternalException {
        git.commit().setMessage(message).call();
    }

    public void push() throws IOException, JGitInternalException, GitAPIException {
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.push().setCredentialsProvider(cp).call();
    }

    public void pull() throws IOException, JGitInternalException, GitAPIException {
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.pull().setCredentialsProvider(cp).call();
    }

    public void addAlbum(Album album) throws IOException, GitAPIException {
        /**
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

        Album albumData;
        List<Album> albumMetaData = new ArrayList<Album>();
        ObjectMapper mapper = new ObjectMapper();

        File dataDir = new File(localPath + "/src/main/resources/data");
        if (!dataDir.exists()) {
            log.info("Creating Data directory");
            dataDir.mkdir();
        }
        File tracksDir = new File(localPath + "/src/main/resources/data/tracks");
        if (!tracksDir.exists()) {
            log.info("Creating Tracks directory");
            tracksDir.mkdir();
        }
        File artistDir = new File(localPath + "/src/main/resources/data/tracks/" + album.getArtistId());
        if (!artistDir.exists()) {
            log.info(String.format("Creating artist- {} directory", album.getArtistId()));
            artistDir.mkdir();
        }
        File albumMetadataFile = new File(localPath + "/src/main/resources/data/tracks/" + album.getArtistId() + "/albums.json");
        if (!albumMetadataFile.exists()) {
            log.info("Creating albumMetadata file");
            albumMetadataFile.createNewFile();
        } else {
            albumMetaData = mapper.readValue(albumMetadataFile, new TypeReference<List<Album>>(){});
        }

        boolean albumMetadataAlreadyPresent = false;
        for (Album localAlbum : albumMetaData) {
            if (localAlbum.getId().equals(album.getId())) {
                albumMetadataAlreadyPresent = true;
                break;
            }
        }

        if (!albumMetadataAlreadyPresent) {
            Album albumWithoutTracks = album;
            albumWithoutTracks.setTracks(null);
            albumMetaData.add(albumWithoutTracks);

            mapper.writerWithDefaultPrettyPrinter().writeValue(albumMetadataFile, albumMetaData);
        }


        File albumFile = new File(localPath + "/src/main/resources/data/tracks/" + album.getArtistId() + "/" + album.getId() + ".json");
        if (!albumFile.exists()) {
            log.info(String.format("Creating albumn-{} file", album.getId()));
            albumFile.createNewFile();
            albumData = album;
        } else {
            albumData = mapper.readValue(albumFile, Album.class);
            //TODO: validate whether we are appending any already locked tracks
            albumData.getTracks().addAll(album.getTracks());
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(albumFile, albumData);
    }

    public void addArtist(Artist artist) throws IOException, GitAPIException {
        /**
         * /tracks/artists.json
         * /tracks/<artistId>/albumns.json
         */

        List<Artist> artistData = new ArrayList<Artist>();
        ObjectMapper mapper = new ObjectMapper();

        File dataDir = new File(localPath + "/src/main/resources/data");
        if (!dataDir.exists()) {
            log.info("Creating Data directory");
            dataDir.mkdir();
        }

        File artistFile = new File(localPath + "/src/main/resources/data/artists.json");
        if (!artistFile.exists()) {
            log.info("Creating artists.json file");
            artistFile.createNewFile();
        } else {
            artistData = mapper.readValue(artistFile, new TypeReference<List<Artist>>(){});
        }

        boolean artistAlreadyPresent = false;
        for (Artist localArtist : artistData) {
            if (localArtist.getId() == artist.getId()) {
                artistAlreadyPresent = true;
                break;
            }
        }

        if (!artistAlreadyPresent) {
            Artist artistWithoutAlbumn = artist;
            artistWithoutAlbumn.setAlbums(null);
            artistData.add(artistWithoutAlbumn);
            mapper.writerWithDefaultPrettyPrinter().writeValue(artistFile, artistData);
        }

        for (Album album : artist.getAlbums()) {
            this.addAlbum(album);
        }
    }

    public void addTrack(Track track) throws IOException, GitAPIException {
        File myfile = new File(localPath + "/src/main/resources/track.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Track> tracks = mapper.readValue(myfile, new TypeReference<List<Track>>(){});
        tracks.add(track);
        mapper.writeValue(myfile, tracks);
    }
}
