package com.season.lib.presenter;

import com.season.lib.ui.IView;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    public class LocalObserver<T> implements Observer<T> {

        public LocalObserver(){

        }

        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(T t) {
            onResponse2UI(t);
        }

        @Override
        public void onError(Throwable e) {
            onError2UI(e.getMessage());
        }

        @Override
        public void onComplete() {
        }
    }

    public class HttpCallback<T> implements Callback<T> {
        int type = -1;
        public HttpCallback(int type) {
            this.type = type;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                T result = response.body();
                if (result != null) {
                    afterResponse(result);
                    onResponse2UI(type, result);
                    return;
                }
            }
            onError2UI(type, "数据错误");
        }

        protected void afterResponse(T result) {

        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            onError2UI(type, "连接失败");
        }
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
