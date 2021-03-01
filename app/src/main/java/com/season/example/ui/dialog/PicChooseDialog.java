package com.season.example.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.season.mvp.ui.BaseDialog;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 18:30
 */
public class PicChooseDialog extends BaseDialog {

    public PicChooseDialog(Context context) {
        super(context);
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected void onConfirm() {
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = findViewById(com.season.mvp.R.id.dialog_ll_list);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ((TextView) findViewById(com.season.mvp.R.id.dialog_btn_cancel)).setText("本地相册");
        ((TextView) findViewById(com.season.mvp.R.id.dialog_btn_sure)).setText("拍摄");
    }

    @Override
    protected String getTip() {
        return "请选择图片来源";
    }
}
