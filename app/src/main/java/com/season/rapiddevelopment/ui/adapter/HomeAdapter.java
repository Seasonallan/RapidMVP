package com.season.rapiddevelopment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.model.entry.VideoItem;

import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 17:14
 */
public class HomeAdapter extends BaseRecycleAdapter<VideoItem> {

    public HomeAdapter(Context context, List<VideoItem> lists) {
        super(context, lists);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home, parent, false);
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        HomeViewHolder homeHolder = (HomeViewHolder) holder;
        homeHolder.tv.setText(getItem(position).intro);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public HomeViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.item_text);
        }

    }

}
