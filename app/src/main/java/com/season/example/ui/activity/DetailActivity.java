package com.season.example.ui.activity;

import android.os.Bundle;

import com.season.rapiddevelopment.ui.BaseActivity;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 03:40
 */
public class DetailActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getTitleBar().setTopTile("VideoDetail");
        getTitleBar().enableLeftButton();

    }
}
