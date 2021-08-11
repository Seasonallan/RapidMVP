package com.season.mvp.ui.pulltorefresh;

import com.season.lib.ui.view.refreshview.PullToRefreshBase;
import com.season.lib.ui.view.refreshview.PullToRefreshListView;
import com.season.lib.R;
import com.season.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * Disc: 下拉刷新组件
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-12 02:14
 */
public abstract class Pull2RefreshImpl<T> implements IPull2RefreshView<T> , PullToRefreshBase.OnRefreshListener {


    private PullToRefreshListView mPullToRefreshListView;
    private IPull2RefreshAction mAction;
    public Pull2RefreshImpl(IPull2RefreshAction action){
        this.mAction = action;
        mPullToRefreshListView = (PullToRefreshListView) mAction.findViewById(R.id.pull_to_refresh_view);
        if (mPullToRefreshListView == null){
            throw new RuntimeException("please add PullToRefreshListView to your layout");
        }else{
            //mPullToRefreshListView.enableAutoLoadingMore();
            mPullToRefreshListView.setOnRefreshListener(this);
        }
    }

    @Override
    public void onSuccess(int type, List<T> result, int pageSize) {
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
        if (result != null && result.size() < pageSize){
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
