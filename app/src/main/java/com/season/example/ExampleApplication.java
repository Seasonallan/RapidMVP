package com.season.example;

import android.app.Application;

import com.season.lib.BaseContext;

import cn.leancloud.AVOSCloud;

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


        AVOSCloud.initialize(this, "9ubm89lLXP6z7qWSneSPq3VA-gzGzoHsz",
                "L1tIEyrmFs728x8GYMjG0aFy", "https://9ubm89ll.lc-cn-n1-shared.com");

    }
}
