package com.season.rapiddevelopment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.model.ImageModel;
import com.season.rapiddevelopment.model.entry.VideoItem;
import com.season.rapiddevelopment.ui.view.AlignTextView;

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
        VideoItem item = getItem(position);
        homeHolder.mTitleView.setText(item.name);
        homeHolder.mContentView.setText(item.intro);
        homeHolder.mContentView.recalculate();

        ImageModel.bindImage2View(homeHolder.mImageView, item.cover_url);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView  mTitleView;
        private AlignTextView mContentView;
        public ImageView mImageView;

        public HomeViewHolder(View view) {
            super(view);
            mTitleView = (TextView) view.findViewById(R.id.video_title);
            mImageView = (ImageView) view.findViewById(R.id.video_image);
            mContentView = (AlignTextView) view.findViewById(R.id.video_content);

            mContentView.setMaxLine(4);
            View imageContainerView = view.findViewById(R.id.video_image_cont);
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) imageContainerView.getLayoutParams();
            param.width = (int) (Configure.getScreenWidth()/3.1f * 1);
            param.height = (int) (Configure.getScreenWidth()/3.1f * 3/2);
            imageContainerView.requestLayout();
        }

    }

}
