package com.season.example.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.season.example.entry.ClientKey;
import com.season.example.presenter.CategoryPresenter;
import com.season.example.ui.dialog.LogoutDialog;
import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.BaseFragment;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class CategoryFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    TextView mTextView;

    CategoryPresenter mCategoryPresenter;
    @Override
    protected void onViewCreated() {
        mCategoryPresenter = new CategoryPresenter(this);
        getTitleBar().setTopTile("Category");
        Button btn = (Button) findViewById(R.id.btn_set);
        mTextView = (TextView) findViewById(R.id.tv_result);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryPresenter.getKey();
                new LogoutDialog(getActivity()){
                    @Override
                    protected void onConfirm() {
                        super.onConfirm();
                        BaseApplication.showToast("success");
                    }
                }.show();
            }
        });
    }

    @Override
    public <T> void onResponse(int type, T result) {
        super.onResponse(type, result);
        ClientKey clientKey = (ClientKey) result;
        mTextView.setText(clientKey.toString());
    }

    @Override
    public void onError(int type, String errorMessage) {
        super.onError(type, errorMessage);
        mTextView.setText(errorMessage);
    }
}
