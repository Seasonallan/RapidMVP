package com.season.mvp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.season.mvp.R;
import com.season.lib.ui.BaseRecycleAdapter;
import com.season.mvp.ui.empty.EmptyImpl;
import com.season.mvp.ui.empty.IEmptyView;
import com.season.mvp.ui.loading.ILoadingAction;
import com.season.mvp.ui.loading.ILoadingView;
import com.season.mvp.ui.loading.LoadingImpl;
import com.season.mvp.ui.titlebar.ITitleBarAction;
import com.season.mvp.ui.titlebar.TitleBarImpl;
import com.season.mvp.presenter.BasePresenter;
import com.season.mvp.ui.empty.IEmptyAction;
import com.season.mvp.ui.titlebar.ITitleBar;

import static com.season.lib.BaseContext.showToast;

/**
 * Disc:Activity 基类
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 14:37
 */
public abstract class BaseTLEActivity<P extends BasePresenter> extends AppCompatActivity implements ITitleBarAction, ILoadingAction, IEmptyAction, IView {

    protected P mPresenter;
    private ViewGroup mContentView;

    private ITitleBar mTitleBar;
    private ILoadingView mLoadingView;
    private IEmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_tle);

        mPresenter = attachPresenter(this);

        mEmptyView = new EmptyImpl(this);
        mLoadingView = new LoadingImpl(this);
        if (isTopTileEnable()){
            ViewStub viewStub = super.findViewById(R.id.common_top);
            viewStub.inflate();
            mTitleBar = new TitleBarImpl(this);
        }

        mContentView = findViewById(R.id.main_view);
    }

    /**
     * 绑定P
     * @param view
     * @return
     */
    protected P attachPresenter(IView view) {
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.release();
        }
    }

    /**
     * 顶部标题栏是否显示
     * @return
     */
    protected boolean isTopTileEnable(){
        return true;
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mContentView.addView(view, lp);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }


    /**
     * 顶部标题控制栏
     * @return
     */
    public ITitleBar getTitleBar(){
        return mTitleBar;
    }

    @Override
    public IEmptyView getEmptyView() {
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

//**************** Android M Permission (Android 6.0权限控制代码封装)
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable ;
    public interface PermissionCallback{
        void hasPermission();
        void noPermission();
    }

    /**
     * Android M运行时权限请求封装
     * @param permissionDes 权限描述
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes,PermissionCallback runnable,@NonNull String... permissions){
        if(permissions == null || permissions.length == 0)return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)){
            if(permissionRunnable!=null){
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        }else{
            //permission has not been granted.
            requestPermission(permissionDes,permissionRequestCode,permissions);
        }

    }
    private boolean checkPermissionGranted(String[] permissions){
        boolean flag = true;
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                flag = false;
                break;
            }
        }
        return flag;
    }
    private void requestPermission(String permissionDes,final int requestCode,final String[] permissions){
        if(shouldShowRequestPermissionRationale(permissions)){
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseTLEActivity.this, permissions, requestCode);
                        }
                    }).show();

        }else{
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(BaseTLEActivity.this, permissions, requestCode);
        }
    }
    private boolean shouldShowRequestPermissionRationale(String[] permissions){
        boolean flag = false;
        for(String p:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,p)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == permissionRequestCode){
            if(verifyPermissions(grantResults)){
                if(permissionRunnable!=null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            }else{
                showToast("暂无权限执行相关操作！");
                if(permissionRunnable!=null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    //********************** END Android M Permission ****************************************


}
