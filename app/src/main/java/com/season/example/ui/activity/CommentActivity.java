package com.season.example.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.season.example.entry.BaseEntry;
import com.season.example.entry.CommentItem;
import com.season.example.entry.CommentList;
import com.season.example.entry.VideoItem;
import com.season.example.presenter.CommentPresenter;
import com.season.example.ui.adapter.CommentAdapter;
import com.season.example.ui.dialog.CommentDialog;
import com.season.lib.BaseContext;
import com.season.example.entry.Configure;
import com.season.lib.model.ImageModel;
import com.season.lib.support.dimen.ScreenUtils;
import com.season.lib.ui.IView;
import com.season.lib.ui.view.AlignTextView;
import com.season.lib.ui.view.refreshview.HeadFootView;
import com.season.rapiddevelopment.R;
import com.season.lib.presenter.BasePresenter;
import com.season.example.util.Console;
import com.season.lib.ui.BaseRecycleAdapter;
import com.season.lib.ui.BaseTLEActivity;
import com.season.lib.ui.pulltorefresh.IPull2RefreshAction;
import com.season.lib.ui.pulltorefresh.IPull2RefreshView;
import com.season.lib.ui.pulltorefresh.Pull2RefreshImpl;

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
    protected CommentPresenter attachPresenter(IView view) {
        return new CommentPresenter(view);
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
                        BaseContext.showToast(str);
                    }
                }.show();
            }
        });
        findViewById(R.id.videodetail_bottombar_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseContext.showToast("on Share Button Clicked");
            }
        });

        mPresenter.loadList(mVideoItem.vid, BasePresenter.CREATE);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        Console.logNetMessage(Thread.currentThread().getName() + " onResponseUI result=" + result);
        super.onResponse(type, result);
        if (result instanceof BaseEntry){
            CommentList commentList = (CommentList) (((BaseEntry) result).data);
            mPull2RefreshView.onSuccess(type,commentList.comments, Configure.PAGE_SIZE);
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
        mCommentAdapter.addHeadView(new HeadFootView() {
            @Override
            public void onBindHolder(RecyclerView.ViewHolder holder) {
                TopHolder homeHolder = (TopHolder) holder;

                homeHolder.mTitleView.setText(mVideoItem.name);
                homeHolder.mContentView.setText(mVideoItem.intro);
                homeHolder.mContentView.recalculate();

                ImageModel.bindImage2View(homeHolder.mImageView, mVideoItem.cover_url);
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                return new TopHolder(mCommentAdapter.inflater.inflate(R.layout.item_home, null));
            }

            class TopHolder extends RecyclerView.ViewHolder{

                public TopHolder(@NonNull View view) {
                    super(view);
                    mTitleView = view.findViewById(R.id.video_title);
                    mImageView = view.findViewById(R.id.video_image);
                    mContentView = view.findViewById(R.id.video_content);

                    mContentView.setMaxLine(4);
                    View imageContainerView = view.findViewById(R.id.video_image_cont);
                    LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) imageContainerView.getLayoutParams();
                    param.width = (int) (ScreenUtils.getScreenWidth() /3.1f * 1);
                    param.height = (int) (ScreenUtils.getScreenWidth()/3.1f * 3/2);
                    imageContainerView.requestLayout();
                }
                private TextView mTitleView;
                private AlignTextView mContentView;
                public ImageView mImageView;

            }
        });
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
