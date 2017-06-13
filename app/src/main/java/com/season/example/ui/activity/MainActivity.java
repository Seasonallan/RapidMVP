package com.season.example.ui.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.season.example.ui.fragment.CategoryFragment;
import com.season.rapiddevelopment.R;
import com.season.example.ui.fragment.HomeFragment;
import com.season.example.ui.fragment.HotFragment;
import com.season.example.ui.fragment.UserFragment;

public class MainActivity extends FragmentActivity {

    private String mTabDescription[] = {"Home", "Category", "Hot", "User"};
    private int mTabIcon[] = {R.drawable.icon_home, R.drawable.icon_category, R.drawable.icon_hot, R.drawable.icon_user};
    private Class mTabFragment[] = {HomeFragment.class, CategoryFragment.class, HotFragment.class, UserFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(R.id.main_bottom_bar);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_fragment);

        for (int i = 0; i < mTabDescription.length; i++) {
            TabHost.TabSpec spec = mTabHost.newTabSpec(mTabDescription[i]).setIndicator(getView(i));
            mTabHost.addTab(spec, mTabFragment[i], null);
        }
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


}
