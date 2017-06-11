package com.season.rapiddevelopment.tools;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Disc: 时间显示工具
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 22:26
 */
public class TimeUtil {

	/**
	 * 时间转化yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static String formatCalendarT(String time) {
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		@SuppressWarnings("deprecation")
		Date curDate = new Date(time);
		Calendar calendar = (Calendar) Calendar.getInstance().clone();
		calendar.setTime(curDate);
		Calendar now = (Calendar) Calendar.getInstance().clone();
		if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) 
				&& calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)) {
			SimpleDateFormat formatter = new SimpleDateFormat("今天 HH:mm", Locale.CHINA);
			return formatter.format(curDate);
		}else{
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.CHINA);
			return (calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日 "+formatter.format(curDate);
		}
	}
	

	/**
	 * 显示展示的时间
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String getTimeMDHM(String timeStr) {
		try {
			Long lTime = Long.parseLong(timeStr);
			Date date = new Date(lTime);

			if (date.getYear() != new Date().getYear()) {
				SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				return formatter2.format(date);
			}
			SimpleDateFormat formatter2 = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
			return formatter2.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStr;
	} 
	
	/**
	 * 显示展示的时间
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String getCategoryTimeMDHM(String timeStr) {
		try {
			Long lTime = Long.parseLong(timeStr + "000");
			Date date = new Date(lTime);
			if (date.getYear() != new Date().getYear()) {
				SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				return formatter2.format(date);
			}
			SimpleDateFormat formatter2 = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
			return formatter2.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStr;
	}

	/**
	 * 显示展示的时间
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String getCategoryTimeYMD(String timeStr) {
		try {
			Long lTime = Long.parseLong(timeStr + "000");
			Date date = new Date(lTime);
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
			return formatter2.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStr;
	}
	
}
