package com.season.example.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.season.example.entry.BaseEntry;
import com.season.example.entry.ClientKey;
import com.season.example.presenter.HotPresenter;
import com.season.lib.ui.IView;
import com.season.rapiddevelopment.R;
import com.season.lib.ui.BaseTLEFragment;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class HotFragment extends BaseTLEFragment<HotPresenter> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    TextView mTextView;

    @Override
    protected void onViewCreated() {
        getTitleBar().setTopTile("Set");
        Button btn = (Button) findViewById(R.id.btn_set);
        mTextView = (TextView) findViewById(R.id.tv_result);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getKey();
            }
        });
    }

    @Override
    protected HotPresenter attachPresenter(IView view) {
        return new HotPresenter(view);
    }

    @Override
    public <T> void onResponse(int type, T result) {
        super.onResponse(type, result);
        if (result instanceof BaseEntry){
            ClientKey clientKey = (ClientKey) (((BaseEntry) result).data);
            mTextView.setText(clientKey.toString());
        }
    }
}
