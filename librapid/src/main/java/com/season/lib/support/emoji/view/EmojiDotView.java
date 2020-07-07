package com.season.lib.support.emoji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Disc: 底部小点，用于配合viewpager使用
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:26
 */
public class EmojiDotView extends View {
	
	public EmojiDotView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

    /**
     * 设置总数
     * @param count
     */
	public void setCount(int count){
		this.dotCount = count;
		invalidate();
	}

    /**
     * 选中位置
     * @param posi
     */
	public void select(int posi){
		this.currentSelect = posi;
		invalidate();
	}
	
	private int height, width;
	public int dotCount = 5, currentSelect = 0;

	private Paint paint;
    float padding;
    private void init(){
        if (paint == null){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);

            padding = 8 * getContext().getResources().getDisplayMetrics().density;
        }
    }

	@Override
	protected void onDraw(Canvas canvas) {
		if (width <= 0 || height <= 0) {
			width = getWidth();
			height = getHeight();
		}
		
		if (width <= 0 || height <= 0) {
			return;
		}
        init();

		float radius = height/2;
		float cx = width/2 - dotCount * (padding/2 + height/2) + radius + padding/2;
		float cy = height/2;
		for (int i = 0; i < dotCount; i++) {
			if (i == currentSelect) {
				paint.setColor(0xff12a0ea);
			} else {
				paint.setColor(0x88c9c9c9);
			}
			canvas.drawRect(cx - radius/2, cy - radius/4, cx + radius/2, cy + radius/4, paint);
			//canvas.drawCircle(cx, cy, radius, paint);
			cx += radius*2 + padding;
		}

	}

}
