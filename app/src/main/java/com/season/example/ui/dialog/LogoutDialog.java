package com.season.example.ui.dialog;

import android.content.Context;
import android.view.Gravity;

import com.season.rapiddevelopment.ui.BaseDialog;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 18:30
 */
public class LogoutDialog extends BaseDialog {

    public LogoutDialog(Context context) {
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
    protected String getTip() {
        return "确定要退出当前帐号吗？";
    }
}
