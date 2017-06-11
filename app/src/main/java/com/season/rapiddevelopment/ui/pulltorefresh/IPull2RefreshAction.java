package com.season.rapiddevelopment.ui.pulltorefresh;

import android.view.View;

import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.empty.IEmptyView;
import com.season.rapiddevelopment.ui.loading.ILoadingView;

import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:22
 */
public interface IPull2RefreshAction<T> {

    View findViewById(int id);

    ILoadingView getLoadingView();

    IEmptyView getEmptyView();

    BaseRecycleAdapter initAdapter(List<T> result);

    BaseRecycleAdapter getAdapter();
}
