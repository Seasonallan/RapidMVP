package com.season.example.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.season.example.ExampleApplication;
import com.season.example.entry.CommentList;
import com.season.example.entry.VideoItem;
import com.season.example.presenter.CommentPresenter;
import com.season.example.ui.adapter.CommentAdapter;
import com.season.example.ui.adapter.HomeAdapter;
import com.season.example.ui.dialog.CommentDialog;
import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.ui.BaseActivity;
import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshBase;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshListView;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 03:40
 */
public class CommentActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener {

    public static void show(Context context, VideoItem item) {
        Intent intent = new Intent();
        intent.setClass(context, CommentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("VideoItem", item);
        context.startActivity(intent);
    }
    VideoItem mVideoItem;
    CommentPresenter mPresenter;
    PullToRefreshListView mPullToRefreshListView;
    CommentAdapter mCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getTitleBar().setTopTile("VideoDetail");
        getTitleBar().enableLeftButton();

        mVideoItem = (VideoItem) getIntent().getSerializableExtra("VideoItem");
        mPresenter = new CommentPresenter(this, mVideoItem.vid);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_view);
        mPullToRefreshListView.enableAutoLoadingMore();
        mPullToRefreshListView.setOnRefreshListener(this);

        findViewById(R.id.videodetail_bottombar_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommentDialog(CommentActivity.this){
                    @Override
                    public void onCommit(String str) {
                        super.onCommit(str);
                        ExampleApplication.showToast(str);
                    }
                }.show();
            }
        });
        findViewById(R.id.videodetail_bottombar_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleApplication.showToast("on Share Button Clicked");
            }
        });

        mPresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        Console.log(Thread.currentThread().getName() + " onResponseUI result=" + result);
        super.onResponse(type, result);
        if (result instanceof CommentList){
            CommentList commentList = (CommentList) result;
            mPullToRefreshListView.onRefreshComplete();
            getEmptyView().dismissEmptyView();
            if (type == BasePresenter.REFRESH || mCommentAdapter == null) {
                mCommentAdapter = new CommentAdapter(this, commentList.comments){

                    public void onItemClick(CommentList item){
                        //CommentActivity.show(getContext(), item);
                    }
                };
                mPullToRefreshListView.setAdapter(mCommentAdapter);
            } else {
                mCommentAdapter.append(commentList.comments);
                mCommentAdapter.notifyDataSetChanged();
            }
            if ((mCommentAdapter == null || mCommentAdapter.getCount() <= 0) && commentList.comments == null){
                getEmptyView().showEmptyView();
            }
            if (commentList.comments != null && commentList.comments.size() < Configure.PAGE_SIZE){
                mPullToRefreshListView.noMore();
            }

        }
    }


    @Override
    public void onEmptyViewClick() {
        getEmptyView().dismissEmptyView();
        mPresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return mCommentAdapter;
    }

    @Override
    public void onError(int type, String errorMessage) {
        super.onError(type, errorMessage);
        mPullToRefreshListView.onRefreshComplete();
        mPullToRefreshListView.errorLoadingMore();
        if (mCommentAdapter == null || mCommentAdapter.getCount() <= 0){
            getEmptyView().showEmptyView();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.loadList(BasePresenter.REFRESH);
    }

    @Override
    public void onLoadingMore() {
        mPresenter.loadList(BasePresenter.MORE);
    }
}
