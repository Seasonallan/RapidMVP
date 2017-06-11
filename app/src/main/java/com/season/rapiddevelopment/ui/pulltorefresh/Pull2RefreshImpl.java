package com.season.rapiddevelopment.ui.pulltorefresh;

import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshBase;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshListView;

import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-12 02:14
 */
public abstract class Pull2RefreshImpl<T> implements IPull2RefreshView<T> , PullToRefreshBase.OnRefreshListener {

    private IPull2RefreshAction mAction;
    public Pull2RefreshImpl(IPull2RefreshAction action){
        this.mAction = action;
        mPullToRefreshListView = (PullToRefreshListView) mAction.findViewById(R.id.pull_to_refresh_view);
        mPullToRefreshListView.enableAutoLoadingMore();
        mPullToRefreshListView.setOnRefreshListener(this);
    }

    PullToRefreshListView mPullToRefreshListView;

    @Override
    public void onSuccess(int type, List<T> result) {
        mPullToRefreshListView.onRefreshComplete();
        mAction.getEmptyView().dismissEmptyView();
        if (type == BasePresenter.REFRESH || mAction.getAdapter() == null) {
            mPullToRefreshListView.setAdapter(mAction.initAdapter(result));
        } else {
            mAction.getAdapter().append(result);
            mAction.getAdapter().notifyDataSetChanged();
        }
        if ((mAction.getAdapter() == null || mAction.getAdapter().getCount() <= 0) && result == null){
            mAction.getEmptyView().showEmptyView();
        }
        if (result != null && result.size() < Configure.PAGE_SIZE){
            mPullToRefreshListView.noMore();
        }
    }

    @Override
    public void onError() {
        mPullToRefreshListView.onRefreshComplete();
        mPullToRefreshListView.errorLoadingMore();
        if (mAction.getAdapter() == null || mAction.getAdapter().getCount() <= 0){
            mAction.getEmptyView().showEmptyView();
        }
    }
}
