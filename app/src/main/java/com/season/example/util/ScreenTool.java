package com.season.example.util;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenTool {

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        int device = NotchScreenUtil.getDeviceBrand();
        if (device == NotchScreenUtil.DEVICE_BRAND_HUAWEI) {
            statusBarHeight = NotchScreenUtil.getNotchSizeAtHuawei(context);
        } else if (device == NotchScreenUtil.DEVICE_BRAND_OPPO) {
            statusBarHeight = NotchScreenUtil.getNotchSizeAtOppo();
        } else if (device == NotchScreenUtil.DEVICE_BRAND_VIVO) {
            statusBarHeight = NotchScreenUtil.getNotchSizeAtVivo(context);
        }
        return statusBarHeight;
    }

    /**
     * 获取底部菜单的高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * 设置状态栏全透明
     */
    public static void setTransparent(AppCompatActivity appCompatActivity, boolean transparentBar, int navigationColor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
            appCompatActivity.getWindow().setNavigationBarColor(appCompatActivity.getResources().getColor(navigationColor));

            if (transparentBar) {
                appCompatActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                appCompatActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            //需要设置这个flag contentView才能延伸到状态栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (transparentBar) {
            appCompatActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//刘海
            WindowManager.LayoutParams lp = appCompatActivity.getWindow().getAttributes();
            //lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            //lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            appCompatActivity.getWindow().setAttributes(lp);
        }
    }

}
