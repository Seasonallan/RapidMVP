package com.season.example.model.local.preference;

import com.season.rapiddevelopment.model.BaseLocalModel;

/**
 * Disc: SharedPreferences工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class SPLocalModelFactory {


    public BaseLocalModel key() {
        return new SharedPreferencesModel("key");
    }

    public BaseLocalModel common() {
        return new SharedPreferencesModel("userCache");
    }

}
