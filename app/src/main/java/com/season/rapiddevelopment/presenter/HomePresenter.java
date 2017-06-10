package com.season.rapiddevelopment.presenter;

import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.model.NetModel;
import com.season.rapiddevelopment.model.entry.VideoItem;
import com.season.rapiddevelopment.model.entry.VideoList;
import com.season.rapiddevelopment.ui.IView;
import com.season.rapiddevelopment.ui.adapter.BaseRecycleAdapter;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class HomePresenter extends BasePresenter{

    public HomePresenter(IView view){
        super(view);
    }

    public void loadList(int callType) {
        String maxId = "0";
        int action = 1;

        BaseRecycleAdapter adapter = getView().getAdapter();
        if (callType == REFRESH) {
            if (adapter != null && adapter.getCount() > 0) {
                maxId = ((VideoItem)adapter.getItem(0)).pub_id;
            }
        }else if (callType == MORE) {
            if (adapter != null && adapter.getCount() > 0) {
                action = 2;
                maxId = ((VideoItem)adapter.getItem(adapter.getCount() - 1)).pub_id;
            }
        }else{
            getView().getLoadingView().showLoadingView();
        }
        NetModel.getInstance().getVideo(Configure.PAGE_SIZE, action, maxId, new HttpCallback<VideoList>(callType));

    }


}
