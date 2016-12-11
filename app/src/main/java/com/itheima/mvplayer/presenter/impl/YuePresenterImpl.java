package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.presenter.YuePresenter;
import com.itheima.mvplayer.view.YueListView;

public class YuePresenterImpl implements YuePresenter {
    public static final String TAG = "YuePresenterImpl";

    private YueListView mYueListView;

    public YuePresenterImpl(YueListView view) {
        mYueListView = view;
    }

    @Override
    public void loadYueData() {

    }

    @Override
    public void loadMoreYueData() {

    }

    @Override
    public void refresh() {

    }
}
