package com.season.rapiddevelopment.ui.fragment;

import android.os.AsyncTask;
import android.view.View;

import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.ui.adapter.HomeAdapter;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshBase;
import com.season.rapiddevelopment.ui.view.refreshview.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class HomeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    PullToRefreshListView mPullToRefreshListView;
    HomeAdapter mHomeAdapter;

    @Override
    protected void onViewCreated() {
        getTitleBar().setTopTile("Home");

        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_view);
        mPullToRefreshListView.enableAutoLoadingMore();
        mPullToRefreshListView.setOnRefreshListener(this);

        findViewById(R.id.loading_container).setVisibility(View.VISIBLE);
        new DownLoadTask(true, false).execute();
    }

    int pageCount = 10;
    private class DownLoadTask extends AsyncTask<Void, Void, List<String>> {

        boolean refresh, more;

        public DownLoadTask(boolean refresh, boolean more) {
            this.refresh = refresh;
            this.more = more;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int count = 0;
            if (mHomeAdapter != null) {
                count = mHomeAdapter.getItemCount();
            }
            ArrayList mDatas = new ArrayList<>();
            for (int i = 0; i < pageCount; i++) {
                mDatas.add("item" + (i + count));
            }
            return mDatas;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            findViewById(R.id.loading_container).setVisibility(View.GONE);
            mPullToRefreshListView.onRefreshComplete();
            if (refresh || mHomeAdapter == null) {
                mHomeAdapter = new HomeAdapter(getContext(), strings);
                mPullToRefreshListView.setAdapter(mHomeAdapter);
            } else {
                mHomeAdapter.append(strings);
                mHomeAdapter.notifyDataSetChanged();
                pageCount --;
                if (pageCount ==6)
                    mPullToRefreshListView.noMore();
            }
        }
    }


    @Override
    public void onRefresh() {
        new DownLoadTask(true, false).execute();
    }

    @Override
    public void onLoadingMore() {
        new DownLoadTask(false, true).execute();
    }
}
