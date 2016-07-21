package com.linfu.musictube.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfu.musictube.model.YoutubeInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

/**
 * Created by sindhu.vadivelu on 20/07/16.
 */
public class YoutubeService {
    private HttpClient httpClient;

    public YoutubeService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    //API key name - youtubelinfu
    //API Key - AIzaSyB9jNGDt2XYRGe1dR2TV-kcdNZ4uteCT0k

    public YoutubeInfo getTrack(String albumnName, String trackId, String trackName) throws IOException {

        //https://www.googleapis.com/youtube/v3/search?part=snippet&q=dog&key=AIzaSyB9jNGDt2XYRGe1dR2TV-kcdNZ4uteCT0k

        String searchKey = albumnName + " " + trackName + " lyrics";
        searchKey =  searchKey.replace(" ", "+"); //"Kaththi+Aathi+lyrics";
        String apiKey = "AIzaSyB9jNGDt2XYRGe1dR2TV-kcdNZ4uteCT0k";
        //HttpRequest request = new HttpGet("/youtube/v3/search?part=snippet&q=" + searchKey + "&key=" + apiKey);
        HttpGet request = new HttpGet("/youtube/v3/search?part=snippet&q=" + searchKey + "&key=" + apiKey);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000000).setConnectTimeout(1000).build();
        request.setConfig(requestConfig);

        HttpHost url = new HttpHost("www.googleapis.com", 443, "https");

        final HttpResponse httpResponse = httpClient.execute(url, request);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResult = objectMapper.readTree(result);

                for (int i=0 ; i<jsonResult.get("items").size() ; i++) {
                    if(jsonResult.get("items").get(i).get("id").get("videoId") != null) {
                        YoutubeInfo youtubeInfo = new YoutubeInfo();
                        youtubeInfo.setTrackId(trackId);
                        youtubeInfo.setVideoId(jsonResult.get("items").get(i).get("id").get("videoId").asText());
                        youtubeInfo.setVideoTitle(jsonResult.get("items").get(i).get("snippet").get("title").asText());
                        youtubeInfo.setChannelId(jsonResult.get("items").get(i).get("snippet").get("channelId").asText());
                        youtubeInfo.setChannelTitle(jsonResult.get("items").get(i).get("snippet").get("channelTitle").asText());
                        return youtubeInfo;
                    }
                }
            }
        }
        return null;
    }
}
