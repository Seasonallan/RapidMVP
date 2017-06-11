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

package com.season.rapiddevelopment.tools.emoji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.season.rapiddevelopment.tools.emoji.EmojiconHandler;

/**
 * Disc: 可显示表情的输入框
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:26
 */
public class EmojiconEditText extends EditText {
    private int mEmojiconSize;

    public EmojiconEditText(Context context) {
        super(context);
    }

    public EmojiconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmojiconEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) { 
        setText(getText());
        mEmojiconSize = (int) (24 * getContext().getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        EmojiconHandler.replaceEmojis(getContext(), getText(), mEmojiconSize);
    }

    /**
     * Set the size of emojicon in pixels.
     */
    public void setEmojiconSize(int pixels) {
        mEmojiconSize = pixels;
    }
}
