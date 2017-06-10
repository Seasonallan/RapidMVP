package com.season.rapiddevelopment.ui.fragment;

import android.os.AsyncTask;
import android.view.View;

import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.model.entry.VideoItem;
import com.season.rapiddevelopment.model.entry.VideoList;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.presenter.HomePresenter;
import com.season.rapiddevelopment.ui.IView;
import com.season.rapiddevelopment.ui.adapter.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.adapter.HomeAdapter;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshBase;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class HomeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    PullToRefreshListView mPullToRefreshListView;
    HomeAdapter mHomeAdapter;

    HomePresenter mHomePresenter;

    @Override
    protected void onViewCreated() {
        mHomePresenter = new HomePresenter(this);
        getTitleBar().setTopTile("Home");

        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_view);
        mPullToRefreshListView.enableAutoLoadingMore();
        mPullToRefreshListView.setOnRefreshListener(this);

        findViewById(R.id.loading_container).setVisibility(View.VISIBLE);

        mHomePresenter.loadList(BasePresenter.CREATE);
    }


    @Override
    public void onRefresh() {
        mHomePresenter.loadList(BasePresenter.REFRESH);
    }

    @Override
    public void onLoadingMore() {
        mHomePresenter.loadList(BasePresenter.MORE);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        if (result instanceof  VideoList){
            VideoList videoLists = (VideoList) result;
            mPullToRefreshListView.onRefreshComplete();
            getEmptyView().dismissEmptyView();
            if (type == BasePresenter.REFRESH || mHomeAdapter == null) {
                mHomeAdapter = new HomeAdapter(getContext(), videoLists.movies);
                mPullToRefreshListView.setAdapter(mHomeAdapter);
            } else {
                mHomeAdapter.append(videoLists.movies);
                mHomeAdapter.notifyDataSetChanged();
            }
            if (mHomeAdapter == null || mHomeAdapter.getCount() <= 0){
                getEmptyView().showEmptyView();
            }else if (videoLists.movies.size() < Configure.PAGE_SIZE){
                mPullToRefreshListView.noMore();
            }
        }
    }


    @Override
    public void onEmptyViewClick() {
        mHomePresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return mHomeAdapter;
    }

    @Override
    public void onError(int type, String errorMessage) {
        super.onError(type, errorMessage);
        mPullToRefreshListView.onRefreshComplete();
        mPullToRefreshListView.errorLoadingMore();
        if (mHomeAdapter == null || mHomeAdapter.getCount() <= 0){
            getEmptyView().showEmptyView();
        }
    }
}
