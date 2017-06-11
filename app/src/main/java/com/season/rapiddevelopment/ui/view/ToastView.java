package com.season.rapiddevelopment.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.season.rapiddevelopment.R;

/**
 * Disc: 提示弹窗试图
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:15
 */
public class ToastView {

    private Toast mToast;
    private View mView;
    private TextView mToastContentView;
    private ImageView mToastImageView;
    private ProgressBar mToastProgressbar;

    @SuppressLint("InflateParams")
    public ToastView(Context context) {
        mToast = new Toast(context);
        mView = LayoutInflater.from(context).inflate(R.layout.inc_common_toast, null);

        mToastContentView = (TextView) mView.findViewById(R.id.toast_tv_desc);
        mToastImageView = (ImageView) mView.findViewById(R.id.toast_iv);
        mToastProgressbar = (ProgressBar) mView.findViewById(R.id.toast_pb);

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(mView);
    }


    /**
     * 显示提示弹窗
     * @param id
     * @param txt
     */
    public void show(int id, String txt) {
        mView.setVisibility(View.VISIBLE);
        mToastImageView.setVisibility(View.VISIBLE);
        mToastProgressbar.setVisibility(View.GONE);
        mToastImageView.setImageResource(id);
        mToastContentView.setText(txt);

        mToast.show();
    }

}
