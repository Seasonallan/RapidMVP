package com.season.example.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.season.example.ExampleApplication;
import com.season.example.entry.CommentItem;
import com.season.example.entry.CommentList;
import com.season.example.entry.VideoItem;
import com.season.example.presenter.CommentPresenter;
import com.season.example.ui.adapter.CommentAdapter;
import com.season.example.ui.dagger.FragmentComponent;
import com.season.example.ui.dialog.CommentDialog;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.BaseTLEActivity;
import com.season.rapiddevelopment.ui.pulltorefresh.IPull2RefreshAction;
import com.season.rapiddevelopment.ui.pulltorefresh.IPull2RefreshView;
import com.season.rapiddevelopment.ui.pulltorefresh.Pull2RefreshImpl;

import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 03:40
 */
public class CommentActivity extends BaseTLEActivity<CommentPresenter> implements IPull2RefreshAction<CommentItem> {

    public static void show(Context context, VideoItem item) {
        Intent intent = new Intent();
        intent.setClass(context, CommentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("VideoItem", item);
        context.startActivity(intent);
    }

    VideoItem mVideoItem;
    CommentAdapter mCommentAdapter;
    IPull2RefreshView mPull2RefreshView;

    @Override
    protected void inject(FragmentComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getTitleBar().setTopTile("VideoDetail");
        getTitleBar().enableLeftButton();

        mVideoItem = (VideoItem) getIntent().getSerializableExtra("VideoItem");
        mPull2RefreshView = new Pull2RefreshImpl(this){
            @Override
            public void onRefresh() {
                mPresenter.loadList(mVideoItem.vid, BasePresenter.REFRESH);
            }

            @Override
            public void onLoadingMore() {
                mPresenter.loadList(mVideoItem.vid, BasePresenter.MORE);
            }
        };

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

        mPresenter.loadList(mVideoItem.vid, BasePresenter.CREATE);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        Console.logNetMessage(Thread.currentThread().getName() + " onResponseUI result=" + result);
        super.onResponse(type, result);
        if (result instanceof CommentList){
            CommentList commentList = (CommentList) result;
            mPull2RefreshView.onSuccess(type,commentList.comments);
        }
    }


    @Override
    public void onEmptyViewClick() {
        getEmptyView().dismissEmptyView();
        mPresenter.loadList(mVideoItem.vid, BasePresenter.CREATE);
    }

    @Override
    public BaseRecycleAdapter initAdapter(List<CommentItem> result) {
        mCommentAdapter = new CommentAdapter(CommentActivity.this, result){

            public void onItemClick(CommentList item){
                //CommentActivity.show(getContext(), item);
            }
        };
        return mCommentAdapter;
    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return mCommentAdapter;
    }


    @Override
    public void onError(int type, String errorMessage) {
        super.onError(type, errorMessage);
        mPull2RefreshView.onError();
    }
}
