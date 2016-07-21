package com.linfu.musictube.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sindhu.vadivelu on 21/07/16.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeInfo {
    private String trackId;
    private String videoId;
    private String videoTitle;
    private String channelId;
    private String channelTitle;
}
