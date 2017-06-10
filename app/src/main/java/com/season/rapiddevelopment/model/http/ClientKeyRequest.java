package com.season.rapiddevelopment.model.http;

import com.season.rapiddevelopment.model.entry.BaseEntry;
import com.season.rapiddevelopment.model.entry.ClientKey;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:19
 */
public interface ClientKeyRequest {

    /**
     * 获取网络通讯密钥
     * @return
     */
    @GET("wskey/get")
    Call<BaseEntry<ClientKey>> getClientKey(@Query("imei") String imei, @Query("name") String name, @Query("app_version_name") String app_version_name);

}
