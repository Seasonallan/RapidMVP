package com.season.rapiddevelopment.presenter;

import com.season.rapiddevelopment.model.entry.BaseEntry;
import com.season.rapiddevelopment.ui.IView;

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

    BasePresenter(IView view) {
        this.mView = view;
    }

    public IView getView() {
        return mView;
    }

    class HttpCallback<T> implements Callback<BaseEntry<T>> {

        int type = -1;

        HttpCallback(int type) {
            this.type = type;
        }

        @Override
        public void onResponse(Call<BaseEntry<T>> call, Response<BaseEntry<T>> response) {
            getView().getLoadingView().dismissLoadingView();
            if (response.isSuccessful()) {
                T result = response.body().data;
                if (result != null) {
                    afterResponse(result);
                    getView().onResponse(type, result);
                    return;
                }
            }
            getView().onError(type, "数据错误");
        }

        protected void afterResponse(T result) {

        }

        @Override
        public void onFailure(Call<BaseEntry<T>> call, Throwable t) {
            getView().onError(type, "连接失败");
        }
    }


}
