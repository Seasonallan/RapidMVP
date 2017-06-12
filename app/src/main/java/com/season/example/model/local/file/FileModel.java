package com.season.example.model.local.file;

import com.season.rapiddevelopment.model.BaseLocalModel;
import com.season.rapiddevelopment.tools.FileUtil;

import java.io.File;

/**
 * Disc: 本地文件存储
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 17:31
 */
public class FileModel extends BaseLocalModel {

    public File mFileDir;

    public FileModel(String fileName) {
        super();
        mFileDir = FileUtil.getCacheDir(mContext, false, fileName);
    }

    @Override
    public Object getValueImmediately(String key) {
        return FileUtil.getObject(mFileDir, key);
    }

    @Override
    public boolean setValueImmediately(String key, Object value) {
        return FileUtil.saveObject(mFileDir, key, value);
    }


}
