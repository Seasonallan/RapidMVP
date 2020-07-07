package com.season.lib.support.dbase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.season.lib.BaseContext;
import com.season.lib.support.dbase.base.BaseDao;

/**
 * 数据库生成 升级 更新帮助
 *
 */
public abstract class BaseSQLiteOpenHelper<T extends BaseDao> extends SQLiteOpenHelper {


	public BaseSQLiteOpenHelper(String databaseName, int version) {
		super(BaseContext.sContext, databaseName, null, version);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
}