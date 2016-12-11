package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.model.NetworkCallback;
import com.itheima.mvplayer.model.NetworkManager;
import com.itheima.mvplayer.model.YueDanBean;
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
        NetworkManager.getInstance().loadYueData(new NetworkCallback<YueDanBean>() {
            @Override
            public void onError() {
                mYueDanView.onLoadYueDanDataFailed();
            }

            @Override
            public void onSuccess(YueDanBean result) {
                mPlayListsBeanList.addAll(result.getPlayLists());
                mYueDanView.onLoadYueDanDataSuccess();
            }
        });
    }

    @Override
    public void loadMoreYueDanData() {
        NetworkManager.getInstance().loadYueData(mPlayListsBeanList.size(), new NetworkCallback<YueDanBean>() {
            @Override
            public void onError() {
                mYueDanView.onLoadMoreYueDanDataFailed();
            }

            @Override
            public void onSuccess(YueDanBean result) {
                mPlayListsBeanList.addAll(result.getPlayLists());
                mYueDanView.onLoadMoreYueDanDataSuccess();
            }
        });
    }

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
