package com.season.example.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.season.example.entry.Configure;
import com.season.example.entry.VideoItem;
import com.season.example.presenter.HomePresenter;
import com.season.example.ui.activity.CommentActivity;
import com.season.example.ui.activity.GoodsAddActivity;
import com.season.example.ui.adapter.HomeAdapter;
import com.season.lib.ui.BaseRecycleAdapter;
import com.season.example.presenter.SubPresenter;
import com.season.mvp.ui.BaseTLEFragment;
import com.season.mvp.ui.IView;
import com.season.mvp.ui.pulltorefresh.IPull2RefreshAction;
import com.season.mvp.ui.pulltorefresh.IPull2RefreshView;
import com.season.mvp.ui.pulltorefresh.Pull2RefreshImpl;
import com.season.rapiddevelopment.R;

import java.util.List;

import cn.leancloud.AVObject;


/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class HomeFragment extends BaseTLEFragment<HomePresenter> implements IPull2RefreshAction {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    IPull2RefreshView mPull2RefreshView;
    HomeAdapter mHomeAdapter;

    @Override
    protected void onViewCreated() {

        getTitleBar().setTopTile("商品管理");
        getTitleBar().setTopRightView("添加", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), GoodsAddActivity.class));
            }
        });

        mPull2RefreshView = new Pull2RefreshImpl(this) {

            @Override
            public void onRefresh() {
                mPresenter.loadList(SubPresenter.REFRESH);
            }

            @Override
            public void onLoadingMore() {
                mPresenter.loadList(SubPresenter.MORE);
            }
        };

        mPresenter.loadList(SubPresenter.CREATE);
    }

    @Override
    protected HomePresenter attachPresenter(IView view) {
        return new HomePresenter(view);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        if (result instanceof List) {
            List<AVObject> videoLists = (List<AVObject>) result;
            mPull2RefreshView.onSuccess(type, videoLists, Configure.PAGE_SIZE);
        }
    }


    @Override
    public void onEmptyViewClick() {
        getEmptyView().dismissEmptyView();
        mPresenter.loadList(SubPresenter.CREATE);
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
