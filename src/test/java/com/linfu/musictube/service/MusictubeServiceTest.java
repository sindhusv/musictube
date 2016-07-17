package com.linfu.musictube.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.linfu.musictube.model.Albumn;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by sindhu.vadivelu on 07/07/16.
 */
public class MusictubeServiceTest {
    private String localPath, remotePath;
    private Repository localRepo;
    private Git git;

    @Before
    public void init() throws IOException {
        localPath = "/Users/sindhu.vadivelu/flipkart/personal/mytest";
        remotePath = "https://github.com/sindhusv/musictube.git";
        localRepo = new FileRepository(localPath + "/.git");
        git = new Git(localRepo);
    }

    @Test
    public void testCreate() throws IOException {
        Repository newRepo = new FileRepository(localPath + ".git");
        newRepo.create();
    }

    @Test
    public void testClone() throws IOException, GitAPIException {
        Git.cloneRepository().setURI(remotePath)
                .setDirectory(new File(localPath)).call();
    }

    @Test
    public void testAdd() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile");
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }

    @Test
    public void testEdit() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile");
        /*String jsonData = "{\"results\":[{\"lat\":\"value\",\"lon\":\"value\" }]}";
        FileWriter fw = new FileWriter(myfile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Some text here for a reason");
        bw.flush();
        bw.close();
        fw.close();*/

        /*String jsonData = "{\"id\": \"2261436\"," +
                "    \"artistId\": \"129757\"," +
                "    \"name\": \"Naanum Rowdy Dhaan\"," +
                "    \"artist\": \"Anirudh Ravichander\"," +
                "    \"albumArt\": \"http://media.theaudiodb.com/images/media/album/thumb/xvtuqq1457460899.jpg\"," +
                "    \"year\": 2015" +
                "  }";

        String albumnContent = new Scanner(myfile).useDelimiter("\\Z").next();
*/
        ObjectMapper mapper = new ObjectMapper();
        List<Albumn> albumns = mapper.readValue(myfile, new TypeReference<List<Albumn>>(){});

        //List<Albumn> albumns = mapper.readValue(albumnContent, new TypeReference<List<Albumn>>(){});

        Albumn newAlbumn = new Albumn();
        newAlbumn.setId("12345");
        newAlbumn.setArtistId("67890");
        newAlbumn.setName("Sindhu");
        newAlbumn.setArtist("Livi");
        newAlbumn.setAlbumArt("http://sjfgdg");
        newAlbumn.setYear("2016");
        albumns.add(newAlbumn);

        //String jsonInString = mapper.writeValueAsString(albumns);
        mapper.writeValue(myfile, albumns);

        /*FileWriter fw = new FileWriter(myfile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(jsonInString);
        bw.flush();
        bw.close();
        fw.close();*/

        git.add().addFilepattern("myfile").call();
    }

    @Test
    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        git.commit().setMessage("Added myfile").call();
    }

    @Test
    public void testPush() throws IOException, JGitInternalException,
            GitAPIException {

        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.push().setCredentialsProvider(cp).call();

    }

    @Test
    public void testTrackMaster() throws IOException, JGitInternalException,
            GitAPIException {
        git.branchCreate().setName("master")
                .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("origin/master").setForce(true).call();
    }

    @Test
    public void testPull() throws IOException, GitAPIException {
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider( "sindhusv", "Fresh@2014" );
        git.pull().setCredentialsProvider(cp).call();
    }

}
