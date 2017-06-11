package com.season.rapiddevelopment.ui;

import com.season.rapiddevelopment.ui.empty.IEmptyView;
import com.season.rapiddevelopment.ui.loading.ILoadingView;
import com.season.rapiddevelopment.ui.titlebar.ITitleBar;

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
