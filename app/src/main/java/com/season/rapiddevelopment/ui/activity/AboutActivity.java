package com.season.rapiddevelopment.ui.activity;

import android.os.Bundle;

import com.season.rapiddevelopment.R;

/**
 * Disc: 关于页面
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:00
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        getTitleBar().setTopTile("About");
        getTitleBar().enableLeftButton();
    }
}
