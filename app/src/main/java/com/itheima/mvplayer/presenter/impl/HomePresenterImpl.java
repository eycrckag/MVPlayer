package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.presenter.HomePresenter;
import com.itheima.mvplayer.view.HomeView;

public class HomePresenterImpl implements HomePresenter {
    public static final String TAG = "HomePresenterImpl";

    private HomeView mHomeView;

    public HomePresenterImpl(HomeView homeView) {
        mHomeView = homeView;
    }

    @Override
    public void loadHomeData() {

    }
}
