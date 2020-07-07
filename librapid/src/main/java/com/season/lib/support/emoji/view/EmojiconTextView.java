package com.season.lib.support.emoji.view;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.season.lib.support.emoji.EmojiconHandler;


/**
 * Disc: 可显示表情的TextView
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 21:26
 */
public class EmojiconTextView extends TextView {

    private int mEmojiconSize;

    public EmojiconTextView(Context context) {
        super(context);
        mEmojiconSize = (int) (18 * getContext().getResources().getDisplayMetrics().density);
    }

    public EmojiconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mEmojiconSize = (int) (18 * getContext().getResources().getDisplayMetrics().density);
    }

    public EmojiconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mEmojiconSize = (int) (18 * getContext().getResources().getDisplayMetrics().density);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        EmojiconHandler.replaceEmojis(getContext(), builder, mEmojiconSize);
        super.setText(builder, type);
    }

    public void setEmojiconSize(int pixels) {
        mEmojiconSize = pixels;
    }
}
