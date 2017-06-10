package com.season.rapiddevelopment.tools;

import java.security.MessageDigest;
import java.util.UUID;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 获取设备唯一标识工具�?
 * 
 * @author wuwq
 *
 */
public class UniqueIdUtils {
	/**
	 * 获取唯一设备标识
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
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
		return MD5(result.toString());
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取唯一uuid
	 * 
	 * @param context
	 * @return
	 */
	public static String getUUID(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}

	/**
	 * 获取终端型号
	 * 
	 * @param context
	 * @return
	 */
	public static String getTerminalBrand(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获取设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceInfo(Context context) {
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
