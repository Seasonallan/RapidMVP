package com.season.example.entry;

import java.io.Serializable;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 22:25
 */
public class CommentItem implements Serializable{
    private static final long serialVersionUID = 8034018343104338811L;
    public String id;

    public String nickname;

    public String avatar_url;

    public String content;

    public String create_time;
}
