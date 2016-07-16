package com.linfu.musictube.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sindhu.vadivelu on 07/07/16.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    private String id;
    private String artistId;
    private String name;
    private String artist;
    private String albumArt;
    private String year;
}
