package com.season.rapiddevelopment.ui.view.refreshview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Season on 2017/6/18.
 */

public abstract class HeadFootView {

    public int getType() {
        return getClass().getName().hashCode();
    }

    public abstract void onBindHolder(RecyclerView.ViewHolder holder);

    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);


}
