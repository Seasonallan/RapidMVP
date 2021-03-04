package com.season.example.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.season.example.entry.VideoItem;
import com.season.example.ui.activity.GoodsAddActivity;
import com.season.example.ui.activity.WebActivity;
import com.season.lib.support.dimen.ScreenUtils;
import com.season.lib.ui.BaseRecycleAdapter;
import com.season.mvp.model.ImageModel;
import com.season.rapiddevelopment.R;

import java.util.List;

import cn.leancloud.AVObject;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 17:14
 */
public class HomeAdapter extends BaseRecycleAdapter<AVObject> {

    public HomeAdapter(Context context, List<AVObject> lists) {
        super(context, lists);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        HomeViewHolder homeHolder = (HomeViewHolder) holder;
        final AVObject item = getItem(position);
        homeHolder.mTitleView.setText(item.getString("title"));
        homeHolder.mContentView.setText(item.getString("price") + "元");

        View imageContainerView = homeHolder.mImageView;
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) imageContainerView.getLayoutParams();
        //param.width = (int) (ScreenUtils.getScreenWidth() /4.1f * 2.5f/2);
        param.height = (int) (ScreenUtils.getScreenWidth()/4.1f * 3/2);
        imageContainerView.requestLayout();
        ImageModel.bindImage2View(homeHolder.mImageView, item.getString("image"));

        homeHolder.preViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.open(mContext, "https://vlifer.applinzi.com/qing/mall/home.php?id=" + item.getObjectId());
            }
        });

        homeHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsAddActivity.open(mContext, item);
            }
        });
    }



    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleView;
        public TextView mContentView;
        public ImageView mImageView;
        public View preViewButton, editButton;

        public HomeViewHolder(View view) {
            super(view);
            mTitleView = view.findViewById(R.id.video_title);
            mImageView = view.findViewById(R.id.video_image);
            mContentView = view.findViewById(R.id.video_content);
            preViewButton = view.findViewById(R.id.main_btn_preview);
            editButton = view.findViewById(R.id.main_btn_edit);

        }

    }

}
