package com.season.example.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import java.util.UUID;


/**
 * Disc: 手机唯一吗工具
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:26
 */
public class UniqueIdUtil {
	/**
	 * 获取唯一设备标识
	 *
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			return UUID.randomUUID().toString();
		}
		StringBuffer result = new StringBuffer();
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			// imsi
			String imsi = tm.getSubscriberId();
			if (!TextUtils.isEmpty(imsi)) {
				result.append(imsi);
			}
			// imei
			String imei = tm.getDeviceId();
			if (!TextUtils.isEmpty(imei)) {
				result.append(imei);
			}
			// mac
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			String wifiMac = info.getMacAddress();
			if (!TextUtils.isEmpty(wifiMac)) {
				result.append(wifiMac);
			}
		} catch (Exception e) {
		}
		if (result.length() == 0) {
			UUID deviceUuid = UUID.randomUUID();
			result.append(deviceUuid.toString());
		}
		return Crypto.MD5(result.toString());
	}


	/**
	 * 获取设备信息
	 *
	 * @param context
	 * @return
	 */
	public static String getDeviceInfo(Context context) {
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			return android.os.Build.BRAND;
		}
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null) {
			String imei = tm.getDeviceId();
			String imsi = tm.getSubscriberId();
			String phoneType = android.os.Build.MODEL;// 手机型号
			String phoneBrand = android.os.Build.BRAND;// 手机品牌
			String phoneNumber = tm.getLine1Number();// 手机号码，有的可得，有的不可�?

			if (true) {
				return phoneBrand;
			}
			StringBuilder sb = new StringBuilder();

			if (!TextUtils.isEmpty(imei)) {
				sb.append("imei:" + imei);
				sb.append(",");
			}
			if (!TextUtils.isEmpty(imsi)) {
				sb.append("imsi:" + imsi);
				sb.append(",");
			}
			if (!TextUtils.isEmpty(phoneType)) {
				sb.append("phoneType:" + phoneType);
				sb.append(",");
			}
			if (!TextUtils.isEmpty(phoneBrand)) {
				sb.append("phoneBrand:" + phoneBrand);
				sb.append(",");
			}
			if (!TextUtils.isEmpty(phoneNumber)) {
				sb.append("phoneNumber:" + phoneNumber);
				sb.append(",");
			}

			return sb.toString();
		}
		return null;
	}
}
