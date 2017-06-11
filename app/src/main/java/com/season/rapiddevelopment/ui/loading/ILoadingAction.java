package com.season.rapiddevelopment.ui.loading;

import android.view.View;

/**
 * Disc: 加载中回调
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:01
 */
public interface ILoadingAction {

    /**
     * 查找资源
     * @param id
     * @return
     */
    View findViewById(int id);

}
