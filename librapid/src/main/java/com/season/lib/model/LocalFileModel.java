package com.season.lib.model;

import android.os.Environment;
import com.season.lib.model.base.BaseLocalModel;
import com.season.lib.support.file.FileUtils;
import java.io.File;

/**
 * Disc: 本地文件存储
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 17:31
 */
public abstract class LocalFileModel extends BaseLocalModel {

    public abstract File getFileDir();

    @Override
    public Object getValueImmediately(String key) {
        return FileUtils.getSerialData(key, getFileDir());
    }

    @Override
    public boolean setValueImmediately(String key, Object value) {
        return FileUtils.saveSerialData(key, value, getFileDir());
    }

    public File getCacheDir(boolean cache2Sdcard, String dirName) {
        File cacheDir;
        if (cache2Sdcard && Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment
                    .getExternalStorageDirectory().getPath() + "/" + dirName);
        } else {
            cacheDir = mContext.getCacheDir();
        }
        if (cacheDir == null) {
            cacheDir = new File("data/data/" + mContext.getPackageName() + "/cache");
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

}
