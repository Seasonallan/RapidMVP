package com.season.rapiddevelopment.ui.loading;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.titlebar.TitleBarImpl;

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
    }

    @Override
    public void showLoadingView() {
        checkNull();
        mLoadingContainer.setVisibility(View.VISIBLE);
    }

    void checkNull(){
        if (mLoadingContainer == null){
            ViewStub viewStub = mAction.findViewById(R.id.common_loading);
            viewStub.inflate();
            mLoadingContainer = mAction.findViewById(R.id.loading_container);
            if (mLoadingContainer != null) {
                mLoadingDescriptionView = mAction.findViewById(R.id.pull_to_refresh_txt);
            } else {
                throw new RuntimeException("please add inc_common_loading.xml to your layout");
            }
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
        checkNull();
        mLoadingContainer.setVisibility(View.GONE);
    }
}
