package com.season.rapiddevelopment;

import android.app.Application;
import android.content.Context;

import com.season.rapiddevelopment.ui.view.ToastView;

/**
 * Disc: Application基类
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 14:34
 */
public class BaseApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    private static ToastView mToastView;

    /**
     * 通用弹窗，默认笑脸
     *
     * @param txt 弹窗信息
     */
    public static void showToast(String txt) {
        showToast(R.mipmap.emoticon_cool, txt);
    }

    /**
     * 错误弹窗， 默认悲伤表情
     *
     * @param txt 弹窗信息
     */
    public static void showToastError(String txt) {
        showToast(R.mipmap.emoticon_sad, txt);
    }

    /**
     * 弹窗
     *
     * @param id  图片icon
     * @param txt 弹窗信息
     */
    public static void showToast(int id, String txt) {
        try {//解决友盟后台出现的错误
            if (mToastView == null) {
                mToastView = new ToastView(sContext);
            } else {

            }
            mToastView.show(id, txt);
        } catch (Exception e) {
        }
    }


}
