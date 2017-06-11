/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.season.rapiddevelopment.tools.emoji;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com)
 */
class EmojiconSpan extends DynamicDrawableSpan {
    private final Context mContext;
    private final int mResourceId;
    private final int mSize;
    private Drawable mDrawable;

    public EmojiconSpan(Context context, int resourceId, int size) {
        super();
        mContext = context;
        mResourceId = resourceId;
        mSize = size;
    }

/*
    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x, 
                     int top, int y, int bottom, Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();
        
        int transY = bottom - b.getBounds().bottom; 

        canvas.translate(x, transY - mSize/2);
        b.draw(canvas);
        canvas.restore();
    }

    private WeakReference<Drawable> mDrawableRef;
    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<Drawable>(d);
        }

        return d;
    }*/
    public Drawable getDrawable() {
        if (mDrawable == null) {
            try {
                mDrawable = mContext.getResources().getDrawable(mResourceId);
                int size = mSize;
                mDrawable.setBounds(0, 0, size, size);
            } catch (Exception e) {
                // swallow
            }
        }
        return mDrawable;
    }
}