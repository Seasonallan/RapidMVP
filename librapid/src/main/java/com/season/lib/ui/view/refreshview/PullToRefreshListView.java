package com.season.lib.ui.view.refreshview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.season.lib.ui.BaseRecycleAdapter;
import com.season.lib.R;

/**
 * Disc: 下拉刷新，滑动自动加载更多或点击加载更多
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:22
 */
public class PullToRefreshListView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        recyclerView = new RecyclerView(context, attrs);
        recyclerView.setVerticalScrollBarEnabled(false);
        //scrollView.setVerticalFadingEdgeEnabled(false);
        //scrollView.setScrollbarFadingEnabled(false);
        recyclerView.setId(android.R.id.list);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    private FooterLayout mFooterLayout;

    /**
     * 隐藏底部加载更多
     */
    public void disableFooterView() {
        if (mRecycleAdapter != null && mFooterLayout != null) {
            mRecycleAdapter.removeFootView(mFooterLayout);
        }
    }

    boolean enableAutoLoading;

    /**
     * 启动底部自动加载更多
     */
    public void enableAutoLoadingMore() {
        enableAutoLoading = true;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (mRecycleAdapter == null) {
                        return;
                    }

                    if (mFooterLayout.canAutoLoadingMore()) {
                        if (layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.getItemCount() - 3) {
                            startLoadingMore();
                        }
                    }
                }
            }
        });
    }

    private void startLoadingMore() {
        if (!mFooterLayout.isLoadingMore && onRefreshListener != null) {
            mFooterLayout.isLoadingMore = true;
            mFooterLayout.normal(enableAutoLoading);
            mRecycleAdapter.notifyDataSetChanged();
            onRefreshListener.onLoadingMore();
        }
    }

    /**
     * 加载更多出现错误
     */
    public void errorLoadingMore() {
        if (mRecycleAdapter != null) {
            mFooterLayout.error();
            mRecycleAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 没有更多数据了
     */
    public void noMore() {
        if (mRecycleAdapter != null) {
            mFooterLayout.noMore();
            mRecycleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefreshComplete() {
        super.onRefreshComplete();
        if (mRecycleAdapter != null) {
            mFooterLayout.isLoadingMore = false;
            mRecycleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected boolean isReadyForPullDown() {
        return layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
    }

    BaseRecycleAdapter mRecycleAdapter;

    public void setAdapter(BaseRecycleAdapter adapter) {
        this.mRecycleAdapter = adapter;
        this.mFooterLayout = new FooterLayout(mRecycleAdapter.inflater, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mFooterLayout.canLoadingMore()) {
                    startLoadingMore();
                }
            }
        });
        this.mRecycleAdapter.addFootView(mFooterLayout);
        this.mFooterLayout.setFooterEnabled(enableAutoLoading);
        this.recyclerView.setAdapter(mRecycleAdapter);
    }


    public static class FooterLayout extends HeadFootView {

        @Override
        public int getType() {
            return 1023;
        }

        public static final int NORMAL = 0x00;
        public static final int NO_MORE = 0x01;
        public static final int ERROR = 0x02;
        public static final int AUTO_LOADING = 0x03;

        LayoutInflater inflater;
        View.OnClickListener listener;

        FooterLayout(LayoutInflater inflater, View.OnClickListener listener) {
            this.inflater = inflater;
            this.listener = listener;
        }

        public boolean isLoadingMore = false;
        int mFooterStatus = AUTO_LOADING;

        public boolean canAutoLoadingMore() {
            return mFooterStatus == AUTO_LOADING;
        }

        public boolean canLoadingMore() {
            return mFooterStatus == NORMAL || mFooterStatus == ERROR;
        }

        public void setFooterEnabled(boolean isAuto) {
            if (isAuto) {
                mFooterStatus = AUTO_LOADING;
            } else {
                mFooterStatus = NORMAL;
            }
        }

        public void normal(boolean isAuto) {
            if (isAuto) {
                mFooterStatus = AUTO_LOADING;
            } else {
                mFooterStatus = NORMAL;
            }
        }

        public void error() {
            mFooterStatus = ERROR;
        }

        public void noMore() {
            mFooterStatus = NO_MORE;
        }


        @Override
        public void onBindHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.onBindViewHolder(mFooterStatus, isLoadingMore);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.pull_to_refresh_footer, parent, false);
            return new FooterViewHolder(view, listener);
        }

        class FooterViewHolder extends RecyclerView.ViewHolder {

            public void onBindViewHolder(int status, boolean isLoadingMore) {
                switch (status) {
                    case NORMAL:
                        if (isLoadingMore) {
                            loading();
                        } else {
                            normal();
                        }
                        break;
                    case AUTO_LOADING:
                        loading();
                        break;
                    case NO_MORE:
                        noMore();
                        break;
                    case ERROR:
                        error();
                        break;
                }
            }


            public final ProgressBar headerProgress;
            public final TextView headerText;

            public FooterViewHolder(View view, View.OnClickListener listener) {
                super(view);
                headerText = (TextView) view.findViewById(R.id.pull_to_refresh_footer_text);
                headerProgress = (ProgressBar) view
                        .findViewById(R.id.pull_to_refresh_footer_progress);
                if (listener != null) {
                    itemView.setOnClickListener(listener);
                }
            }

            public void loading() {
                headerText.setText("加载更多中");
                headerProgress.setVisibility(VISIBLE);
            }

            public void noMore() {
                headerText.setText("没有数据");
                headerProgress.setVisibility(GONE);
            }

            public void normal() {
                headerText.setText("点击加载");
                headerProgress.setVisibility(GONE);
            }

            public void error() {
                headerText.setText("加载错误，点击重试");
                headerProgress.setVisibility(GONE);
            }


        }
    }


}
