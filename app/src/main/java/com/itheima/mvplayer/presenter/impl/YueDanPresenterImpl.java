package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.model.NetworkCallback;
import com.itheima.mvplayer.model.NetworkManager;
import com.itheima.mvplayer.presenter.YueDanPresenter;
import com.itheima.mvplayer.view.YueDanView;

public class YueDanPresenterImpl implements YueDanPresenter {
    public static final String TAG = "YueDanPresenterImpl";

    private YueDanView mYueDanView;

    public YueDanPresenterImpl(YueDanView view) {
        mYueDanView = view;
    }

    @Override
    public void loadYueDanData() {
        NetworkManager.getInstance().loadYueData(new NetworkCallback() {
            @Override
            public void onError() {
                mYueDanView.onLoadYueDanDataFailed();
            }

            @Override
            public void onSuccess(Object result) {
                mYueDanView.onLoadYueDanDataSuccess();
            }
        });
    }

    @Override
    public void loadMoreYueDanData() {

    }

    @Override
    public void refresh() {

    }
}
