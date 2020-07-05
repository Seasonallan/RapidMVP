package com.season.rapiddevelopment.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.season.rapiddevelopment.model.database.iterface.Column;
import com.season.rapiddevelopment.model.database.iterface.IDbHelper;
import com.season.rapiddevelopment.model.database.iterface.Table;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 
 *  数据库实体基类,   用以反射
 * @author laijp
 * @date 2014-6-13
 * @email 451360508@qq.com
 */
public abstract class BaseDao {



    /**
     * 可用于装饰数据库
     * @return
     */
    public IDbHelper newDatabaseHelper(){
        return null;
    }


    /**
     * 获取数据库版本
     * @return
     */
    public int getDatabaseVersion(){
        return 1;
    }

    /**
     * 获取表名字
     */
    public String getTableName(){
        return getClass().getAnnotation(Table.class).name();
    }

    /**
     * 删除表单
     * @param db
     */
    public void dropTable(SQLiteDatabase db){
        StringBuffer sql = new StringBuffer("DROP TABLE IF EXISTS ");
        sql.append(getTableName());
        db.execSQL(sql.toString());
    }
    
    /**
     * 数据库的创建
     * @param db
     */
    public void createTable(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer("CREATE TABLE ");
        sql.append(getTableName());
        sql.append(" (_id INTEGER PRIMARY KEY AUTOINCREMENT");
        for (Field field : getClass().getDeclaredFields()) {
            Column fieldEntityAnnotation = field.getAnnotation(Column.class);
            if (fieldEntityAnnotation != null) {
                sql.append(", ");
                sql.append(fieldEntityAnnotation.name());
                sql.append(" ");
                sql.append(fieldEntityAnnotation.type());
            }
        }
        sql.append(");");
        db.execSQL(sql.toString());
    }

    /**
     * 数据库更新操
     * @param db 可操作数据库
     * @param oldVersion 旧版本号
     * @param newVersion 新版本号
     */
    public void onUpgradeAction(final SQLiteDatabase db, final int oldVersion,
              final int newVersion){

    }

	/**
	 * 获取唯一
	 * @return
	 */
	public List<String> getPrimaryKeyColumnName() {
        List<String> keys = new ArrayList<String>();
		for (Field field : getClass().getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				String columnName = fieldEntityAnnotation.name();
				if (columnName != null) {
					Column annotationColumn = field.getAnnotation(Column.class);
					if (annotationColumn.isPrimaryKey()) {
						keys.add(columnName);
					}
				}
			}
		}
		return keys;
	}

    /**
     * 根据唯一码构建SQL搜索条件语句
     * @return
     */
    public String getPrimaryKeyWhereClause(){
        List<String> keys = getPrimaryKeyColumnName();
        String where = null;
        if (keys != null && keys.size() > 0){
            where = "";
            for (int i =0 ; i< keys.size(); i++){
                where += keys.get(i) + " = '" + getValue(keys.get(i)) + "'";
                if (i < keys.size() -1){
                    where += " AND ";
                }
            }
        }
        return where;
    }
	/**
	 * 获取结果排序
	 * @return
	 */
	public String getOrderColumnMessage() { 
		for (Field field : getClass().getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				String columnName = fieldEntityAnnotation.name();
				if (columnName != null) {
					Column annotationColumn = field.getAnnotation(Column.class);
					if (annotationColumn.isOrderDesc()) {
						return columnName + " desc";
					}else if (annotationColumn.isOrderAsc()) {
						return columnName + " asc";
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取 列名称数组，可用于更新所有数�?
	 * @return
	 */
	@Deprecated
	public String[] getColumns() {
		boolean isHaveAnyKey = false;
		List<String> columnsList = new ArrayList<String>();
		for (Field field : getClass().getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				String columnName = fieldEntityAnnotation.name();
				if (columnName != null)
					columnsList.add(columnName);
				if (fieldEntityAnnotation.isPrimaryKey()) {
					isHaveAnyKey = true;
				}
			}
		}
		if (!isHaveAnyKey) {
			columnsList.add("_id");
		}
		String[] columnsArray = new String[columnsList.size()];
		return columnsList.toArray(columnsArray);
	}
	
	/**
	 * Cursor类型转换为该实体
	 * @param cursor
	 * @return
	 */
	public void fromCursor(Cursor cursor){
        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (!field.isAccessible())
                    field.setAccessible(true);
                Class<?> fieldType = field.getType();
                Column fieldEntityAnnotation = field.getAnnotation(Column.class);
                if (fieldEntityAnnotation != null){
                    field.set(this, getValueFromCursor(cursor, fieldType, fieldEntityAnnotation.name()));
                }
            } catch (Exception e) {
            }
        }
	}

	/**
	 * 转换为可用于数据库操作的键
	 * @return
	 * @throws IllegalAccessException
	 */
	public ContentValues toContentValues(){
		ContentValues contentValues = new ContentValues();
		for (Field field : getClass().getDeclaredFields()) {
            try{
                   if (!field.isAccessible()){
                        field.setAccessible(true); // for private variables
                   }
                    Object fieldValue = field.get(this);
                    Column fieldEntityAnnotation = field.getAnnotation(Column.class);
                if (fieldEntityAnnotation != null)
                    putInContentValues(contentValues, fieldValue, fieldEntityAnnotation.name());
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		return contentValues;
	}

	/**
	 * 获取某个键的
	 * @param key
	 * @return
	 */
	public Object getValue(String key) { 
		try {  
			for (Field field : getClass().getDeclaredFields()) {
                Column fieldEntityAnnotation = field.getAnnotation(Column.class);
                if (fieldEntityAnnotation != null && fieldEntityAnnotation.name().equals(key)){
                    if (!field.isAccessible())
                        field.setAccessible(true); // for private variables
                    return field.get(this);
                }
			} 
		}catch (IllegalAccessException e) { 
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 存储某一对键
     * @param contentValues
     * @throws IllegalAccessException
     */
    private void putInContentValues(ContentValues contentValues, Object fieldValue, String key) throws IllegalAccessException {
 
        if (fieldValue instanceof Long) {
            contentValues.put(key, Long.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof String) {
            contentValues.put(key, fieldValue.toString());
        } else if (fieldValue instanceof Integer) {
            contentValues.put(key, Integer.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Float) {
            contentValues.put(key, Float.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Byte) {
            contentValues.put(key, Byte.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Short) {
            contentValues.put(key, Short.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Boolean) {
            contentValues.put(key, Boolean.parseBoolean(fieldValue.toString()));
        } else if (fieldValue instanceof Double) {
            contentValues.put(key, Double.valueOf(fieldValue.toString()));
        } else if (fieldValue instanceof Byte[] || fieldValue instanceof byte[]) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        outputStream);
                objectOutputStream.writeObject(fieldValue);
                contentValues.put(key, outputStream.toByteArray());
                objectOutputStream.flush();
                objectOutputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }

    // Get content from specific types
    private Object getValueFromCursor(Cursor cursor, Class<?> fieldType, String columnName)
            throws IllegalAccessException {
        Object value = null;
        int columnIndex = cursor.getColumnIndex(columnName);
        if (fieldType.isAssignableFrom(Long.class)
                || fieldType.isAssignableFrom(long.class)) {
            value = cursor.getLong(columnIndex);
        } else if (fieldType.isAssignableFrom(String.class)) {
            value = cursor.getString(columnIndex);
        } else if ((fieldType.isAssignableFrom(Integer.class) || fieldType
                .isAssignableFrom(int.class))) {
            value = cursor.getInt(columnIndex);
        } else if ((fieldType.isAssignableFrom(Byte[].class) || fieldType
                .isAssignableFrom(byte[].class))) {
            value = cursor.getBlob(columnIndex);
        } else if ((fieldType.isAssignableFrom(Double.class) || fieldType
                .isAssignableFrom(double.class))) {
            value = cursor.getDouble(columnIndex);
        } else if ((fieldType.isAssignableFrom(Float.class) || fieldType
                .isAssignableFrom(float.class))) {
            value = cursor.getFloat(columnIndex);
        } else if ((fieldType.isAssignableFrom(Short.class) || fieldType
                .isAssignableFrom(short.class))) {
            value = cursor.getShort(columnIndex);
        } else if (fieldType.isAssignableFrom(Byte.class)
                || fieldType.isAssignableFrom(byte.class)) {
            value = (byte) cursor.getShort(columnIndex);
        } else if (fieldType.isAssignableFrom(Boolean.class)
                || fieldType.isAssignableFrom(boolean.class)) {
            int booleanInteger = cursor.getInt(columnIndex);
            value = booleanInteger == 1;
        }
        return value;
    }

    /**
     * 获取创建数据库的键值对
     *
     * @return
     */
    public HashMap<String, String> getHashMap2CreateDatabase() {
        Field[] field = getClass().getDeclaredFields();
        HashMap<String, String> res = new HashMap<String, String>();
        for (int j = 0; j < field.length; j++) {
            try {
                String name = field[j].getName();
                Object value = field[j].get(this);
                if (value instanceof String) {
                    res.put(name, "varchar");
                } else if (value instanceof Boolean) {
                    res.put(name, "integer");
                } else if (value instanceof Integer) {
                    res.put(name, "integer");
                } else if (value instanceof Float) {
                    res.put(name, "varchar");
                } else if (value instanceof Double) {
                    res.put(name, "varchar");
                } else if (value instanceof Long) {
                    res.put(name, "long");
                } else if (value instanceof Short) {
                    res.put(name, "varchar");
                } else {
                    res.put(name, "varchar");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

}