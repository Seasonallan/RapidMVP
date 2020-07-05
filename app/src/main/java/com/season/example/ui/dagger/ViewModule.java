package com.season.example.ui.dagger;

import com.season.rapiddevelopment.ui.IView;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    private IView iView;
    public ViewModule(IView iView){
        this.iView = iView;
    }


    @Provides
    public IView provideIView(){
        return this.iView;
    }
}
