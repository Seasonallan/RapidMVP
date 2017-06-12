package com.season.rapiddevelopment.model.database.iterface;

import android.database.sqlite.SQLiteDatabase;

/**
 * 代表�?��数据�?
 */
public interface IDbHelper {
    /**
     * 获取操作数据�?
     * @return
     */
    public SQLiteDatabase getDatabase();
}
