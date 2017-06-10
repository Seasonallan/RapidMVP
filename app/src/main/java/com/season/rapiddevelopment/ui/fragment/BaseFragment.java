package com.season.rapiddevelopment.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.season.rapiddevelopment.ui.activity.loading.ILoadingAction;
import com.season.rapiddevelopment.ui.activity.loading.ILoadingView;
import com.season.rapiddevelopment.ui.activity.loading.LoadingImpl;
import com.season.rapiddevelopment.ui.activity.titlebar.ITitleBar;
import com.season.rapiddevelopment.ui.activity.titlebar.ITitleBarAction;
import com.season.rapiddevelopment.ui.activity.titlebar.TitleBarImpl;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:25
 */
public abstract class BaseFragment extends Fragment implements ITitleBarAction, ILoadingAction {

    // 设计模式 - 模板方法(Template Method)模式
    /**
     * 选择布局
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * View的创建和事件
     */
    protected abstract void onViewCreated();

    ITitleBar mTitleBar;

    /**
     * 顶部标题控制栏
     * @return
     */
    protected ITitleBar getTitleBar(){
        if (mTitleBar == null){
            mTitleBar = new TitleBarImpl(this);
        }
        return mTitleBar;
    }


    ILoadingView mLoadingView;

    /**
     * 控制加载中的显示与消失
     * @return
     */
    protected ILoadingView getLoadingView(){
        if (mLoadingView == null){
            mLoadingView = new LoadingImpl(this);
        }
        return mLoadingView;
    }


    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
            onViewCreated();
        }
        return mView;
    }

    public View findViewById(int id){
        return mView.findViewById(id);
    }

    @Override
    public void finish() {
        getActivity().onBackPressed();
    }

    protected void startActivity(Class cls){
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
