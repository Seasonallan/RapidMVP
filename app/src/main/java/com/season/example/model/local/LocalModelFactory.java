package com.season.example.model.local;

import com.season.example.model.local.FileModel;
import com.season.example.model.local.SharedPreferencesModel;
import com.season.rapiddevelopment.model.BaseLocalModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class LocalModelFactory {

    private Map<String, BaseLocalModel> map = new HashMap<>();

    private <T extends BaseLocalModel> T generateLocalModel(Class<T> clazz) {
        try {
            if (map.containsKey(clazz.getName()))
                return (T) map.get(clazz.getName());
            else {
                T res = clazz.newInstance();
                map.put(clazz.getName(), res);
                return res;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public BaseLocalModel sharedPreferences() {
        return generateLocalModel(SharedPreferencesModel.class);
        //return new SharedPreferencesModel();
    }

    public BaseLocalModel file() {
        return generateLocalModel(FileModel.class);
        // return new FileModel();
    }

}
