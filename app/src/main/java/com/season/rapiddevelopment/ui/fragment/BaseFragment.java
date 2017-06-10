package com.season.rapiddevelopment.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.IView;
import com.season.rapiddevelopment.ui.activity.empty.EmptyImpl;
import com.season.rapiddevelopment.ui.activity.empty.IEmptyAction;
import com.season.rapiddevelopment.ui.activity.empty.IEmptyView;
import com.season.rapiddevelopment.ui.activity.loading.ILoadingAction;
import com.season.rapiddevelopment.ui.activity.loading.ILoadingView;
import com.season.rapiddevelopment.ui.activity.loading.LoadingImpl;
import com.season.rapiddevelopment.ui.activity.titlebar.ITitleBar;
import com.season.rapiddevelopment.ui.activity.titlebar.ITitleBarAction;
import com.season.rapiddevelopment.ui.activity.titlebar.TitleBarImpl;
import com.season.rapiddevelopment.ui.adapter.BaseRecycleAdapter;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:25
 */
public abstract class BaseFragment extends Fragment implements ITitleBarAction, ILoadingAction, IEmptyAction, IView {

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
    @Override
    public ITitleBar getTitleBar(){
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
    @Override
    public ILoadingView getLoadingView(){
        if (mLoadingView == null){
            mLoadingView = new LoadingImpl(this);
        }
        return mLoadingView;
    }

    IEmptyView mEmptyView;

    /**
     * 控制重新加载的显示与消失
     * @return
     */
    @Override
    public IEmptyView getEmptyView(){
        if (mEmptyView == null){
            mEmptyView = new EmptyImpl(this);
        }
        return mEmptyView;
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

    @Override
    public <T> void onResponse(int type, T result) {
    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return null;
    }

    @Override
    public void onEmptyViewClick() {
    }

    @Override
    public void onError(int type, String errorMessage) {
        getLoadingView().dismissLoadingView();
        BaseApplication.showToast(R.mipmap.emoticon_sad, errorMessage);
    }

    protected void startActivity(Class cls){
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
