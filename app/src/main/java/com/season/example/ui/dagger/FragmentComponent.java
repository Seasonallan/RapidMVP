package com.season.example.ui.dagger;

import com.season.example.ui.activity.CommentActivity;
import com.season.example.ui.fragment.CategoryFragment;
import com.season.example.ui.fragment.HomeFragment;
import com.season.example.ui.fragment.HotFragment;

import dagger.Component;

@Component(modules = ViewModule.class)
public interface FragmentComponent {
    void inject(HomeFragment fragment);
    void inject(CategoryFragment fragment);
    void inject(HotFragment fragment);

    void inject(CommentActivity activity);
}
