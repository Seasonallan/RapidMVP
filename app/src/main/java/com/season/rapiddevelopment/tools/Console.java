package com.season.rapiddevelopment.tools;

import android.util.Log;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 21:37
 */
public class Console {

    private static final String LOG = "SEASON";


    public static void log(Object log){
        Log.e(LOG, log.toString());
    }

}
