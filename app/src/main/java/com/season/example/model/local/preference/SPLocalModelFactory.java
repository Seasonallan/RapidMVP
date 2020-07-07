package com.season.example.model.local.preference;


import com.season.lib.model.LocalSPresModel;
import com.season.lib.model.base.BaseLocalModel;

/**
 * Disc: SharedPreferences工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class SPLocalModelFactory {


    public BaseLocalModel key() {
        return new LocalSPresModel("key");
    }

    public BaseLocalModel common() {
        return new LocalSPresModel("userCache");
    }

}
