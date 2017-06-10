package com.season.rapiddevelopment.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:25
 */
public abstract class BaseFragment extends Fragment implements ITitleBarAction {

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
