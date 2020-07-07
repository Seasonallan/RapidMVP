package com.season.example.presenter;

import com.season.example.entry.ClientKey;
import com.season.example.model.ModelFactory;
import com.season.lib.presenter.BasePresenter;
import com.season.lib.ui.IView;


/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-11 00:28
 */
public class CategoryPresenter extends BasePresenter {

    public CategoryPresenter(IView view) {
        super(view);
    }

    public void getKey() {
        ModelFactory.local().file().key().getValue("keyData",
                new LocalObserver<ClientKey>());
    }

}
