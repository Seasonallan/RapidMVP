package com.season.rapiddevelopment.model.http;

import com.season.rapiddevelopment.model.entry.BaseEntry;
import com.season.rapiddevelopment.model.entry.ClientKey;
import com.season.rapiddevelopment.model.entry.VideoList;

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


    /**
     * 获取视频信息
     * @param size int 否 10 返回的视频数
     * @param action  int 否 1 ⽤户的动作，1：下拉刷新 2：上拉 加载
     * @param pub_id int 否 0 发布ID
     * @return
     */
    @GET("movie/getlist")
    Call<BaseEntry<VideoList>> getVideo(@Query("size") int size, @Query("action") int action, @Query("pub_id") String pub_id);




}
