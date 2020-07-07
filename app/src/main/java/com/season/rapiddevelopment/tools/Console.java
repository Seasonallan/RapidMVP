package com.season.rapiddevelopment.tools;

import android.util.Log;

/**
 * Disc: 简易日志输出
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:37
 */
public class Console {

    public static void logNetMessage(Object log){
        //System.out.println(log.toString());
        Log.e("NetLogCat", log.toString());
    }

}
