package com.season.rapiddevelopment;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.season.rapiddevelopment.model.entry.ClientKey;
import com.season.rapiddevelopment.ui.view.ToastView;

import java.io.File;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 14:34
 */
public class BaseApplication extends Application{

    public static Context sContext;
    public static String SAVE_FILEPATH; //数据缓存位置
    public static final String sCacheFileName = "CacheFile";

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SAVE_FILEPATH = getCacheFile(false) + "/" + sCacheFileName;
        ClientKey.initKeyData();
    }

    /** 缓存文件目录 */
    private static String mFilePath = Environment
            .getExternalStorageDirectory().getPath() + "/" + sCacheFileName;
    /**
     * 图片下载的文件夹
     *
     * @return
     */
    public static File getCacheFile(boolean cache2Sdcard) {
        File cacheDir;
        if (cache2Sdcard && android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(mFilePath);
        } else {
            cacheDir = sContext.getCacheDir();
        }
        if(cacheDir == null){
            cacheDir = new File("data/data/"+sContext.getPackageName()+"/cache");
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }


    private static ToastView mToastView;
    public static void showToast(int id, String txt) {
        try {//解决友盟后台出现的错误
            if (mToastView == null) {
                mToastView = new ToastView(sContext);
            }else{

            }
            mToastView.show(id, txt);
        } catch (Exception e) {
        }
    }


}
