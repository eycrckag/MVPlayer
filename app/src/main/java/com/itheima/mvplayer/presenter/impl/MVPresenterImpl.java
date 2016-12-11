package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.network.NetworkListener;
import com.itheima.mvplayer.presenter.MVPresenter;
import com.itheima.mvplayer.view.MVView;

public class MVPresenterImpl implements MVPresenter {
    public static final String TAG = "MVPresenterImpl";

    public MVView mMVView;

    public MVPresenterImpl(MVView view) {
        mMVView = view;
    }

    @Override
    public void loadAreaData() {
//        new MVAreaRequest(URLProviderUtil.getMVareaUrl(), mNetworkListener);
    }

    private NetworkListener mNetworkListener = new NetworkListener() {
        @Override
        public void onError(String errorMsg) {

        }

        @Override
        public void onSuccess(Object result) {

        }
    };
}
