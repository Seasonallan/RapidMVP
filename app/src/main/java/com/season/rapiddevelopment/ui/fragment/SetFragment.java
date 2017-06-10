package com.season.rapiddevelopment.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.model.BaseNetModel;
import com.season.rapiddevelopment.model.FilePrefrences;
import com.season.rapiddevelopment.model.NetModel;
import com.season.rapiddevelopment.model.entry.BaseEntry;
import com.season.rapiddevelopment.model.entry.ClientKey;
import com.season.rapiddevelopment.presenter.SetPresenter;
import com.season.rapiddevelopment.tools.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    TextView mTextView;

    SetPresenter mSetPresenter;
    @Override
    protected void onViewCreated() {
        mSetPresenter = new SetPresenter(this);
        getTitleBar().setTopTile("Set");
        Button btn = (Button) findViewById(R.id.btn_set);
        mTextView = (TextView) findViewById(R.id.tv_result);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPresenter.getKey();
            }
        });
    }

    @Override
    public <T> void onResponse(int type, T result) {
        super.onResponse(type, result);
        ClientKey clientKey = (ClientKey) result;
        mTextView.setText(clientKey.toString());
    }
}
