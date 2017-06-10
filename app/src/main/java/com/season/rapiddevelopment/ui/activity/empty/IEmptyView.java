package com.season.rapiddevelopment.ui.activity.empty;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:04
 */
public interface IEmptyView {

    /**
     * 显示加载错误
     */
    void showEmptyView();

    void showEmptyView(String description);

    void showEmptyView(int resourceId, String description);

    /**
     * 移除加载错误
     */
    void dismissEmptyView();
}
