package com.season.rapiddevelopment.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.model.BaseNetModel;
import com.season.rapiddevelopment.model.FilePrefrences;
import com.season.rapiddevelopment.model.NetModel;
import com.season.rapiddevelopment.model.entry.BaseEntry;
import com.season.rapiddevelopment.model.entry.ClientKey;
import com.season.rapiddevelopment.tools.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Disc:
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:27
 */
public class SetFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    protected void onViewCreated() {
        getTitleBar().setTopTile("Set");
        Button btn = (Button) findViewById(R.id.btn_set);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoadingView().showLoadingView();
                NetModel.getInstance().getClientKey(new Callback<BaseEntry<ClientKey>>() {
                    @Override
                    public void onResponse(Call<BaseEntry<ClientKey>> call, Response<BaseEntry<ClientKey>> response) {
                        //4.处理结果
                        if (response.isSuccessful()) {
                            getLoadingView().dismissLoadingView();
                            BaseApplication.showToast(R.mipmap.emoticon_cool, "获取成功");
                            ClientKey result = response.body().data;
                            if (result != null) {
                                if (response.body().isClientIdInvalid()) {
                                    ClientKey.resetKeyDate();
                                }else{
                                    FilePrefrences.saveObject("keyData", result);
                                    ClientKey.initKeyData();
                                    Console.log(result);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntry<ClientKey>> call, Throwable t) {
                        getLoadingView().dismissLoadingView();
                        BaseApplication.showToast(R.mipmap.emoticon_sad, "获取失败");

                    }
                });
            }
        });
    }
}
