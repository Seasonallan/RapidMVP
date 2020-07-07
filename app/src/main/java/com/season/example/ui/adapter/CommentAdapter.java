package com.season.example.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.season.example.entry.CommentItem;
import com.season.rapiddevelopment.R;
import com.season.lib.model.ImageModel;
import com.season.lib.util.TimeUtil;
import com.season.lib.ui.BaseRecycleAdapter;

import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 17:14
 */
public class CommentAdapter extends BaseRecycleAdapter<CommentItem> {

    public CommentAdapter(Context context, List<CommentItem> lists) {
        super(context, lists);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        HomeViewHolder homeHolder = (HomeViewHolder) holder;
        CommentItem item = getItem(position);

        homeHolder.mNickname.setText(item.nickname);
        homeHolder.mTime.setText(TimeUtil.getCategoryTimeMDHM(item.create_time));
        homeHolder.mContent.setText(item.content);

        ImageModel.bindCircleImage2View(homeHolder.mImageView, item.avatar_url);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView mNickname;
        private TextView mTime;
        private TextView mContent;
        public ImageView mImageView;

        public HomeViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.comment_iv_head);
            mNickname = (TextView) view.findViewById(R.id.comment_tv_nickname);
            mTime = (TextView) view.findViewById(R.id.comment_tv_time);
            mContent = (TextView) view.findViewById(R.id.comment_tv_content);
        }

    }

}
