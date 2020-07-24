package com.season.example.presenter;

import com.season.example.entry.BaseEntry;
import com.season.example.entry.ClientKey;
import com.season.example.model.ModelFactory;
import com.season.mvp.presenter.BasePresenter;
import com.season.mvp.ui.IView;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class HotPresenter extends BasePresenter {

    public HotPresenter(IView view) {
        super(view);
    }

    public void getKey() {
        getView().getLoadingView().showLoadingView();
        ClientKey.resetClientKey();
        ModelFactory.net().kuaifang().key().getClientKey(
                new HttpCallback<BaseEntry<ClientKey>>(BasePresenter.GET_KEY) {
            protected void afterResponse(BaseEntry<ClientKey> result) {
                ClientKey.saveKeyData(result.data);
                onResponse2UI(result.data);
                ModelFactory.local().file().key().setValue("keyData", result.data,
                        new LocalObserver<Boolean>());
            }
        });
    }


}
