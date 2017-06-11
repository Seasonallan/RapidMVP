package com.season.rapiddevelopment.ui.loading;

import android.view.View;
import android.widget.TextView;

import com.season.rapiddevelopment.R;

/**
 * Disc: 加载中
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:00
 */
public class LoadingImpl implements ILoadingView{

    private ILoadingAction mAction;
    public LoadingImpl(ILoadingAction action){
        this.mAction = action;
    }

    private boolean checkLoadingImage() {
        if (mLoadingContainer != null) {
            return true;
        }
        mLoadingContainer = mAction.findViewById(R.id.loading_container);
        if (mLoadingContainer != null) {
            return true;
        }
        return false;
    }

    private View mLoadingContainer;

    @Override
    public void showLoadingView() {
        if (checkLoadingImage()) {
            mLoadingContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadingView(String txt) {
        if (checkLoadingImage()) {
            mLoadingContainer.setVisibility(View.VISIBLE);
            TextView contentView = (TextView) mAction.findViewById(R.id.pull_to_refresh_txt);
            if (contentView != null) {
                contentView.setText(txt);
            }
        }
    }

    @Override
    public void showLoadingView(int color) {
        if (checkLoadingImage()) {
            mLoadingContainer.setBackgroundColor(color);
            mLoadingContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissLoadingView(){
        if (checkLoadingImage()) {
            if (mLoadingContainer.getVisibility() == View.VISIBLE) {
                mLoadingContainer.setVisibility(View.GONE);
            }
        }
    }
}
