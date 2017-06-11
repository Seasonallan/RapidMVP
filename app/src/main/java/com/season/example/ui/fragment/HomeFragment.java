package com.season.example.ui.fragment;

import com.season.example.entry.VideoItem;
import com.season.example.entry.VideoList;
import com.season.example.presenter.HomePresenter;
import com.season.example.ui.activity.CommentActivity;
import com.season.example.ui.adapter.HomeAdapter;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.ui.BaseFragment;
import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.pulltorefresh.IPull2RefreshAction;
import com.season.rapiddevelopment.ui.pulltorefresh.IPull2RefreshView;
import com.season.rapiddevelopment.ui.pulltorefresh.Pull2RefreshImpl;

import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class HomeFragment extends BaseFragment implements IPull2RefreshAction {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    IPull2RefreshView mPull2RefreshView;
    HomeAdapter mHomeAdapter;

    HomePresenter mHomePresenter;

    @Override
    protected void onViewCreated() {
        mHomePresenter = new HomePresenter(this);
        getTitleBar().setTopTile("Home");

        mPull2RefreshView = new Pull2RefreshImpl(this) {

            @Override
            public void onRefresh() {
                mHomePresenter.loadList(BasePresenter.REFRESH);
            }

            @Override
            public void onLoadingMore() {
                mHomePresenter.loadList(BasePresenter.MORE);
            }
        };

        mHomePresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        if (result instanceof VideoList) {
            VideoList videoLists = (VideoList) result;
            mPull2RefreshView.onSuccess(type, videoLists.movies);
        }
    }


    @Override
    public void onEmptyViewClick() {
        getEmptyView().dismissEmptyView();
        mHomePresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    public BaseRecycleAdapter initAdapter(List result) {
        mHomeAdapter = new HomeAdapter(getContext(), result) {

            public void onItemClick(VideoItem item) {
                CommentActivity.show(getContext(), item);
            }
        };
        return mHomeAdapter;
    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return mHomeAdapter;
    }

    @Override
    public void onError(int type, String errorMessage) {
        super.onError(type, errorMessage);
        mPull2RefreshView.onError();
    }
}
