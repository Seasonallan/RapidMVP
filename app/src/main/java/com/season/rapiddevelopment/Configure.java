package com.season.rapiddevelopment;


/**
 * Disc: 配置信息
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 01:10
 */
public class Configure {

    /**
     * 加载数据分页一次获取的数量
     */
    public static final int PAGE_SIZE = 15;

    private static int sScreenWidth, sScreenHeight;

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        if (sScreenWidth <= 0) {
            sScreenWidth = BaseApplication.sContext.getResources().getDisplayMetrics().widthPixels;
            sScreenHeight = BaseApplication.sContext.getResources().getDisplayMetrics().heightPixels;
        }
        return sScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        if (sScreenHeight <= 0) {
            sScreenWidth = BaseApplication.sContext.getResources().getDisplayMetrics().widthPixels;
            sScreenHeight = BaseApplication.sContext.getResources().getDisplayMetrics().heightPixels;
        }
        return sScreenHeight;
    }

}
