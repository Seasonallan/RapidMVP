package com.season.example.presenter;

import com.season.example.entry.VideoItem;
import com.season.example.entry.VideoList;
import com.season.example.model.ModelFactory;
import com.season.rapiddevelopment.Configure;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.tools.Console;
import com.season.rapiddevelopment.ui.BaseRecycleAdapter;
import com.season.rapiddevelopment.ui.IView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class HomePresenter extends BasePresenter {

    @Inject
    public HomePresenter(IView view){
        super(view);
    }

    protected <T> void onResponse2UI(int type, T result) {
        super.onResponse2UI(type, result);
        if (type == REFRESH){
            ModelFactory.local().file().commcon().setValue("HomeVideo", result, null);
        }
    }

    public void loadList(int callType) {
        if (callType == CREATE){
            getView().getLoadingView().showLoadingView();
            Console.logNetMessage("check local cache");
            ModelFactory.local().file().commcon().getValue("HomeVideo", new LocalObserver<VideoList>() {
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
                maxId = ((VideoItem)adapter.getRealItem(0)).pub_id;
            }
        }else if (callType == MORE) {
            if (adapter != null && adapter.getCount() > 0) {
                action = 2;
                maxId = ((VideoItem)adapter.getRealItem(adapter.getCount() - 1)).pub_id;
            }
        }else{
        }

        if (true){
            VideoList list = new VideoList();
            ArrayList<VideoItem> videoList = new ArrayList<VideoItem>();
            for (int i = 0; i < Configure.PAGE_SIZE; i++) {

                VideoItem item = new VideoItem();
                item.name = "name";
                item.intro = "intro";
                item.cover_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594035526874&di=3fdb235c72b928d264ca24b022f1ad9a&imgtype=0&src=http%3A%2F%2Fdmimg.5054399.com%2Fallimg%2Fpkm%2Fpk%2F13.jpg";

                videoList.add(item);
            }
            list.movies = videoList;
            onResponse2UI(callType, list);
        }else{
            ModelFactory.net().kuaifang().video().getVideo(Configure.PAGE_SIZE, action, maxId,
                    new HttpCallback<VideoList>(callType));
        }

    }


}
