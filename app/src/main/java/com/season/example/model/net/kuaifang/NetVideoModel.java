package com.season.example.model.net.kuaifang;

import com.season.example.entry.VideoList;
import com.season.rapiddevelopment.model.BaseEntry;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 23:46
 */
public class NetVideoModel extends KuaifangNetModel {

    public NetVideoModel() {
        super();
    }

    public void getVideo(int pageSize, int action, String maxId, Callback<BaseEntry<VideoList>> callback) {
        INetRequestVideo service = mRetrofit.create(INetRequestVideo.class);
        Call<BaseEntry<VideoList>> call = service.getVideo(pageSize, action, maxId);
        call.enqueue(callback);
    }
}
