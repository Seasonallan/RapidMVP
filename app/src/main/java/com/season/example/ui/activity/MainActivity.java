package com.season.example.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.ViewPager;

import com.season.example.ui.dagger.FragmentComponent;
import com.season.example.ui.fragment.CategoryFragment;
import com.season.rapiddevelopment.R;
import com.season.example.ui.fragment.HomeFragment;
import com.season.example.ui.fragment.HotFragment;
import com.season.example.ui.fragment.UserFragment;
import com.season.rapiddevelopment.ui.BaseTLEActivity;

public class MainActivity extends BaseTLEActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private String mTabDescription[] = {"Home", "Category", "Hot", "User"};
    private int mTabIcon[] = {R.drawable.icon_home, R.drawable.icon_category, R.drawable.icon_hot, R.drawable.icon_user};
    private Class mTabFragment[] = {HomeFragment.class, CategoryFragment.class, HotFragment.class, UserFragment.class};

    private FragmentTabHost mTabHost;
    private ViewPager viewPager;

    @Override
    protected void inject(FragmentComponent component) {
    }

    @Override
    protected boolean isTopTileEnable(){
        return false;
    }

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

        mTabHost.setCurrentTab(3);

        performCodeWithPermission("App请求访问权限",  new PermissionCallback() {
            @Override
            public void hasPermission() {
            }
            @Override
            public void noPermission() {
            }

        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

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

    class PagerAdapter extends FragmentPagerAdapter {

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
