package com.season.example.model.net.kuaifang;

import com.season.example.entry.ClientKey;
import com.season.lib.BaseContext;
import com.season.example.entry.BaseEntry;
import com.season.example.util.PkgManagerUtil;
import com.season.example.util.UniqueIdUtil;

import retrofit2.Callback;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 23:46
 */
public class KuaifangRequestKey extends KuaifangNetModel {

    public KuaifangRequestKey() {
        super();
        mApi = getHttpClient().create(INetRequestKey.class);
    }

    private INetRequestKey mApi;

    public void getClientKey(Callback<BaseEntry<ClientKey>> callback) {
        mApi.getClientKey(UniqueIdUtil.getDeviceId(BaseContext.sContext),
                UniqueIdUtil.getDeviceInfo(BaseContext.sContext),
                PkgManagerUtil.getApkVersionName(BaseContext.sContext))
                .enqueue(callback);
    }

}
