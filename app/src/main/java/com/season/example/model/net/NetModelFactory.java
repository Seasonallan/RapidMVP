package com.season.example.model.net;

import com.season.example.model.net.kuaifang.KuaifangNetModelFactory;

/**
 * Disc: 网络数据工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:02
 */
public class NetModelFactory {

    /**
     * 快放视频接口
     * @return
     */
    public KuaifangNetModelFactory kuaifang() {
        return new KuaifangNetModelFactory();
    }

}
