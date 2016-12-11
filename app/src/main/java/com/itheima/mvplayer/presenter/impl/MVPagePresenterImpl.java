package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.model.MVPageBean;
import com.itheima.mvplayer.network.MVPageRequest;
import com.itheima.mvplayer.network.NetworkListener;
import com.itheima.mvplayer.presenter.BaseListPresenter;
import com.itheima.mvplayer.view.BaseListView;

import java.util.List;

public class MVPagePresenterImpl implements BaseListPresenter {
    public static final String TAG = "MVPagePresenterImpl";

    private String mCode;
    private BaseListView mMVPageView;


    public MVPagePresenterImpl(BaseListView view, String code) {
        mMVPageView = view;
        mCode = code;
    }

    @Override
    public void loadListData() {
        MVPageRequest.getRequest(mCode,mNetworkListener).execute();
    }

    @Override
    public List getListData() {
        return null;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMoreListData() {

    }

    private NetworkListener<MVPageBean> mNetworkListener = new NetworkListener<MVPageBean>() {
        @Override
        public void onError(String errorMsg) {

        }

        @Override
        public void onSuccess(MVPageBean result) {

        }
    };
}
