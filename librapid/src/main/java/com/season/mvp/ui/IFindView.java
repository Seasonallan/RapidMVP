package com.season.mvp.ui;

import android.view.View;

public interface IFindView {

    /**
     * 查找资源
     * @param id
     * @return
     */
    <V extends View> V findViewById(int id);

}
