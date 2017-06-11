package com.season.rapiddevelopment.ui.loading;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:04
 */
public interface ILoadingView {

    /**
     * 显示加载中
     */
    void showLoadingView();

    /**
     * 显示加载中
     * @param txt 提示内容
     */
    void showLoadingView(String txt);

    /**
     * 显示加载中， 背景色
     * @param color
     */
    void showLoadingView(int color);

    /**
     * 移除加载中
     */
    void dismissLoadingView();
}
