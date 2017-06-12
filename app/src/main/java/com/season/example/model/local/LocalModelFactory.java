package com.season.example.model.local;

import com.season.example.model.local.database.DBLocalModelFactory;
import com.season.example.model.local.file.FileLocalModelFactory;
import com.season.example.model.local.preference.SPLocalModelFactory;

/**
 * Disc: 本地数据工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class LocalModelFactory {

    public SPLocalModelFactory sharedPreferences() {
        return new SPLocalModelFactory();
    }

    public FileLocalModelFactory file() {
        return new FileLocalModelFactory();
    }

    public DBLocalModelFactory database() {
        return new DBLocalModelFactory();
    }

}
