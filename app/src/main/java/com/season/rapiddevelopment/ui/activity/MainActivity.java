package com.season.rapiddevelopment.ui.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.fragment.HomeFragment;
import com.season.rapiddevelopment.ui.fragment.SetFragment;
import com.season.rapiddevelopment.ui.fragment.UserFragment;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private String mTabDescription[] = {"Home", "Category", "Hot", "User"};
    private int mTabIcon[] = {R.drawable.icon_home, R.drawable.icon_category, R.drawable.icon_hot, R.drawable.icon_user};
    private Class mTabFragment[] = {HomeFragment.class, SetFragment.class, SetFragment.class, UserFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTabHost = (FragmentTabHost) findViewById(R.id.main_bottom_bar);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_fragment);

        for (int i = 0; i < mTabDescription.length; i++) {
            TabHost.TabSpec spec = mTabHost.newTabSpec(mTabDescription[i]).setIndicator(getView(i));
            mTabHost.addTab(spec, mTabFragment[i], null);
        }
        mTabHost.setCurrentTab(0);

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
