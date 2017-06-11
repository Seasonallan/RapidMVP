package com.season.example.model.net.kuaifang;

import com.season.example.entry.VideoList;
import com.season.rapiddevelopment.model.BaseEntry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:19
 */
public interface INetRequestVideo {

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
