package com.season.example.presenter;

import com.season.example.entry.ClientKey;
import com.season.example.model.ModelFactory;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.ui.IView;

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
        ModelFactory.net().kuaifang().key().getClientKey(new HttpCallback<ClientKey>(BasePresenter.GET_KEY) {
            protected void afterResponse(ClientKey result) {
                ClientKey.saveKeyData(result);
                onResponse2UI(result);
                ModelFactory.local().file().key().setValue("keyData", result, new LocalObserver<Boolean>());
            }
        });
    }


}
