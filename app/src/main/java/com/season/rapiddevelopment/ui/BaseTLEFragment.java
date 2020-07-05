package com.season.rapiddevelopment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.fragment.app.Fragment;

import com.season.example.ui.dagger.DaggerFragmentComponent;
import com.season.example.ui.dagger.FragmentComponent;
import com.season.example.ui.dagger.ViewModule;
import com.season.rapiddevelopment.BaseApplication;
import com.season.rapiddevelopment.R;
import com.season.rapiddevelopment.presenter.BasePresenter;
import com.season.rapiddevelopment.ui.empty.EmptyImpl;
import com.season.rapiddevelopment.ui.empty.IEmptyAction;
import com.season.rapiddevelopment.ui.empty.IEmptyView;
import com.season.rapiddevelopment.ui.loading.ILoadingAction;
import com.season.rapiddevelopment.ui.loading.ILoadingView;
import com.season.rapiddevelopment.ui.loading.LoadingImpl;
import com.season.rapiddevelopment.ui.titlebar.ITitleBar;
import com.season.rapiddevelopment.ui.titlebar.ITitleBarAction;
import com.season.rapiddevelopment.ui.titlebar.TitleBarImpl;

import javax.inject.Inject;


/**
 * Disc: Fragment基类
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-06-10 15:25
 */
public abstract class BaseTLEFragment<P extends BasePresenter> extends Fragment implements ITitleBarAction, ILoadingAction, IEmptyAction, IView {

    // 设计模式 - 模板方法(Template Method)模式
    /**
     * 选择布局
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * View的创建和事件
     */
    protected abstract void onViewCreated();

    /**
     * 注入
     * @param component
     */
    protected abstract void inject(FragmentComponent component);


    /**
     * 顶部标题控制栏
     * @return
     */
    @Override
    public ITitleBar getTitleBar(){
        return mTitleBar;
    }


    /**
     * 控制加载中的显示与消失
     * @return
     */
    @Override
    public ILoadingView getLoadingView(){
        return mLoadingView;
    }

    /**
     * 控制重新加载的显示与消失
     * @return
     */
    @Override
    public IEmptyView getEmptyView(){
        return mEmptyView;
    }


    ITitleBar mTitleBar;
    ILoadingView mLoadingView;
    IEmptyView mEmptyView;


    @Inject
    protected P mPresenter;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.base_tle, container, false);
            mEmptyView = new EmptyImpl(this);
            mLoadingView = new LoadingImpl(this);
            if (isTopTileEnable()){
                ViewStub viewStub = mView.findViewById(R.id.common_top);
                viewStub.inflate();
                mTitleBar = new TitleBarImpl(this);
            }
            ViewGroup contentView = (ViewGroup) mView.findViewById(R.id.main_view);
            View mainView = inflater.inflate(getLayoutId(), container, false);
            contentView.addView(mainView);
            inject(DaggerFragmentComponent.builder()
                    .viewModule(new ViewModule(this))
                    .build());
            onViewCreated();
        }
        return mView;
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return mView.findViewById(id);
    }

    /**
     * 顶部标题栏是否显示
     * @return
     */
    protected boolean isTopTileEnable(){
        return true;
    }


    @Override
    public void finish() {
        getActivity().onBackPressed();
    }

    @Override
    public <T> void onResponse(int type, T result) {
    }

    @Override
    public BaseRecycleAdapter getAdapter() {
        return null;
    }

    @Override
    public void onEmptyViewClick() {
    }

    @Override
    public void onError(int type, String errorMessage) {
        getLoadingView().dismissLoadingView();
        BaseApplication.showToast(R.mipmap.emoticon_sad, errorMessage);
    }

    protected void startActivity(Class cls){
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
