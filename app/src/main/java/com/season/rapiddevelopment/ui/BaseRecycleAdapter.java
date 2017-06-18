package com.season.rapiddevelopment.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.season.rapiddevelopment.ui.view.refreshview.HeadFootView;

import java.util.ArrayList;
import java.util.List;

/**
 * Disc: 带有加载更多的RecyclerView.Adapter
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 17:14
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<HeadFootView> mHeadViewLayouts;
    List<HeadFootView> mFootViewLayouts;
    protected List<T> mList;
    protected Context mContext;
    public LayoutInflater inflater;

    public BaseRecycleAdapter(Context context, List<T> lists) {
        this.mContext = context;
        this.mList = lists;
        this.inflater = LayoutInflater.from(mContext);
        this.mHeadViewLayouts = new ArrayList();
        this.mFootViewLayouts = new ArrayList();
    }

    public void addHeadView(HeadFootView headerViewLayout) {
        mHeadViewLayouts.add(headerViewLayout);
        notifyDataSetChanged();
    }


    public void addFootView(HeadFootView footView) {
        mFootViewLayouts.add(0, footView);
        notifyDataSetChanged();
    }

    public void removeFootView(HeadFootView footerView) {
        for (int i = 0; i < mFootViewLayouts.size(); i++) {
            if (mFootViewLayouts.get(i).getType() == footerView.getType()) {
                mFootViewLayouts.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        for (int i = 0; i < mHeadViewLayouts.size(); i++) {
            if (i == position) {
                return mHeadViewLayouts.get(i).getType();
            }
        }

        for (int i = 0; i < mFootViewLayouts.size(); i++) {
            if (getItemCount() - 1 - i == position) {
                return mFootViewLayouts.get(i).getType();
            }
        }

        return super.getItemViewType(position);
    }

    public T getRealItem(int position) {
        return mList.get(position);
    }

    public T getItem(int position) {
        return mList.get(position - mHeadViewLayouts.size());
    }

    public void append(List<T> lists) {
        if (mList == null) {
            mList = new ArrayList();
        }
        mList.addAll(lists);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (int i = 0; i < mHeadViewLayouts.size(); i++) {
            HeadFootView holder = mHeadViewLayouts.get(i);
            if (holder.getType() == viewType) {
                return holder.onCreateHolder(parent, viewType);
            }
        }

        for (int i = 0; i < mFootViewLayouts.size(); i++) {
            HeadFootView holder = mFootViewLayouts.get(i);
            if (holder.getType() == viewType) {
                return holder.onCreateHolder(parent, viewType);
            }
        }
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        for (int i = 0; i < mHeadViewLayouts.size(); i++) {
            HeadFootView headHolder = mHeadViewLayouts.get(i);
            if (headHolder.getType() == viewType) {
                headHolder.onBindHolder(holder);
                return;
            }
        }

        for (int i = 0; i < mFootViewLayouts.size(); i++) {
            HeadFootView headHolder = mFootViewLayouts.get(i);
            if (headHolder.getType() == viewType) {
                headHolder.onBindHolder(holder);
                return;
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onItemClick(getItem(position));
            }
        });
        onBindHolder(holder, position);
    }

    public void onItemClick(T item) {

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
        return mList.size() + mHeadViewLayouts.size() + mFootViewLayouts.size();
    }


}
