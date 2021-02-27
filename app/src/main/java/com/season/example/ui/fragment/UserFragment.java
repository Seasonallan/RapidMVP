package com.season.example.ui.fragment;

import android.widget.TextView;

import com.season.example.model.ModelFactory;
import com.season.mvp.ui.BaseTLEFragment;
import com.season.rapiddevelopment.R;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class UserFragment extends BaseTLEFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onViewCreated() {
        getTitleBar().setTopTile("用户中心");

        ((TextView) findViewById(R.id.tv_name)).setText(ModelFactory.local().sharedPreferences().common().getValueImmediately("user").toString());

    }
}
