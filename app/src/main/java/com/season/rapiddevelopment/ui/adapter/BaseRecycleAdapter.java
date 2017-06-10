package com.season.rapiddevelopment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.season.rapiddevelopment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Disc: 带有加载更多的RecyclerView.Adapter
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 17:14
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int NORMAL = 0x00;
    final int NO_MORE = 0x01;
    final int ERROR = 0x02;
    final int AUTO_LOADING = 0x03;
    final int DISABLE = 0x04;

    int mFooterStatus = AUTO_LOADING;
    int FOOTER = 2;

    public boolean isLoadingMore = false;
    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater inflater;

    public BaseRecycleAdapter(Context context, List<T> lists) {
        this.mContext = context;
        this.mList = lists;
        this.inflater = LayoutInflater.from(mContext);
    }

    public boolean canAutoLoadingMore() {
        return mFooterStatus == AUTO_LOADING;
    }

    public boolean canLoadingMore() {
        return mFooterStatus == NORMAL || mFooterStatus == ERROR;
    }

    public void setFooterEnabled(boolean enable, boolean isAuto) {
        if (enable) {
            if (isAuto) {
                mFooterStatus = AUTO_LOADING;
            } else {
                mFooterStatus = NORMAL;
            }
        } else {
            mFooterStatus = DISABLE;
        }
    }

    public void error() {
        mFooterStatus = ERROR;
        notifyDataSetChanged();
    }

    public void noMore() {
        mFooterStatus = NO_MORE;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooterStatus != DISABLE && position >= getItemCount() - 1) {
            return FOOTER;
        }
        return super.getItemViewType(position);
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public void append(List<T> lists) {
        if (mList == null) {
            mList = new ArrayList();
        }
        mList.addAll(lists);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = inflater.inflate(R.layout.pull_to_refresh_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            return onCreateHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFooterView(position)) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            switch (mFooterStatus) {
                case NORMAL:
                    if (isLoadingMore){
                        viewHolder.loading();
                    }else{
                        viewHolder.normal();
                    }
                    break;
                case AUTO_LOADING:
                    viewHolder.loading();
                    break;
                case NO_MORE:
                    viewHolder.noMore();
                    break;
                case ERROR:
                    viewHolder.error();
                    break;
            }
        } else {
            onBindHolder(holder, position);
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    /**
     * 绑定ViewHolder数据
     *
     * @param holder
     * @param position
     */
    public abstract void onBindHolder(RecyclerView.ViewHolder holder, int position);


    public int getCount() {
        return mList.size();
    }

    @Override
    public int getItemCount() {
        if (mFooterStatus != DISABLE) {
            return mList.size() + 1;
        }
        return mList.size();
    }

    private boolean isFooterView(int position) {
        if (mFooterStatus != DISABLE && getItemViewType(position) == FOOTER) {
            return true;
        }
        return false;
    }

    public View.OnClickListener mOnClickListener;

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public final ProgressBar headerProgress;
        public final TextView headerText;

        public FooterViewHolder(View view) {
            super(view);
            headerText = (TextView) view.findViewById(R.id.pull_to_refresh_footer_text);
            headerProgress = (ProgressBar) view
                    .findViewById(R.id.pull_to_refresh_footer_progress);
            if (mOnClickListener != null){
                itemView.setOnClickListener(mOnClickListener);
            }
        }

        public void loading() {
            headerText.setText("加载更多中");
            headerProgress.setVisibility(View.VISIBLE);
        }

        public void noMore() {
            headerText.setText("没有更多");
            headerProgress.setVisibility(View.GONE);
        }

        public void normal() {
            headerText.setText("点击加载");
            headerProgress.setVisibility(View.GONE);
        }

        public void error() {
            headerText.setText("加载错误，点击重试");
            headerProgress.setVisibility(View.GONE);
        }
    }


}
