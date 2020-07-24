package com.season.example.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.season.example.entry.BaseEntry;
import com.season.example.entry.VideoItem;
import com.season.example.entry.VideoList;
import com.season.example.presenter.HomePresenter;
import com.season.example.ui.activity.CommentActivity;
import com.season.example.ui.adapter.HomeAdapter;
import com.season.example.entry.Configure;
import com.season.lib.ui.BaseRecycleAdapter;
import com.season.mvp.ui.IView;
import com.season.rapiddevelopment.R;
import com.season.mvp.presenter.BasePresenter;
import com.season.mvp.ui.BaseTLEFragment;
import com.season.mvp.ui.pulltorefresh.IPull2RefreshAction;
import com.season.mvp.ui.pulltorefresh.IPull2RefreshView;
import com.season.mvp.ui.pulltorefresh.Pull2RefreshImpl;
import com.season.lib.ui.view.refreshview.HeadFootView;

import java.util.List;


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

        getTitleBar().setTopTile("Home");

        mPull2RefreshView = new Pull2RefreshImpl(this) {

            @Override
            public void onRefresh() {
                mPresenter.loadList(BasePresenter.REFRESH);
            }

            @Override
            public void onLoadingMore() {
                mPresenter.loadList(BasePresenter.MORE);
            }
        };

        mPresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    protected HomePresenter attachPresenter(IView view) {
        return new HomePresenter(view);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        if (result instanceof BaseEntry) {
            VideoList videoLists = (VideoList) (((BaseEntry) result).data);
            mPull2RefreshView.onSuccess(type, videoLists.movies, Configure.PAGE_SIZE);
        }
    }


    @Override
    public void onEmptyViewClick() {
        getEmptyView().dismissEmptyView();
        mPresenter.loadList(BasePresenter.CREATE);
    }

    @Override
    public BaseRecycleAdapter initAdapter(List result) {
        mHomeAdapter = new HomeAdapter(getContext(), result) {

            public void onItemClick(VideoItem item) {
                CommentActivity.show(getContext(), item);
            }
        };
        mHomeAdapter.addHeadView(new HeadFootView() {
            @Override
            public void onBindHolder(RecyclerView.ViewHolder holder) {
                HeadViewHolder homeHolder = (HeadViewHolder) holder;
                homeHolder.mNickname.setText("我是名字");
                homeHolder.mTime.setText("我是时间");
                homeHolder.mContent.setText("我是内容");
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View view = mHomeAdapter.inflater.inflate(R.layout.item_comment, parent, false);
                return new HeadViewHolder(view);
            }

            class HeadViewHolder extends RecyclerView.ViewHolder {
                private TextView mNickname;
                private TextView mTime;
                private TextView mContent;
                public ImageView mImageView;

                public HeadViewHolder(View view) {
                    super(view);
                    mImageView = (ImageView) view.findViewById(R.id.comment_iv_head);
                    mNickname = (TextView) view.findViewById(R.id.comment_tv_nickname);
                    mTime = (TextView) view.findViewById(R.id.comment_tv_time);
                    mContent = (TextView) view.findViewById(R.id.comment_tv_content);
                }

            }

        });
        mHomeAdapter.addHeadView(new HeadFootView() {
            @Override
            public void onBindHolder(RecyclerView.ViewHolder holder) {
                HeadViewHolder homeHolder = (HeadViewHolder) holder;
                homeHolder.mNickname.setText("我是名字2");
                homeHolder.mTime.setText("我是时间");
                homeHolder.mContent.setText("我是内容");
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View view = mHomeAdapter.inflater.inflate(R.layout.item_comment, parent, false);
                return new HeadViewHolder(view);
            }

            class HeadViewHolder extends RecyclerView.ViewHolder {
                private TextView mNickname;
                private TextView mTime;
                private TextView mContent;
                public ImageView mImageView;

                public HeadViewHolder(View view) {
                    super(view);
                    mImageView = (ImageView) view.findViewById(R.id.comment_iv_head);
                    mNickname = (TextView) view.findViewById(R.id.comment_tv_nickname);
                    mTime = (TextView) view.findViewById(R.id.comment_tv_time);
                    mContent = (TextView) view.findViewById(R.id.comment_tv_content);
                }

            }

        });
        mHomeAdapter.addHeadView(new HeadFootView() {
            @Override
            public void onBindHolder(RecyclerView.ViewHolder holder) {
                HeadViewHolder homeHolder = (HeadViewHolder) holder;
                homeHolder.mNickname.setText("我是名字3");
                homeHolder.mTime.setText("我是时间");
                homeHolder.mContent.setText("我是内容");
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View view = mHomeAdapter.inflater.inflate(R.layout.item_comment, parent, false);
                return new HeadViewHolder(view);
            }

            class HeadViewHolder extends RecyclerView.ViewHolder {
                private TextView mNickname;
                private TextView mTime;
                private TextView mContent;
                public ImageView mImageView;

                public HeadViewHolder(View view) {
                    super(view);
                    mImageView = (ImageView) view.findViewById(R.id.comment_iv_head);
                    mNickname = (TextView) view.findViewById(R.id.comment_tv_nickname);
                    mTime = (TextView) view.findViewById(R.id.comment_tv_time);
                    mContent = (TextView) view.findViewById(R.id.comment_tv_content);
                }

            }

        });
        mHomeAdapter.addFootView(new HeadFootView() {
            @Override
            public void onBindHolder(RecyclerView.ViewHolder holder) {
                HeadViewHolder homeHolder = (HeadViewHolder) holder;
                homeHolder.mNickname.setText("我是底部名字");
                homeHolder.mTime.setText("我是时间");
                homeHolder.mContent.setText("我是内容");
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View view = mHomeAdapter.inflater.inflate(R.layout.item_comment, parent, false);
                return new HeadViewHolder(view);
            }

            class HeadViewHolder extends RecyclerView.ViewHolder {
                private TextView mNickname;
                private TextView mTime;
                private TextView mContent;
                public ImageView mImageView;

                public HeadViewHolder(View view) {
                    super(view);
                    mImageView = (ImageView) view.findViewById(R.id.comment_iv_head);
                    mNickname = (TextView) view.findViewById(R.id.comment_tv_nickname);
                    mTime = (TextView) view.findViewById(R.id.comment_tv_time);
                    mContent = (TextView) view.findViewById(R.id.comment_tv_content);
                }

            }

        });
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
