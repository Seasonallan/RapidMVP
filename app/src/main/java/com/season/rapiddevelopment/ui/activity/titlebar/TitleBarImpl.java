package com.season.rapiddevelopment.ui.activity.titlebar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.season.rapiddevelopment.R;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 16:19
 */
public class TitleBarImpl implements ITitleBar{

    private ITitleBarAction mAction;
    public TitleBarImpl(ITitleBarAction action){
        this.mAction = action;
    }


    @Override
    public void disableLeftView() {
        View tv = mAction.findViewById(R.id.titlebar_iv_left);
        if (tv != null) {
            tv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void disableRightView() {
        View tv = mAction.findViewById(R.id.titlebar_tv_right);
        if (tv != null) {
            tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void enableRightImageView(int drawableId,  View.OnClickListener listener) {
        View tv = mAction.findViewById(R.id.titlebar_iv_right);
        if (tv != null && tv instanceof ImageView) {
            tv.setVisibility(View.VISIBLE);
            ((ImageView) tv).setImageResource(drawableId);
            if (listener != null){
                tv.setOnClickListener(listener);
            }
        }
    }

    @Override
    public void disableRightImageView() {
        View tv = mAction.findViewById(R.id.titlebar_iv_right);
        if (tv != null && tv instanceof ImageView) {
            tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTopTile(String title){
        setTopTile(title, null);
    }

    @Override
    public void setTopTile(String title, View.OnClickListener listener){
        View titleView = mAction.findViewById(R.id.titlebar_tv_title);
        if (titleView != null && titleView instanceof TextView){
            TextView textView = (TextView) titleView;
            textView.setText(title);
            if (listener != null){
                textView.setOnClickListener(listener);
            }
        }
    }

    @Override
    public void enableLeftButton(){
        View backButton = mAction.findViewById(R.id.titlebar_iv_left);
        if (backButton != null){
            backButton.setVisibility(View.VISIBLE);
            backButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mAction.finish();
                }
            });
        }
    }

    @Override
    public void setTopRightView(String text, View.OnClickListener listener){
        View rightView = mAction.findViewById(R.id.titlebar_iv_right);
        if (rightView != null){
            rightView.setVisibility(View.VISIBLE);
            TextView textView = (TextView) rightView;
            textView.setText(text);
            if (listener != null){
                rightView.setOnClickListener(listener);
            }
        }
    }
}
