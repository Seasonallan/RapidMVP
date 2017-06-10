package com.season.rapiddevelopment.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.base.BaseFragment;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class SetFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    protected void onViewCreated() {
        getTitleBar().setTopTile("Set");
        Button btn = (Button) findViewById(R.id.btn_set);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.showToast(R.mipmap.emoticon_han, "test");
            }
        });
    }
}
