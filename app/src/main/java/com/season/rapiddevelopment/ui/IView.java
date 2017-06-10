package com.season.rapiddevelopment.ui;

import com.season.rapiddevelopment.ui.activity.empty.IEmptyView;
import com.season.rapiddevelopment.ui.activity.loading.ILoadingView;
import com.season.rapiddevelopment.ui.activity.titlebar.ITitleBar;
import com.season.rapiddevelopment.ui.adapter.BaseRecycleAdapter;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:29
 */
public interface IView{

    ILoadingView getLoadingView();

    ITitleBar getTitleBar();

    IEmptyView getEmptyView();

    <T> void onResponse(int type, T result);

    BaseRecycleAdapter getAdapter();

    void onError(int type, String errorMessage);

}
