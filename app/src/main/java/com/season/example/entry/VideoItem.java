package com.season.example.entry;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.season.lib.support.dbase.BaseSQLiteOpenHelper;
import com.season.lib.support.dbase.base.BaseDao;
import com.season.lib.support.dbase.base.IDbHelper;
import com.season.lib.support.dbase.base.iterface.Column;
import com.season.lib.support.dbase.base.iterface.Table;

import java.io.Serializable;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:21
 */

@Table(name = "VideoItem", isOrderBy = true)
public class VideoItem extends BaseDao implements Serializable {

    private static final long serialVersionUID = -2702279901753042304L;


    /**
     * 可用于装饰数据库
     * @return
     */
    public IDbHelper newDatabaseHelper(){
        return new IDbHelper() {
            @Override
            public SQLiteDatabase getDatabase() {

                return new BaseSQLiteOpenHelper("VideoItemDatabase", getDatabaseVersion()){
                    @Override
                    public void onCreate(SQLiteDatabase db) {
                        createTable(db);
                    }

                    @Override
                    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        dropTable(db);
                        createTable(db);
                    }
                }.getWritableDatabase();
            }
        };
    }

    public String groupName;

    public String groupTime;

    public String id;

    public String name;

    public String img_model;

    public String intro;

    public String cover_url;

    public String collect_id;

    public String duration;

    @Column(name = "vid", isPrimaryKey = true, isOrderDesc = true)
    public String vid;

    @Column(name = "pub_id")
    public String pub_id;

    public String web_url;

    public String url;

    public String description;

    public String open_type;

    public String org_id;

    public String title;

    public String m3u8_url;

    public String img_url;

    public String img_width;

    public String img_height;

    public String digs;

    public boolean heightBigger() {
        try {
            return true;//Integer.parseInt(img_height) >= Integer.parseInt(img_width);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasDigs() {
        try {
            int d = Integer.parseInt(digs);
            return d > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void digestUp() {
        try {
            digs = (Integer.parseInt(digs) + 1) + "";
        } catch (Exception e) {
            digs = "1";
        }
    }

    public String shares;

    public String subtitle;

    public String plays;

    public String share_url;

    public String publish_time;

    public String is_popup;

    public String web_plays;

    public String iframe_src;

    public String comments;

    public String web_digs;

    public String html;

    public String time;

    public String has_collected;

    public boolean hasCollect() {
        try {
            return !TextUtils.isEmpty(has_collected) && has_collected.equals("1");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 图片显示模式，1：等比例模式 2：填充模式
     *
     * @return
     */
    public boolean isImageModeFill() {
        try {
            return !TextUtils.isEmpty(img_model) && img_model.equals("2");
        } catch (Exception e) {
            return true;
        }
    }

}
