package com.season.rapiddevelopment.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.season.rapiddevelopment.R;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 18:27
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }

    /**
     * 默认返回居中
     *
     * @return
     */
    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected abstract void onConfirm();

    protected abstract String getTip();

    protected void onCancel(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);

        Window dialogWindow = getWindow();

        ColorDrawable dw = new ColorDrawable(0x0000ff00);
        dialogWindow.setBackgroundDrawable(dw);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);

        WindowManager.LayoutParams lay = dialogWindow.getAttributes();
        lay.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 3.5f / 4);
        lay.gravity = getGravity();

        TextView tipView = (TextView) findViewById(R.id.dialog_tv_tip);
        tipView.setText(getTip());

        findViewById(R.id.dialog_btn_cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCancel();
                dismiss();
            }
        });

        findViewById(R.id.dialog_btn_sure).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onConfirm();
            }
        });

    }


}
