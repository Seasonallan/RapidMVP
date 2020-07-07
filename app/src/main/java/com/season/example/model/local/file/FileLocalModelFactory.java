package com.season.example.model.local.file;

import com.season.lib.model.LocalFileModel;
import com.season.lib.model.base.BaseLocalModel;
import java.io.File;

/**
 * Disc: 文件工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class FileLocalModelFactory {

    public BaseLocalModel key() {
        return new LocalFileModel() {
            @Override
            public File getFileDir() {
                return getCacheDir(false, "key");
            }
        };
    }

    public BaseLocalModel commcon() {
        return new LocalFileModel() {
            @Override
            public File getFileDir() {
                return getCacheDir(false, "userCache");
            }
        };
    }

}
