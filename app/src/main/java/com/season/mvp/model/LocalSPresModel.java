package com.season.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.season.mvp.model.base.BaseLocalModel;


/**
 * Disc: SharedPreferences 存储
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 17:38
 */
public class LocalSPresModel extends BaseLocalModel {

    private SharedPreferences mSharedPreferences;

    public LocalSPresModel(String spName) {
        super();
        mSharedPreferences = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }


    @Override
    public String getValueImmediately(String key) {
        return mSharedPreferences.getString(key, "");
    }

    @Override
    public boolean setValueImmediately(String key, Object value) {
//        if (value instanceof Integer){
//            return mSharedPreferences.edit().putInt(key, (Integer) value).commit();
//        }else if (value instanceof Boolean){
//            return mSharedPreferences.edit().putBoolean(key, (Boolean) value).commit();
//        }
        return mSharedPreferences.edit().putString(key, value.toString()).commit();
    }
}
