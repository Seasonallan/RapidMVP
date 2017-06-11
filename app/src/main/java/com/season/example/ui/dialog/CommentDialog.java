package com.season.example.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.tools.emoji.Emojicon;
import com.season.rapiddevelopment.tools.emoji.EmojiconHandler;
import com.season.rapiddevelopment.tools.emoji.People;
import com.season.rapiddevelopment.tools.emoji.view.EmojiDotView;
import com.season.rapiddevelopment.tools.emoji.view.EmojiconTextView;
import com.season.rapiddevelopment.ui.view.FullGridView;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-12 00:00
 */
public class CommentDialog extends Dialog {

    public CommentDialog(Activity context) {
        super(context);
    }

    public void hideEmoji(){
        if (bottomView != null && functionView != null){
            functionView.setImageResource(R.mipmap.input_biaoqing);
            bottomView.setVisibility(View.GONE);
        }
    }

    public void clearEditText(){
        if (editView != null) {
            editView.setText("");
        }
    }

    private View bottomView;
    private EditText editView;
    private EmojiDotView emojiDotView;
    private ImageView functionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_comment);

        bottomView =  findViewById(R.id.commentdialog_viewpagerc);
        emojiDotView = (EmojiDotView) findViewById(R.id.commentdialog_emojidot);
        editView = (EditText) findViewById(R.id.commentdialog_et_content);
        functionView = (ImageView) findViewById(R.id.commentdialog_tv_cancel);
        ViewPager viewPager = (ViewPager) findViewById(R.id.commentdialog_viewpager);
        MyPagerAdapter adapter = new MyPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        emojiDotView.setCount(adapter.getCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                emojiDotView.select(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        Window dialogWindow = getWindow();

        ColorDrawable dw = new ColorDrawable(0x0000ff00);
        dialogWindow.setBackgroundDrawable(dw);
        dialogWindow.setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lay = dialogWindow.getAttributes();
        lay.width = getContext().getResources().getDisplayMetrics().widthPixels;
        lay.gravity = Gravity.BOTTOM;

        functionView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (bottomView.getVisibility() == View.VISIBLE) {
                    showSoftInput();
                    functionView.setImageResource(R.mipmap.input_biaoqing);
                    bottomView.setVisibility(View.GONE);
                } else {
                    hideSoftInputFromWindow();
                    functionView.setImageResource(R.mipmap.input_keboard);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            bottomView.setVisibility(View.VISIBLE);
                        }
                    }, 80);
                }

            }
        });

        findViewById(R.id.commentdialog_tv_comment).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCommit(editView.getText().toString().trim());
            }
        });

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                showSoftInput();
            }
        }, 100);

    }


    /**
     * Pager适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
        private Context context;
        public MyPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int position) {
            ViewPager pViewPager = ((ViewPager) arg0);
            FullGridView fullGridView = new FullGridView(context);
            fullGridView.setNumColumns(7);
            fullGridView.setSelector(R.drawable.selector_bkg_transparent);
            //fullGridView.setVerticalSpacing((int) (4 * getContext().getResources().getDisplayMetrics().density));
            GridAdapter adapter = null;
            switch (position) {
                case 0:
                    adapter = new GridAdapter(People.DATA1);
                    break;
                case 1:
                    adapter = new GridAdapter(People.DATA2);
                    break;
                case 2:
                    adapter = new GridAdapter(People.DATA3);
                    break;
                case 3:
                    adapter = new GridAdapter(People.DATA4);
                    break;

                default:
                    break;
            }
            fullGridView.setAdapter(adapter);
            fullGridView.setOnItemClickListener(new OnGridItemClickListener(position));
            pViewPager.addView(fullGridView);
            return fullGridView;
        }

        class OnGridItemClickListener implements AdapterView.OnItemClickListener {
            int position;
            public OnGridItemClickListener(int posi) {
                this.position = posi;
            }
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posi, long id) {
                if (editView == null) {
                    return;
                }
                if(posi == People.DATA1.length - 1){
                    EmojiconHandler.removeEmoji(editView);
                    return;
                }
                Emojicon emojicon = null;
                switch (position) {
                    case 0:
                        emojicon = People.DATA1[posi];
                        break;
                    case 1:
                        emojicon = People.DATA2[posi];
                        break;
                    case 2:
                        emojicon = People.DATA3[posi];
                        break;
                    case 3:
                        emojicon = People.DATA4[posi];
                        break;

                    default:
                        break;
                }
                EmojiconHandler.addEmojis(editView, emojicon);
            }

        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    class GridAdapter extends BaseAdapter {
        Emojicon[] data;
        public GridAdapter(Emojicon[] data) {
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EmojiconTextView textView = new EmojiconTextView(getContext());
            int height = (int) (180 * getContext().getResources().getDisplayMetrics().density/4);
            textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    height));
            int padding = (int) (8 * getContext().getResources().getDisplayMetrics().density);
            textView.setPadding(padding, padding, padding, padding);
            textView.setEmojiconSize(height - padding * 2);
            textView.setText(getItem(position).getEmoji());
            return textView;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Emojicon getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

    public void onCommit(String str){

    }

    @Override
    public void dismiss() {
        super.dismiss();
        hideSoftInputFromWindow();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 关闭输入法
     */
    protected void hideSoftInputFromWindow() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSoftInput() {
        if (editView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editView, InputMethodManager.SHOW_FORCED);
        }
    }


}
