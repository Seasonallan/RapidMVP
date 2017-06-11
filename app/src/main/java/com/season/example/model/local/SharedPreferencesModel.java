package com.season.example.model.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.season.rapiddevelopment.model.BaseLocalModel;

import java.util.Objects;

/**
 * Disc: SharedPreferences 存储
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 17:38
 */
public class SharedPreferencesModel extends BaseLocalModel {

    private SharedPreferences mSharedPreferences;
    private final String mFileName = "FileName";

    public SharedPreferencesModel() {
        super();
        mSharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getValueImmediately(String key) {
        return mSharedPreferences.getString(key, null);
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
