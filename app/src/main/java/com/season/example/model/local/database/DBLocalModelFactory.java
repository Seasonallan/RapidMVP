package com.season.example.model.local.database;

import com.season.example.entry.ClientKey;
import com.season.example.entry.VideoItem;
import com.season.rapiddevelopment.model.BaseDatabaseModel;

/**
 * Disc: 数据库工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class DBLocalModelFactory {


    public BaseDatabaseModel video() {
        return new DatabaseModel(VideoItem.class);
    }

    public BaseDatabaseModel key() {
        return new DatabaseModel(ClientKey.class);
    }

}
