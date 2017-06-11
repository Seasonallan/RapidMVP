package com.season.example.model.local;

import com.season.rapiddevelopment.model.BaseLocalModel;
import com.season.rapiddevelopment.tools.FileUtil;

import java.io.File;

/**
 * Disc: 本地文件存储
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 17:31
 */
public class FileModel extends BaseLocalModel {

    /**
     * 缓存文件目录
     */
    public static final String sCacheFileName = "CacheFile";
    public File mFileDir;

    public FileModel() {
        super();
        mFileDir = FileUtil.getCacheDir(mContext, false, sCacheFileName);
    }

    @Override
    protected Object getValue(String key) {
        return FileUtil.getObject(mFileDir, key);
    }

    @Override
    protected boolean setValue(String key, Object value) {
        return FileUtil.saveObject(mFileDir, key, value);
    }


}
