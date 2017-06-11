package com.season.rapiddevelopment.ui.empty;

/**
 * Disc: 加载失败或为空组件
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:04
 */
public interface IEmptyView {

    /**
     * 显示加载错误
     */
    void showEmptyView();

    /**
     * 显示加载错误
     * @param description 描述
     */
    void showEmptyView(String description);

    /**
     * 显示加载错误
     * @param resourceId 图片资源
     * @param description 描述
     */
    void showEmptyView(int resourceId, String description);

    /**
     * 移除加载错误
     */
    void dismissEmptyView();
}
