package com.season.example;

import android.app.Application;

import com.season.example.entry.ClientKey;
import com.season.example.model.ModelFactory;
import com.season.lib.BaseContext;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 13:12
 */
public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseContext.init(this);

        ModelFactory.local().file().key().getValue("keyData", new Observer<ClientKey>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ClientKey clientKey) {
                ClientKey.saveKeyData(clientKey);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
