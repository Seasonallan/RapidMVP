package com.season.example.ui.activity;

import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.season.example.ui.fragment.CategoryFragment;
import com.season.example.ui.fragment.HomeFragment;
import com.season.example.ui.fragment.HotFragment;
import com.season.example.ui.fragment.UserFragment;
import com.season.mvp.ui.BaseTLEActivity;
import com.season.rapiddevelopment.R;

public class MainActivity extends BaseTLEActivity implements ViewPager.OnPageChangeListener, BottomNavigationBar.OnTabSelectedListener {

    private String mTabDescription[] = {"商品", "订单", "统计", "用户"};
    private int mTabIcon[] = {R.mipmap.icon_home, R.mipmap.icon_category,
            R.mipmap.icon_hot, R.mipmap.icon_user};
    private int mTabIconSel[] = {R.mipmap.icon_home_pressed, R.mipmap.icon_category_pressed,
            R.mipmap.icon_hot_pressed, R.mipmap.icon_user_pressed};
    private Class mTabFragment[] = {HomeFragment.class, CategoryFragment.class, HotFragment.class, UserFragment.class};

    private ViewPager viewPager;

    @Override
    protected boolean isTopTileEnable() {
        return false;
    }

    private BottomNavigationBar mBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.main_fragment);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));


        mBottomNavigationBar = findViewById(R.id.main_bottom_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setBarBackgroundColor(R.color.blue_black);

        for (int i = 0; i < mTabIcon.length; i++) {
            mBottomNavigationBar
                    .addItem(new BottomNavigationItem(mTabIconSel[i], mTabDescription[i])
                            .setActiveColorResource(R.color.blue)
                            .setInactiveIconResource(mTabIcon[i])
                            .setInActiveColorResource(R.color.gray));
        }
        mBottomNavigationBar.setFirstSelectedPosition(0).initialise();
        mBottomNavigationBar.setTabSelectedListener(this);

        viewPager.addOnPageChangeListener(this);

        performCodeWithPermission("App请求访问权限", new PermissionCallback() {
                    @Override
                    public void hasPermission() {
                    }

                    @Override
                    public void noPermission() {
                    }

                }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }


    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mBottomNavigationBar.selectTab(position);
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
