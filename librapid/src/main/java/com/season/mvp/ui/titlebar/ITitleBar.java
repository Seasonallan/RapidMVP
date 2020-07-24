package com.season.mvp.ui.titlebar;

import android.view.View;

/**
 * Disc: 通用标题栏组件
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:22
 */
public interface ITitleBar {

    /**
     * 设置标题
     * @param title
     */
    void setTopTile(String title);

    /**
     * 设置标题并配置标题点击事件
     * @param title
     * @param listener
     */
    void setTopTile(String title, View.OnClickListener listener);

    /**
     * 设置返回键可见并自动配置点击销毁当前页面
     */
    void enableLeftButton();

    /**
     * 设置返回键不可见
     */
    void disableLeftView();

    /**
     * 设置顶部右边文字与事件监听器
     * @param text
     * @param listener
     */
    void setTopRightView(String text, View.OnClickListener listener);

    /**
     * 设置顶部右边文字不可见
     */
    void disableRightView();


    /**
     * 设置顶部右侧图片和点击监听器
     * @param drawableId
     * @param listener
     */
    void enableRightImageView(int drawableId, View.OnClickListener listener);

    /**
     * 设置顶部图片文字不可见
     */
    void disableRightImageView();

}
