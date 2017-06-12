package com.season.rapiddevelopment.model.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.season.example.ExampleApplication;

/**
 * 数据库生成 升级 更新帮助
 * @author ziv
 *
 */
public abstract class BaseSQLiteOpenHelper<T extends BaseDao> extends SQLiteOpenHelper {


	public BaseSQLiteOpenHelper(String databaseName, int version) {
		super(ExampleApplication.sContext, databaseName, null, version);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
}