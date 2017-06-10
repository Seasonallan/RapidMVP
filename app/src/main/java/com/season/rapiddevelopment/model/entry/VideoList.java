package com.season.rapiddevelopment.model.entry;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:20
 */
public class VideoList {

    public ArrayList<VideoItem> videos;

    public ArrayList<VideoItem> movies;

    public String is_more;

    public ArrayList<VideoItem> top_ads;

    public ArrayList<VideoItem> nav_ads;

    public boolean hasMore(){
        return !TextUtils.isEmpty(is_more) && is_more.equals("1");
    }

    public String name;

    public String album_type;

    public String img_url;

    public String video_count;
}
