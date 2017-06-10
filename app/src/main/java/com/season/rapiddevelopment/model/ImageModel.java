package com.season.rapiddevelopment.model;

import android.widget.ImageView;

import com.season.rapiddevelopment.BaseApplication;
import com.squareup.picasso.Picasso;

/**
 * Disc: 图片加载框架
 Picasso.with(context).load(R.drawable.landing_screen).into(imageView1);
 Picasso.with(context).load("file:///android_asset/DvpvklR.png").into(imageView2);
 Picasso.with(context).load(new File(...)).into(imageView3);

 Picasso.with(context)
     .load(url)
     .placeholder(R.drawable.user_placeholder)
     .error(R.drawable.user_placeholder_error)
     .into(imageView);
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 03:22
 */
public class ImageModel {

    /**
     * 加载图片
     * @param imageView
     * @param url
     */
    public static void bindImage2View(ImageView imageView, String url){
        Picasso.with(BaseApplication.sContext).load(url).into(imageView);
    }


}
