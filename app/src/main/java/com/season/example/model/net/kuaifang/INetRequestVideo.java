package com.season.example.model.net.kuaifang;

import com.season.example.entry.CommentList;
import com.season.example.entry.VideoList;
import com.season.rapiddevelopment.model.BaseEntry;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:19
 */
public interface INetRequestVideo {

    /**
     * 获取视频信息
     *
     * @param size   int 否 10 返回的视频数
     * @param action int 否 1 ⽤户的动作，1：下拉刷新 2：上拉 加载
     * @param pub_id int 否 0 发布ID
     * @return
     */
    @GET("movie/getlist")
    Call<BaseEntry<VideoList>> getVideo(@Query("size") int size, @Query("action") int action, @Query("pub_id") String pub_id);


    /**
     * 获取视频评论
     *
     * @param size
     * @param comment_id
     * @param vid
     * @return
     */
    @GET("comment/getlist")
    Call<BaseEntry<CommentList>> getComment(@Query("size") int size, @Query("comment_id") String comment_id, @Query("vid") String vid);


    /**
     * 发表评论
     *
     * @param vid
     * @param content
     * @param vid
     * @return
     */
    @POST("comment/post")
    Call<BaseEntry<String>> sentComment(@Field("vid") String vid, @Field("content") String content, @Field("from_type") String from_type);


}
