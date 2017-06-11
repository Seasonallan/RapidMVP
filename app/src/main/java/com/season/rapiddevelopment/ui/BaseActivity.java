package com.season.rapiddevelopment.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.season.rapiddevelopment.ui.empty.EmptyImpl;
import com.season.rapiddevelopment.ui.empty.IEmptyAction;
import com.season.rapiddevelopment.ui.empty.IEmptyView;
import com.season.rapiddevelopment.ui.loading.ILoadingAction;
import com.season.rapiddevelopment.ui.loading.ILoadingView;
import com.season.rapiddevelopment.ui.loading.LoadingImpl;
import com.season.rapiddevelopment.ui.titlebar.ITitleBar;
import com.season.rapiddevelopment.ui.titlebar.ITitleBarAction;
import com.season.rapiddevelopment.ui.titlebar.TitleBarImpl;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 14:37
 */
public class BaseActivity extends Activity implements ITitleBarAction, ILoadingAction, IEmptyAction, IView {

    ITitleBar mTitleBar;
    ILoadingView mLoadingView;
    IEmptyView mEmptyView;

    /**
     * 顶部标题控制栏
     * @return
     */
    public ITitleBar getTitleBar(){
        if (mTitleBar == null){
            mTitleBar = new TitleBarImpl(this);
        }
        return mTitleBar;
    }

    @Override
    public IEmptyView getEmptyView() {
        if (mEmptyView == null){
            mEmptyView = new EmptyImpl(this);
        }
        return mEmptyView;
    }

    @Override
    public void onEmptyViewClick() {

    }

    @Override
    public <T> void onResponse(int type, T result) {

    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return null;
    }

    @Override
    public void onError(int type, String errorMessage) {

    }

    /**
     * 控制加载中的显示与消失
     * @return
     */
    public ILoadingView getLoadingView(){
        if (mLoadingView == null){
            mLoadingView = new LoadingImpl(this);
        }
        return mLoadingView;
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


    protected Intent getIntent(Class cls){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


}
