package com.season.example.model.net.kuaifang;

import com.season.example.entry.ClientKey;
import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.model.BaseEntry;
import com.season.rapiddevelopment.tools.PkgManagerUtil;
import com.season.rapiddevelopment.tools.UniqueIdUtil;

import retrofit2.Callback;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 23:46
 */
public class NetKeyModel extends KuaifangNetModel {

    public NetKeyModel() {
        super();
        mApi = getHttpClient().create(INetRequestKey.class);
    }

    private INetRequestKey mApi;

    public void getClientKey(Callback<BaseEntry<ClientKey>> callback) {
        mApi.getClientKey(UniqueIdUtil.getDeviceId(BaseApplication.sContext),
                UniqueIdUtil.getDeviceInfo(BaseApplication.sContext), PkgManagerUtil.getApkVersionName(BaseApplication.sContext))
                .enqueue(callback);
    }

}
