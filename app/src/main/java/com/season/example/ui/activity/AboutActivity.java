package com.season.example.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.season.example.model.ModelFactory;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.BaseActivity;

/**
 * Disc: 关于页面
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:00
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getTitleBar().setTopTile("About");
        getTitleBar().enableLeftButton();

        TextView textView = (TextView) findViewById(R.id.about_tv_name);
        Object testItem = ModelFactory.local().sharedPreferences().getValueImmediately("testx");
        textView.setText("get=" + testItem);
    }
}
