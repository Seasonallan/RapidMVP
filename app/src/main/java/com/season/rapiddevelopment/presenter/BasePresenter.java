package com.season.rapiddevelopment.presenter;

import com.season.rapiddevelopment.model.BaseEntry;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.ui.IView;

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

    IView mView;

    public BasePresenter(IView view) {
        this.mView = view;
    }

    public IView getView() {
        return mView;
    }


    public class LocalObserver<T> implements Observer<T> {
        @Override
        public void onSubscribe(Disposable d) {
            Console.log(Thread.currentThread().getName() + " onSubscribe ");
        }

        @Override
        public void onNext(T t) {
            Console.log(Thread.currentThread().getName() + " onNext ");
            onResponse2UI(t);
        }

        @Override
        public void onError(Throwable e) {
            Console.log(Thread.currentThread().getName() + " onError ");
            onError2UI(e.getMessage());
        }

        @Override
        public void onComplete() {
            Console.log(Thread.currentThread().getName() + " onComplete ");
        }
    }

    public class HttpCallback<T> implements Callback<BaseEntry<T>> {

        int type = -1;

        public HttpCallback(int type) {
            this.type = type;
        }

        @Override
        public void onResponse(Call<BaseEntry<T>> call, Response<BaseEntry<T>> response) {
            if (response.isSuccessful()) {
                T result = response.body().data;
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
        public void onFailure(Call<BaseEntry<T>> call, Throwable t) {
            onError2UI(type, "连接失败");
        }
    }

    protected void onError2UI(String errorMessage) {
        onError2UI(-1, errorMessage);
    }

    protected void onError2UI(int type, String errorMessage) {
        getView().getLoadingView().dismissLoadingView();
        getView().onError(type, errorMessage);
    }

    protected <T> void onResponse2UI(int type, T result) {
        getView().getLoadingView().dismissLoadingView();
        getView().onResponse(type, result);
    }

    protected <T> void onResponse2UI(T result) {
        onResponse2UI(-1, result);
    }


}
