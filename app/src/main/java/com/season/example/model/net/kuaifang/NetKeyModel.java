package com.season.example.model.net.kuaifang;

import com.season.example.entry.ClientKey;
import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.model.BaseEntry;
import com.season.rapiddevelopment.tools.PkgManagerUtil;
import com.season.rapiddevelopment.tools.UniqueIdUtil;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 23:46
 */
public class NetKeyModel extends KuaifangNetModel {

    public NetKeyModel() {
        super();
    }

    public void getClientKey(Callback<BaseEntry<ClientKey>> callback) {
        INetRequestKey service = mRetrofit.create(INetRequestKey.class);
        Call<BaseEntry<ClientKey>> call = service.getClientKey(UniqueIdUtil.getDeviceId(BaseApplication.sContext),
                UniqueIdUtil.getDeviceInfo(BaseApplication.sContext), PkgManagerUtil.getApkVersionName(BaseApplication.sContext));
        call.enqueue(callback);
    }

}
