package com.season.rapiddevelopment.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Disc: 字符串工具
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:26
 */
public class StringUtil {

	/**
	 * 文件long转化为可显示的M B KB 字符串
	 * @param size
	 * @return
	 */
	public static String getFileSize(long size){
		String sizeStr = "";
		if (size > 0) {
			if (size < 1024) {
				sizeStr = size +" B";
			} else if (size < 1024*1024) {
				sizeStr = new java.text.DecimalFormat("#.0").format(size*1.0f/1024) +" KB";
			} else {
				sizeStr = new java.text.DecimalFormat("#.0").format(size*1.0f/1024/1024) +" MB";
			}
		}
		return sizeStr;
	}

	/**
	 * 检测字符串是否是邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 获取字符串长度 ，如果含中文字符，则每个中文字符长度为2，否则为1
	 * @param str
	 * @return
	 */
	public static int getStringLength(String str){
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 获取字符串某个字符位置 ，如果含中文字符，则每个中文字符长度为2，否则为1
	 * @param str
	 * @return
	 */
	public static int getStringLengthPosition(String str, int position){
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < str.length(); i++) {
			// 获取一个字符
			String temp = str.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 2;
			} else {
				// 其他字符长度为0.5
				valueLength += 1;
			}

			if (valueLength >= position) {
				return i;
			}
		}
		return -1;
	}
}
