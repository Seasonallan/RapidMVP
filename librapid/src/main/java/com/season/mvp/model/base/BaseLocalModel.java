package com.season.mvp.model.base;

import android.content.Context;

import com.season.lib.BaseContext;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Disc: 本地数据Model基类
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 16:27
 */
public abstract class BaseLocalModel {

    public Context mContext;

    public BaseLocalModel() {
        mContext = BaseContext.sContext;
    }

    /**
     * 直接获取数据
     *
     * @param key
     * @return
     */
    public abstract Object getValueImmediately(String key);

    /**
     * 直接设置数据
     *
     * @param key
     * @param value
     * @return
     */
    public abstract boolean setValueImmediately(String key, Object value);

    /**
     * 使用RxJava响应式获取数据
     *
     * @param fileName
     * @param observer
     * @param <T>
     */
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

    class KeyValue {
        String key;
        Object value;
    }

    /**
     * 使用RxJava响应式设置数据
     *
     * @param fileName
     * @param item
     * @param observer
     */
    public void setValue(String fileName, Object item, Observer<Boolean> observer) {
        KeyValue keyMaps = new KeyValue();
        keyMaps.key = fileName;
        keyMaps.value = item;
        Observable observable = Observable.just(keyMaps)
                .subscribeOn(Schedulers.io())
                .map(new Function<KeyValue, Boolean>() {
                    @Override
                    public Boolean apply(KeyValue s) throws Exception {
                        return setValueImmediately(s.key, s.value);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        if (observer == null) {
            observable.subscribe();
        } else {
            observable.subscribe(observer);
        }

    }


}
