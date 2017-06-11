package com.season.rapiddevelopment.ui;

import com.season.rapiddevelopment.ui.empty.IEmptyView;
import com.season.rapiddevelopment.ui.loading.ILoadingView;
import com.season.rapiddevelopment.ui.titlebar.ITitleBar;

/**
 * Disc: View回调，用于Presenter控制View的状态
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:29
 */
public interface IView {

    /**
     * 获取加载中状态视图组件
     *
     * @return
     */
    ILoadingView getLoadingView();

    /**
     * 获取标题栏组件
     *
     * @return
     */
    ITitleBar getTitleBar();

    /**
     * 获取为空组件
     *
     * @return
     */
    IEmptyView getEmptyView();

    /**
     * 数据操作成功回调
     *
     * @param type
     * @param result
     * @param <T>
     */
    <T> void onResponse(int type, T result);

    /**
     * 获取Adapter，用于ListView中加载更多时获取最后一条数据信息
     *
     * @return 用于ListView，不使用可返回NULL
     */
    BaseRecycleAdapter getAdapter();

    /**
     * 数据操作失败回调
     *
     * @param type
     * @param errorMessage
     */
    void onError(int type, String errorMessage);

}
