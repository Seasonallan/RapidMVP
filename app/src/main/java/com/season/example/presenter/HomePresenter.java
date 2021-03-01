package com.season.example.presenter;

import com.season.example.entry.Configure;
import com.season.example.model.ModelFactory;
import com.season.example.util.Console;
import com.season.mvp.presenter.BasePresenter;
import com.season.mvp.ui.IView;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class HomePresenter extends BasePresenter {

    public HomePresenter(IView view) {
        super(view);
    }

    protected <T> void onResponse2UI(int type, T result) {
        super.onResponse2UI(type, result);
        if (type == REFRESH) {
            ModelFactory.local().file().commcon().setValue("HomeVideo2.0", result, null);
        }
    }


    int skip = 0;

    public void loadList(final int callType) {
        if (callType == CREATE && false) {
            getView().getLoadingView().showLoadingView();
            Console.logNetMessage("check local cache");
            ModelFactory.local().file().commcon().getValue("HomeVideo2.0",
                    new LocalObserver<List<AVObject>>() {
                        @Override
                        public void onError(Throwable e) {
                            Console.logNetMessage("empty local cache, load from net");
                            loadList(REFRESH);
                        }

                        @Override
                        public void onNext(List<AVObject> o) {
                            Console.logNetMessage("local cache");
                            super.onNext(o);
                        }
                    });
            return;
        }

        int skipCount = skip;
        if (callType == REFRESH) {
            skipCount = 0;
        }

        AVQuery<AVObject> query = new AVQuery<>("QingGoods");
        query.limit(Configure.PAGE_SIZE);
        query.skip(skipCount);
        query.orderByDescending("createdAt");
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVObject> comments) {
                if (callType == REFRESH) {
                    skip = 0;
                }
                skip += comments.size();
                onResponse2UI(callType, comments);
            }

            public void onError(Throwable throwable) {
            }

            public void onComplete() {
            }
        });

    }


}
