package com.season.example.ui.fragment;

import com.season.example.presenter.CategoryPresenter;
import com.season.mvp.ui.IView;
import com.season.rapiddevelopment.R;
import com.season.mvp.ui.BaseTLEFragment;
import com.season.lib.ui.view.ReboundScrollView;

/**
 * Disc: drag to change scrollview text
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class CategoryFragment extends BaseTLEFragment<CategoryPresenter> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    ReboundScrollView scrollViewTop;
    ReboundScrollView scrollViewBottom;



    @Override
    protected CategoryPresenter attachPresenter(IView view) {
        return new CategoryPresenter(view);
    }

    @Override
    protected void onViewCreated() {
        getTitleBar().setTopTile("Category");

        scrollViewTop = (ReboundScrollView) findViewById(R.id.sv_top);
        scrollViewBottom = (ReboundScrollView) findViewById(R.id.sv_bottom);
        scrollViewBottom.setTopView(scrollViewTop);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
