package com.season.rapiddevelopment.ui.titlebar;

import android.view.View;

/**
 * Disc: 标题栏回调
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:22
 */
public interface ITitleBarAction {

    /**
     * 查找资源
     * @param id
     * @return
     */
    View findViewById(int id);

    /**
     * 通用返回键点击事件
     */
    void finish();

}
