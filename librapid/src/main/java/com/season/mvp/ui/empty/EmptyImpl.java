package com.season.mvp.ui.empty;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.season.mvp.R;

/**
 * Disc: 加载失败或为空组件
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:00
 */
public class EmptyImpl implements IEmptyView {

    private IEmptyAction mAction;
    private View mEmptyContainerView;
    private ImageView mEmptyIconView;
    private TextView mEmptyDescriptionView;

    public EmptyImpl(IEmptyAction action) {
        this.mAction = action;
    }

    @Override
    public void showEmptyView() {
        checkNull();
        mEmptyContainerView.setVisibility(View.VISIBLE);
    }

    void checkNull(){
        if (mEmptyContainerView == null){
            ViewStub viewStub = mAction.findViewById(R.id.common_empty);
            viewStub.inflate();
            mEmptyContainerView = mAction.findViewById(R.id.empty_container);
            if (mEmptyContainerView != null) {
                mEmptyIconView = (ImageView) mAction.findViewById(R.id.empty_iv);
                mEmptyDescriptionView = (TextView) mAction.findViewById(R.id.empty_tv_desc);
                mEmptyContainerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAction.onEmptyViewClick();
                    }
                });
            } else {
                throw new RuntimeException("please add inc_common_empty.xml to your layout");
            }
        }
    }

    @Override
    public void dismissEmptyView() {
        checkNull();
        mEmptyContainerView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView(String txt) {
        showEmptyView(-1, txt);
    }

    @Override
    public void showEmptyView(int id, String txt) {
        showEmptyView();
        if (id > 0)
            mEmptyIconView.setImageResource(id);
        if (txt != null) {
            mEmptyDescriptionView.setText(txt);
        }
    }

}
