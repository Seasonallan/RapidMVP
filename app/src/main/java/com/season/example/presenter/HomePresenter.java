package com.season.example.presenter;

import com.season.example.entry.VideoItem;
import com.season.example.entry.VideoList;
import com.season.example.model.ModelFactory;
import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.IView;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class HomePresenter extends BasePresenter {

    public HomePresenter(IView view){
        super(view);
    }

    protected <T> void onResponse2UI(int type, T result) {
        super.onResponse2UI(type, result);
        if (type == REFRESH){
            ModelFactory.local().file().setValue("HomeVideo", result, null);
        }
    }

    public void loadList(int callType) {
        if (callType == CREATE){
            Console.logNetMessage("check local cache");
            ModelFactory.local().file().getValue("HomeVideo", new LocalObserver<VideoList>() {
                @Override
                public void onError(Throwable e) {
                    Console.logNetMessage("empty local cache, load from net");
                    loadList(REFRESH);
                }

                @Override
                public void onNext(VideoList o) {
                    Console.logNetMessage("local cache");
                    super.onNext(o);
                }
            });
            return;
        }

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
        ModelFactory.net().video().getVideo(Configure.PAGE_SIZE, action, maxId, new HttpCallback<VideoList>(callType));

    }


}
