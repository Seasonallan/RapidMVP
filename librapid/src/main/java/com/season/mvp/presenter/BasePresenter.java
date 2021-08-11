package com.season.mvp.presenter;

import com.season.mvp.ui.IView;

import java.lang.ref.WeakReference;

/**
 * Disc: bind the view and model
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 14:37
 */
public class BasePresenter {

    public static final int CREATE = 0x9;
    public static final int REFRESH = 0x10;
    public static final int MORE = 0x11;
    public static final int GET_KEY = 0x12;

    WeakReference<IView> mView;
    public BasePresenter(IView view) {
        this.mView = new WeakReference<>(view);
    }

    public IView getView() {
        return mView.get();
    }

    public void release(){
        //mView = null;
    }

    protected void onError2UI(String errorMessage) {
        onError2UI(-1, errorMessage);
    }

    protected void onError2UI(int type, String errorMessage) {
        if (getView() == null){
            return;
        }
        getView().getLoadingView().dismissLoadingView();
        getView().onError(type, errorMessage);
    }

    protected <T> void onResponse2UI(int type, T result) {
        if (getView() == null){
            return;
        }
        getView().getLoadingView().dismissLoadingView();
        getView().onResponse(type, result);
    }

    protected <T> void onResponse2UI(T result) {
        onResponse2UI(-1, result);
    }


}
