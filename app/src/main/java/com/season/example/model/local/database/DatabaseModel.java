package com.season.example.model.local.database;

import com.season.rapiddevelopment.model.BaseDatabaseModel;
import com.season.rapiddevelopment.model.database.BaseDao;
import com.season.rapiddevelopment.model.database.BaseDatabase;

/**
 * 数据库model
 * Created by Season on 2017/6/12.
 */

public class DatabaseModel<T extends BaseDao> extends BaseDatabaseModel {

    BaseDatabase database;

    DatabaseModel(Class<T> tClass) {
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
