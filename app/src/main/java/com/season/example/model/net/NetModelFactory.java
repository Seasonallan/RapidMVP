package com.season.example.model.net;

import com.season.example.model.net.kuaifang.NetKeyModel;
import com.season.example.model.net.kuaifang.NetVideoModel;
import com.season.rapiddevelopment.model.BaseNetModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:02
 */
public class NetModelFactory {

    private Map<String, BaseNetModel> map = new HashMap<>();

    public <T extends BaseNetModel> T generateNetModel(Class<T> clazz) {
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

    public NetVideoModel video() {
        return generateNetModel(NetVideoModel.class);
    }

    public NetKeyModel key() {
        return generateNetModel(NetKeyModel.class);
    }
}
