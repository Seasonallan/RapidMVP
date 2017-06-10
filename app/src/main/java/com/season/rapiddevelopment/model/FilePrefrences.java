package com.season.rapiddevelopment.model;

import android.text.TextUtils;

import com.season.rapiddevelopment.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
* @ClassName: FilePrefrences 
* @Description: 缓存数据
* @author Allan-Jp 
* @date 2015-6-23 下午1:49:07 
*
 */
public class FilePrefrences {
	
	/**
	 * 是否存在某文件
	 * @param name
	 * @return
	 */
	public static boolean hasFile(String name){
		try {
			BaseApplication.sContext.openFileInput(name);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 在线程中保存
	 * @param name
	 * @param sod
	 */
	public static void saveObjectThread(final String name, final Object sod){
		new Thread(){
			@Override
			public void run() {
				saveObject(name, sod);
			}
		}.start();
	}
	public static void clear(String name) { 
		try {
			File file = new File(BaseApplication.SAVE_FILEPATH);
			if (file.isFile()) {
				file.delete();
			}
			if (!file.exists()) {
				file.mkdirs();
			}
			File f = new File(file, name);
			if (f.isFile()) {
				f.delete();
			}
		}catch(Exception e){
			
		}
	}
	
	private static String[] arrowCache = {"keyData", "user"};
	private static boolean arrowCache(String key){
		if (TextUtils.isEmpty(key)) {
			return false;
		}
		for (String str : arrowCache) {
			if (str.equals(key)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * @function 将一个对象保存到本地
	 * @author ljp
	 * @time 2015年3月19日11:07:50
	 * @param name
	 * @return void
	 */
	public static void saveObject(String name, Object sod) {
		if (!arrowCache(name)) {
			return;
		}
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			File file = new File(BaseApplication.SAVE_FILEPATH);
			if (file.isFile()) {
				file.delete();
			}
			if (!file.exists()) {
				file.mkdirs();
			}
			File f = new File(file, name);
			if (f.isFile()) {
				f.delete();
			}
			f.createNewFile();
			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(sod);
		} catch (Exception e) {
			e.printStackTrace();
			// 这里是保存文件产生异常
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// fos流关闭异常
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// oos流关闭异常
					e.printStackTrace();
				}
			}
		}
	} 

	/**
	 * @function 从本地读取保存的对象
	 * @author ljp
	 * @time 2015年3月19日11:07:33
	 * @param name
	 * @return Object
	 */
	public static Object getObject(String name) {
		if (!arrowCache(name)) {
			return null;
		}
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			File f = new File(BaseApplication.SAVE_FILEPATH, name);
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			// 这里是读取文件产生异常
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// fis流关闭异常
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// ois流关闭异常
					e.printStackTrace();
				}
			}
		}
		// 读取产生异常，返回null
		return null;
	}
}
