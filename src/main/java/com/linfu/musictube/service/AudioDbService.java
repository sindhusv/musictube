package com.linfu.musictube.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfu.musictube.model.Album;
import com.linfu.musictube.model.Track;
import com.linfu.musictube.model.YoutubeInfo;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindhu.vadivelu on 17/07/16.
 */
public class AudioDbService {

    private HttpClient httpClient;
    private YoutubeService youtubeService;

    public AudioDbService(HttpClient httpClient, YoutubeService youtubeService) {
        this.httpClient = httpClient;
        this.youtubeService = youtubeService;
    }

    public Album getAlbumByAlbumId(String albumId) throws IOException {
        //http://www.theaudiodb.com/api/v1/json/1/album.php?m=2109615

        HttpRequest request = new HttpGet("/api/v1/json/1/album.php?m=" + albumId);
        HttpHost url = new HttpHost("www.theaudiodb.com");

        final HttpResponse httpResponse = httpClient.execute(url, request);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResult = objectMapper.readTree(result);

                Album album = new Album();

                album.setId(jsonResult.get("album").get(0).get("idAlbum").asText());
                album.setArtistId(jsonResult.get("album").get(0).get("idArtist").asText());
                album.setArtistName(jsonResult.get("album").get(0).get("strArtist").asText());
                album.setTitle(jsonResult.get("album").get(0).get("strAlbum").asText());
                album.setYear(jsonResult.get("album").get(0).get("intYearReleased").asText());
                album.setAlbumArt(jsonResult.get("album").get(0).get("strAlbumThumb").asText());

                return album;
            }
        }
        return null;
    }

    public List<Album> getAlbumsByArtistName(String artistName) throws IOException{
        //www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=coldplay

        HttpRequest request = new HttpGet("/api/v1/json/1/searchalbum.php?s=" + artistName);
        HttpHost url = new HttpHost("www.theaudiodb.com");

        final HttpResponse httpResponse = httpClient.execute(url, request);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResult = objectMapper.readTree(result);

                List<Album> albums = new ArrayList<Album>();
                for(int i=0 ; i<jsonResult.get("album").size() ; i++) {
                    Album album = new Album();
                    album.setId(jsonResult.get("album").get(i).get("idAlbum").asText());
                    album.setArtistId(jsonResult.get("album").get(i).get("idArtist").asText());
                    album.setArtistName(jsonResult.get("album").get(i).get("strArtist").asText());
                    album.setTitle(jsonResult.get("album").get(i).get("strAlbum").asText());
                    album.setYear(jsonResult.get("album").get(i).get("intYearReleased").asText());
                    album.setAlbumArt(jsonResult.get("album").get(i).get("strAlbumThumb").asText());

                    albums.add(album);
                }
                return albums;
            }
        }
        return null;
    }

    public List<Track> getTracksByAlbumnId(String albumnId, String albumName) throws IOException {
        //http://www.theaudiodb.com/api/v1/json/1/track.php?m={albumId}
        if (albumName == null) {
            Album album = this.getAlbumByAlbumId(albumnId);
            albumName = album.getTitle();
        }


        HttpRequest request = new HttpGet("/api/v1/json/1/track.php?m=" + albumnId);
        HttpHost url = new HttpHost("www.theaudiodb.com");

        final HttpResponse httpResponse = httpClient.execute(url, request);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResult = objectMapper.readTree(result);

                List<Track> tracks = new ArrayList<Track>();
                for(int i=0 ; i<jsonResult.get("track").size() ; i++) {
                    String trackId = jsonResult.get("track").get(i).get("idTrack").asText();
                    String trackNumber = jsonResult.get("track").get(i).get("intTrackNumber").asText();
                    String trackName = jsonResult.get("track").get(i).get("strTrack").asText();
                    String duration = jsonResult.get("track").get(i).get("intDuration").asText();
                    YoutubeInfo youtubeInfo = youtubeService.getTrack(albumName, trackId, trackName);

                    Track track = new Track();
                    track.setId(trackId);
                    track.setTrackNumber(trackNumber);
                    track.setTitle(trackName);
                    track.setDuration(duration);
                    track.setYoutubeLink(youtubeInfo.getVideoId());
                    track.setYoutubeChannelId(youtubeInfo.getChannelId());
                    track.setYoutubeChannelTitle(youtubeInfo.getChannelTitle());
                    track.setLock(false);
                    tracks.add(track);
                }
                return tracks;
            }
        }
        return null;
    }
}
