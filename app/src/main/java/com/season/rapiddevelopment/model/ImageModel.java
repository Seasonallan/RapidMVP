package com.season.rapiddevelopment.model;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.season.rapiddevelopment.BaseApplication;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Disc: 图片加载框架
 * Picasso.with(context).load(R.drawable.landing_screen).into(imageView1);
 * Picasso.with(context).load("file:///android_asset/DvpvklR.png").into(imageView2);
 * Picasso.with(context).load(new File(...)).into(imageView3);
 * <p/>
 * Picasso.with(context)
 * .load(url)
 * .placeholder(R.drawable.user_placeholder)
 * .error(R.drawable.user_placeholder_error)
 * .into(imageView);
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 03:22
 */
public class ImageModel {

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void bindImage2View(ImageView imageView, String url) {
        Picasso.with(BaseApplication.sContext).load(url).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param imageView
     * @param url
     */
    public static void bindCirclrImage2View(ImageView imageView, String url) {
        Picasso.with(BaseApplication.sContext).load(url).transform(new CircleTransform()).into(imageView);
    }

    /**
     * 圆角
     */
    public static class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            if (source != squaredBitmap){
                //source.recycle();
            }
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();

            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

}
