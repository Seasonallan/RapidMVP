package com.season.example.model;

import com.season.example.model.local.LocalModelFactory;
import com.season.example.model.net.NetModelFactory;

/**
 * Disc: 使用工厂模式创建MODEL
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 17:31
 */
public class ModelFactory {

    /**
     * 本地数据Model
     * @return
     */
    public static LocalModelFactory local() {
        return new LocalModelFactory();
    }

    /**
     * 网络数据Model
     * @return
     */
    public static NetModelFactory net() {
        return new NetModelFactory();
    }

}
