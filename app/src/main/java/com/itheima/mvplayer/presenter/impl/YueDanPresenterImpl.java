package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.model.YueDanBean;
import com.itheima.mvplayer.network.NetworkListener;
import com.itheima.mvplayer.network.YueDanRequest;
import com.itheima.mvplayer.presenter.YueDanPresenter;
import com.itheima.mvplayer.view.YueDanView;

import java.util.ArrayList;
import java.util.List;

public class YueDanPresenterImpl implements YueDanPresenter {
    public static final String TAG = "YueDanPresenterImpl";

    private YueDanView mYueDanView;

    private List<YueDanBean.PlayListsBean> mPlayListsBeanList;


    public YueDanPresenterImpl(YueDanView view) {
        mYueDanView = view;
        mPlayListsBeanList = new ArrayList<YueDanBean.PlayListsBean>();
    }

    @Override
    public void loadYueDanData() {
        if (mPlayListsBeanList.size() > 0) {
            mYueDanView.onLoadMoreYueDanDataSuccess();
            return;
        }
        YueDanRequest.getRequest(mYueDanBeanNetworkListener).execute();
    }

    private NetworkListener<YueDanBean> mYueDanBeanNetworkListener = new NetworkListener<YueDanBean>() {
        @Override
        public void onError(String errorMsg) {
            mYueDanView.onLoadYueDanDataFailed();
        }

        @Override
        public void onSuccess(YueDanBean result) {
            mPlayListsBeanList.addAll(result.getPlayLists());
            mYueDanView.onLoadYueDanDataSuccess();
        }
    };

    @Override
    public void loadMoreYueDanData() {
        YueDanRequest.getLoadMoreRequest(mPlayListsBeanList.size(), mLoadMoreListener).execute();
    }

    private NetworkListener<YueDanBean> mLoadMoreListener = new NetworkListener<YueDanBean>() {
        @Override
        public void onError(String errorMsg) {
            mYueDanView.onLoadMoreYueDanDataFailed();
        }

        @Override
        public void onSuccess(YueDanBean result) {
            mPlayListsBeanList.addAll(result.getPlayLists());
            mYueDanView.onLoadMoreYueDanDataSuccess();
        }
    };

    @Override
    public void refresh() {
        mPlayListsBeanList.clear();
        loadYueDanData();
    }

    @Override
    public List<YueDanBean.PlayListsBean> getPlayList() {
        return mPlayListsBeanList;
    }
}
