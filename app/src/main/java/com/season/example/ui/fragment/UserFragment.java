package com.season.example.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.season.example.ui.activity.AboutActivity;
import com.season.rapiddevelopment.R;
import com.season.mvp.ui.BaseTLEFragment;

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
