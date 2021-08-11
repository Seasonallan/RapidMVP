package com.season.mvp.model;

import com.season.lib.support.dbase.base.BaseDao;
import com.season.lib.support.dbase.base.BaseDatabase;
import com.season.mvp.model.base.BaseDatabaseModel;

/**
 * 数据库model
 * Created by Season on 2017/6/12.
 */

public class LocalDatabaseModel<T extends BaseDao> extends BaseDatabaseModel {

    BaseDatabase database;

    public LocalDatabaseModel(Class<T> tClass) {
        database = new BaseDatabase(tClass);
    }

    public boolean delete(T item){
        return database.deleteItem(item) > 0;
    }

    @Override
    public Object getDataImmediately(int flag) {
        return database.getAll();
    }

    @Override
    public boolean setDataImmediately(int flag, Object value) {
        if (flag == 1){

        }
        return database.insert((BaseDao) value) >= 0;
    }
}
