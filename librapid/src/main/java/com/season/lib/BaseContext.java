package com.season.lib;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.season.lib.ui.view.ToastView;

/**
 *
 * library 基于的上下文，applicatoin基类，或初始化一次
 *
 * */
public class BaseContext {
	public static Context sContext;
	private static Handler mHandler;

	public static void init(Context context) {
		sContext = context;
		mHandler = new Handler(context.getMainLooper());
	}

	public static Context getInstance(){
		if (sContext == null){
			throw new UnsupportedOperationException("u should init me first");
		}
		return sContext;
	}

	public static DisplayMetrics getDisplayMetrics(){
		if (sContext == null){
			throw new UnsupportedOperationException("u should init me first");
		}
		return sContext.getResources().getDisplayMetrics();
	}

	public static Handler getHandler() {
		if (sContext == null){
			throw new UnsupportedOperationException("u should init me first");
		}
		return mHandler;
	}

	public static void post(Runnable runnable){
		getHandler().post(runnable);
	}


	private static ToastView mToastView;

	/**
	 * 通用弹窗，默认笑脸
	 *
	 * @param txt 弹窗信息
	 */
	public static void showToast(String txt) {
		showToast(R.mipmap.emoticon_cool, txt);
	}

	/**
	 * 错误弹窗， 默认悲伤表情
	 *
	 * @param txt 弹窗信息
	 */
	public static void showToastError(String txt) {
		showToast(R.mipmap.emoticon_sad, txt);
	}

	/**
	 * 弹窗
	 *
	 * @param id  图片icon
	 * @param txt 弹窗信息
	 */
	public static void showToast(int id, String txt) {
		try {//解决友盟后台出现的错误
			if (mToastView == null) {
				mToastView = new ToastView(sContext);
			} else {

			}
			mToastView.show(id, txt);
		} catch (Exception e) {
		}
	}
}
