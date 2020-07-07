package com.season.lib.ui.titlebar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.season.lib.R;

/**
 * Disc: 通用标题栏组件
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:19
 */
public class TitleBarImpl implements ITitleBar {

    private ITitleBarAction mAction;

    private TextView mTopTitleView, mTopRightTextView;
    private View mTopLeftView;
    private ImageView mTopRightImageView;

    public TitleBarImpl(ITitleBarAction action) {
        this.mAction = action;
        mTopTitleView = (TextView) mAction.findViewById(R.id.titlebar_tv_title);
        if (mTopTitleView == null) {
            throw new RuntimeException("please add inc_common_titlebar.xml to your layout");
        } else {

            mTopLeftView = mAction.findViewById(R.id.titlebar_iv_left);
            mTopRightTextView = (TextView) mAction.findViewById(R.id.titlebar_tv_right);
            mTopRightImageView = (ImageView) mAction.findViewById(R.id.titlebar_iv_right);

        }
    }


    @Override
    public void disableLeftView() {
        if (mTopLeftView.getVisibility() == View.VISIBLE) {
            mTopLeftView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void disableRightView() {
        if (mTopRightTextView.getVisibility() == View.VISIBLE) {
            mTopRightTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void enableRightImageView(int drawableId, View.OnClickListener listener) {
        mTopRightImageView.setVisibility(View.VISIBLE);
        mTopRightImageView.setImageResource(drawableId);
        if (listener != null) {
            mTopRightImageView.setOnClickListener(listener);
        }
    }

    @Override
    public void disableRightImageView() {
        if (mTopRightImageView.getVisibility() == View.VISIBLE) {
            mTopRightImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTopTile(String title) {
        setTopTile(title, null);
    }

    @Override
    public void setTopTile(String title, View.OnClickListener listener) {
        mTopTitleView.setText(title);
        if (listener != null) {
            mTopTitleView.setOnClickListener(listener);
        }
    }

    @Override
    public void enableLeftButton() {
        mTopLeftView.setVisibility(View.VISIBLE);
        mTopLeftView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAction.finish();
            }
        });
    }

    @Override
    public void setTopRightView(String text, View.OnClickListener listener) {
        mTopRightTextView.setVisibility(View.VISIBLE);
        mTopRightTextView.setText(text);
        if (listener != null) {
            mTopRightTextView.setOnClickListener(listener);
        }
    }
}
