package com.season.example.model.local.file;

import com.season.rapiddevelopment.model.BaseLocalModel;

/**
 * Disc: 文件工厂
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:01
 */
public class FileLocalModelFactory {


    public BaseLocalModel key() {
        return new FileModel("key");
    }

    public BaseLocalModel commcon() {
        return new FileModel("userCache");
    }

}
