package com.season.rapiddevelopment.ui.empty;

import android.view.View;

/**
 * Disc: 错误或为空回调
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:22
 */
public interface IEmptyAction {

    /**
     * 查找资源
     * @param id
     * @return
     */
    View findViewById(int id);

    /**
     * 点击重新加载
     */
    void onEmptyViewClick();

}
