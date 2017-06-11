package com.season.rapiddevelopment.ui.loading;

import android.view.View;
import android.widget.TextView;

import com.season.rapiddevelopment.R;

/**
 * Disc: 加载中状态组件
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:00
 */
public class LoadingImpl implements ILoadingView {

    private View mLoadingContainer;
    private TextView mLoadingDescriptionView;
    private ILoadingAction mAction;

    public LoadingImpl(ILoadingAction action) {
        this.mAction = action;
        mLoadingContainer = mAction.findViewById(R.id.loading_container);
        if (mLoadingContainer != null) {
            mLoadingDescriptionView = (TextView) mAction.findViewById(R.id.pull_to_refresh_txt);
        } else {
            throw new RuntimeException("please add inc_common_loading.xml to your layout");
        }
    }


    @Override
    public void showLoadingView() {
        if (mLoadingContainer.getVisibility() == View.GONE) {
            mLoadingContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadingView(String txt) {
        showLoadingView();
        mLoadingDescriptionView.setText(txt);
    }

    @Override
    public void showLoadingView(int color) {
        showLoadingView();
        mLoadingContainer.setBackgroundColor(color);
    }

    @Override
    public void dismissLoadingView() {
        if (mLoadingContainer.getVisibility() == View.VISIBLE) {
            mLoadingContainer.setVisibility(View.GONE);
        }
    }
}
