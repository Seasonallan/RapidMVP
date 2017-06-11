package com.season.example.model;

import com.season.example.model.local.FileModel;
import com.season.example.model.local.PrefrenceModel;
import com.season.example.model.net.NetKeyModel;
import com.season.example.model.net.NetVideoModel;
import com.season.rapiddevelopment.model.BaseLocalModel;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 17:31
 */
public class ModelFactory {

    public static LocalModelBuilder local() {
        return new LocalModelBuilder();
    }

    public static NetModelBuilder net() {
        return new NetModelBuilder();
    }

    public static class LocalModelBuilder {
        public BaseLocalModel prefrence() {
            return new PrefrenceModel();
        }

        public BaseLocalModel file() {
            return new FileModel();
        }

    }


    public static class NetModelBuilder {
        public NetVideoModel video() {
            return new NetVideoModel();
        }
        public NetKeyModel key() {
            return new NetKeyModel();
        }
    }

}
