package com.season.rapiddevelopment.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.activity.AboutActivity;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class UserFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onViewCreated() {
        getTitleBar().setTopTile("User");
        Button btn = (Button) findViewById(R.id.btn_set);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AboutActivity.class);
            }
        });
    }
}
