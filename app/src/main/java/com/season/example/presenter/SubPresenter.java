package com.season.example.presenter;

import com.season.mvp.presenter.BasePresenter;
import com.season.mvp.ui.IView;

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
public class SubPresenter extends BasePresenter {


    public SubPresenter(IView view) {
        super(view);
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



}
