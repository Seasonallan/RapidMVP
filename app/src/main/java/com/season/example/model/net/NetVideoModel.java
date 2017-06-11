package com.season.example.model.net;

import com.season.example.entry.VideoList;
import com.season.rapiddevelopment.model.BaseEntry;
import com.season.rapiddevelopment.model.BaseNetModel;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 23:46
 */
public class NetVideoModel extends BaseNetModel {

    public NetVideoModel() {
        super();
    }

    public void getVideo(int pageSize, int action, String maxId, Callback<BaseEntry<VideoList>> callback) {
        INetRequest service = mRetrofit.create(INetRequest.class);
        Call<BaseEntry<VideoList>> call = service.getVideo(pageSize, action, maxId);
        call.enqueue(callback);
    }
}
