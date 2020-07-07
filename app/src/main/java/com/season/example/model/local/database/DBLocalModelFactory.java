package com.season.example.model.local.database;

import com.season.example.entry.ClientKey;
import com.season.example.entry.VideoItem;
import com.season.lib.model.LocalDatabaseModel;
import com.season.lib.model.base.BaseDatabaseModel;

/**
 * Disc: 数据库工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class DBLocalModelFactory {


    public BaseDatabaseModel video() {
        return new LocalDatabaseModel(VideoItem.class);
    }

    public BaseDatabaseModel key() {
        return new LocalDatabaseModel(ClientKey.class);
    }

}
