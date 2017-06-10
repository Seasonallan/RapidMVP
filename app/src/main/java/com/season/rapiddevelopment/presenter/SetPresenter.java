package com.season.rapiddevelopment.presenter;

import com.season.rapiddevelopment.model.FilePrefrences;
import com.season.rapiddevelopment.model.NetModel;
import com.season.rapiddevelopment.model.entry.ClientKey;
import com.season.rapiddevelopment.ui.IView;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class SetPresenter extends BasePresenter {

    public SetPresenter(IView view) {
        super(view);
    }

    public void getKey() {
        getView().getLoadingView().showLoadingView();
        NetModel.getInstance().getClientKey(new HttpCallback<ClientKey>(BasePresenter.GET_KEY){
            protected void afterResponse(ClientKey result) {
                ClientKey.saveKeyData(result);
            }
        });
    }


}
