package com.linfu.musictube.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sindhu.vadivelu on 17/07/16.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    private String id;
    private String trackNumber;
    private String title;
    private String youtubeLink;
    private String youtubeChannelId;
    private String youtubeChannelTitle;
    private String duration;
    private boolean lock;
}
