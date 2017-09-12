package com.season.example.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.season.example.ui.fragment.CategoryFragment;
import com.season.rapiddevelopment.R;
import com.season.example.ui.fragment.HomeFragment;
import com.season.example.ui.fragment.HotFragment;
import com.season.example.ui.fragment.UserFragment;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private String mTabDescription[] = {"Home", "Category", "Hot", "User"};
    private int mTabIcon[] = {R.drawable.icon_home, R.drawable.icon_category, R.drawable.icon_hot, R.drawable.icon_user};
    private Class mTabFragment[] = {HomeFragment.class, CategoryFragment.class, HotFragment.class, UserFragment.class};

    private FragmentTabHost mTabHost;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.main_fragment);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabHost = (FragmentTabHost) findViewById(R.id.main_bottom_bar);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_fragment);

        for (int i = 0; i < mTabDescription.length; i++) {
            TabHost.TabSpec spec = mTabHost.newTabSpec(mTabDescription[i]).setIndicator(getView(i));
            mTabHost.addTab(spec, mTabFragment[i], null);
        }
        mTabHost.getTabWidget().setDividerDrawable(null);

        mTabHost.setOnTabChangedListener(this);
        viewPager.addOnPageChangeListener(this);

        mTabHost.setCurrentTab(1);
    }

    private View getView(int i) {
        View view = View.inflate(MainActivity.this, R.layout.tab_bottom, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);
        imageView.setImageResource(mTabIcon[i]);
        textView.setText(mTabDescription[i]);
        return view;

    }

    @Override
    public void onTabChanged(String tabId) {
        int position = mTabHost.getCurrentTab();
        viewPager.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            try {
                return (Fragment) mTabFragment[position].newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public int getCount() {
            return mTabFragment.length;
        }
    }

}
