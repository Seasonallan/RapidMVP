package com.season.example.model.net.kuaifang;


import com.season.lib.model.base.BaseNetModel;

/**
 * Disc: 快放视频数据工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:02
 */
public class KuaifangNetModelFactory {

    public <T extends BaseNetModel> T generateNetModel(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }



    public KuaifangRequestVideo video() {
        return generateNetModel(KuaifangRequestVideo.class);
    }

    public KuaifangRequestKey key() {
        return generateNetModel(KuaifangRequestKey.class);
    }
}
