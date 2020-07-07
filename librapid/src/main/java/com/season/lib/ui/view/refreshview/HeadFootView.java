package com.season.lib.ui.view.refreshview;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

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
