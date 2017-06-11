package com.season.rapiddevelopment.model;

import android.content.Context;

import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.tools.Console;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 16:27
 */
public abstract class BaseLocalModel {

    public Context mContext;

    public BaseLocalModel() {
        mContext = BaseApplication.sContext;
    }

    public abstract Object getValueImmediately(String key);

    public abstract boolean setValueImmediately(String key, Object value);

    public <T> void getValue(String fileName, Observer<T> observer) {
        Observable.just(fileName)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, T>() {
                    @Override
                    public T apply(String s) throws Exception {
                        return (T) getValueImmediately(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public class KeyValue {
        String key;
        Object value;
    }

    public void setValue(String fileName, Object item, Observer<Boolean> observer) {
        KeyValue keyMaps = new KeyValue();
        keyMaps.key = fileName;
        keyMaps.value = item;
        Observable observable = Observable.just(keyMaps)
                .subscribeOn(Schedulers.io())
                .map(new Function<KeyValue, Boolean>() {
                    @Override
                    public Boolean apply(KeyValue s) throws Exception {
                        Console.log(Thread.currentThread().getName() + " setValue " + s);
                        return setValueImmediately(s.key, s.value);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        if (observer == null){
            observable.subscribe();
        }else {
            observable.subscribe(observer);
        }

    }


}
