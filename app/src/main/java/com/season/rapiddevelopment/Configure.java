package com.season.rapiddevelopment;


/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 01:10
 */
public class Configure {

    public static final int PAGE_SIZE = 15;

    private static int sScreenWidth, sScreenHeight;
    public static int getScreenWidth(){
        if (sScreenWidth <= 0){
            sScreenWidth = BaseApplication.sContext.getResources().getDisplayMetrics().widthPixels;
            sScreenHeight = BaseApplication.sContext.getResources().getDisplayMetrics().heightPixels;
        }
        return sScreenWidth;
    }
    public static int getScreenHeight(){
        if (sScreenHeight <= 0){
            sScreenWidth = BaseApplication.sContext.getResources().getDisplayMetrics().widthPixels;
            sScreenHeight = BaseApplication.sContext.getResources().getDisplayMetrics().heightPixels;
        }
        return sScreenHeight;
    }

}
