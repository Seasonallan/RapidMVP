package com.season.example.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Disc: 缓存文件工具
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:26
 */
public class FileUtil {


    /**
     * 图片下载的文件夹
     *
     * @return
     */
    public static File getCacheDir(Context context, boolean cache2Sdcard, String dirName) {
        File cacheDir;
        if (cache2Sdcard && Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment
                    .getExternalStorageDirectory().getPath() + "/" + dirName);
        } else {
            cacheDir = context.getCacheDir();
        }
        if (cacheDir == null) {
            cacheDir = new File("data/data/" + context.getPackageName() + "/cache");
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }


    /**
     * 是否存在某文件
     *
     * @param name
     * @return
     */
    public static boolean hasFile(Context context, String name) {
        try {
            context.openFileInput(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void clear(File dir, String name) {
        try {
            // File dir = new File(BaseApplication.SAVE_FILEPATH);
            if (dir.isFile()) {
                dir.delete();
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(dir, name);
            if (f.isFile()) {
                f.delete();
            }
        } catch (Exception e) {

        }
    }

    private static String[] cacheFilter = {};

    private static boolean arrowCache(String key) {
        if (TextUtils.isEmpty(key)) {
            return true;
        }
        for (String str : cacheFilter) {
            if (str.equals(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param name
     * @return void
     * @function 将一个对象保存到本地
     * @author ljp
     * @time 2015年3月19日11:07:50
     */
    public static boolean saveObject(File dir, String name, Object sod) {
        if (!arrowCache(name)) {
            return false;
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            // File file = new File(BaseApplication.SAVE_FILEPATH);
            if (dir.isFile()) {
                dir.delete();
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(dir, name);
            if (f.isFile()) {
                f.delete();
            }
            f.createNewFile();
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(sod);
        } catch (Exception e) {
           // e.printStackTrace();
            return false;
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
        return true;
    }

    /**
     * @param name
     * @return Object
     * @function 从本地读取保存的对象
     * @author ljp
     * @time 2015年3月19日11:07:33
     */
    public static Object getObject(File dir, String name) {
        if (!arrowCache(name)) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            // File f = new File(BaseApplication.SAVE_FILEPATH, name);
            File f = new File(dir, name);
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            //e.printStackTrace();
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
