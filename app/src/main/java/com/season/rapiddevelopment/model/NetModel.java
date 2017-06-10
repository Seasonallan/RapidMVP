package com.season.rapiddevelopment.model;

import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.model.entry.BaseEntry;
import com.season.rapiddevelopment.model.entry.ClientKey;
import com.season.rapiddevelopment.model.entry.VideoList;
import com.season.rapiddevelopment.model.http.ClientKeyRequest;
import com.season.rapiddevelopment.tools.PkgManagerUtil;
import com.season.rapiddevelopment.tools.UniqueIdUtils;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 23:46
 */
public class NetModel extends BaseNetModel{

    private static final NetModel sInstance = new NetModel();

    public static NetModel getInstance() {
        return sInstance;
    }

    public void getClientKey(Callback<BaseEntry<ClientKey>> callback) {
        ClientKeyRequest service = mRetrofit.create(ClientKeyRequest.class);
        Call<BaseEntry<ClientKey>> call = service.getClientKey(UniqueIdUtils.getDeviceId(BaseApplication.sContext),
                UniqueIdUtils.getDeviceInfo(BaseApplication.sContext), PkgManagerUtil.getApkVersionName(BaseApplication.sContext));
        call.enqueue(callback);
    }

    public void getVideo(int pageSize, int action, String maxId, Callback<BaseEntry<VideoList>> callback) {
        ClientKeyRequest service = mRetrofit.create(ClientKeyRequest.class);
        Call<BaseEntry<VideoList>> call = service.getVideo(pageSize, action, maxId);
        call.enqueue(callback);
    }
}
