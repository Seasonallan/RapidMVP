package com.season.rapiddevelopment.ui.empty;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.season.rapiddevelopment.R;

/**
 * Disc: 加载中
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:00
 */
public class EmptyImpl implements IEmptyView {

    private IEmptyAction mAction;
    public EmptyImpl(IEmptyAction action){
        this.mAction = action;
    }

    private boolean checkLoadingImage() {
        if (mEmptyContainerView != null) {
            return true;
        }
        mEmptyContainerView = mAction.findViewById(R.id.empty_container);
        if (mEmptyContainerView != null) {
            mEmptyContainerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAction.onEmptyViewClick();
                }
            });
            return true;
        }
        return false;
    }

    private View mEmptyContainerView;

    @Override
    public void showEmptyView() {
        if (checkLoadingImage()) {
            if (mEmptyContainerView.getVisibility() == View.GONE) {
                mEmptyContainerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showEmptyView(String txt) {
        showEmptyView(-1, txt);
    }

    @Override
    public void showEmptyView(int id, String txt) {
        if (checkLoadingImage()) {
            mEmptyContainerView.setVisibility(View.VISIBLE);
            ImageView imageView = (ImageView) mAction.findViewById(R.id.empty_iv);
            if (id > 0 && imageView != null)
                imageView.setImageResource(id);
            TextView contentView = (TextView) mAction.findViewById(R.id.empty_tv_desc);
            if (contentView != null && txt != null) {
                contentView.setText(txt);
            }
        }
    }


    @Override
    public void dismissEmptyView(){
        if (checkLoadingImage()) {
            if (mEmptyContainerView.getVisibility() == View.VISIBLE) {
                mEmptyContainerView.setVisibility(View.GONE);
            }
        }
    }
}
