package com.season.rapiddevelopment.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 14:37
 */
public abstract class BaseActivity extends Activity implements ITitleBarAction {

    ITitleBar mTitleBar;

    /**
     * 顶部标题控制栏
     * @return
     */
    protected ITitleBar getTitleBar(){
        if (mTitleBar == null){
            mTitleBar = new TitleBarImpl(this);
        }
        return mTitleBar;
    }

    //---------------------------键盘控制start---------------------------------------
    /**
     * 弹出输入框键盘
     * @param view
     */
    protected void showSoftInputFromWindow(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 接受软键盘输入的编辑文本或其它视图
        inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭输入法
     */
    protected void hideSoftInputFromWindow() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }




}
