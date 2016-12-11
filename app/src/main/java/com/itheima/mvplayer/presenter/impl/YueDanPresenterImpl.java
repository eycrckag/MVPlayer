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
