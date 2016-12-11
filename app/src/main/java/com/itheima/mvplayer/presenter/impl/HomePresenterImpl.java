package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.model.HomeItemBean;
import com.itheima.mvplayer.network.HomeRequest;
import com.itheima.mvplayer.network.MVPlayerRequest;
import com.itheima.mvplayer.network.NetworkListener;
import com.itheima.mvplayer.presenter.HomePresenter;
import com.itheima.mvplayer.view.HomeView;

import java.util.ArrayList;
import java.util.List;


public class HomePresenterImpl implements HomePresenter {
    public static final String TAG = "HomePresenterImpl";

    private HomeView mHomeView;

    private List<HomeItemBean> mHomeItemBeanList;

    private boolean mHasMoreData = true;

    public HomePresenterImpl(HomeView homeView) {
        mHomeView = homeView;
        mHomeItemBeanList = new ArrayList<HomeItemBean>();
    }

    @Override
    public void loadHomeData() {
        if (mHomeItemBeanList.size() > 0) {
            mHomeView.onLoadHomeDataSuccess();
            return;
        }
        HomeRequest.getRequest(mListNetworkListener).execute();
    }

    private NetworkListener<List<HomeItemBean>> mListNetworkListener = new NetworkListener<List<HomeItemBean>>() {
        @Override
        public void onError(String errorMsg) {
            mHomeView.onLoadHomeDataFailed();
        }

        @Override
        public void onSuccess(List<HomeItemBean> result) {
            mHomeItemBeanList.addAll(result);
            mHomeView.onLoadHomeDataSuccess();
        }
    };

    @Override
    public List<HomeItemBean> getHomeListItems() {
        return mHomeItemBeanList;
    }

    @Override
    public void refresh() {
        mHomeItemBeanList.clear();
        loadHomeData();
    }

    @Override
    public void loadMoreHomeData() {
        if (mHasMoreData) {
            HomeRequest.getLoadMoreRequest(mHomeItemBeanList.size(), mLoadMoreListener).execute();
        } else {
            mHomeView.onNoMoreData();
        }

    }

    private NetworkListener<List<HomeItemBean>> mLoadMoreListener = new NetworkListener<List<HomeItemBean>>() {
        @Override
        public void onError(String errorMsg) {
            mHomeView.onLoadMoreHomeDataFailed();
        }

        @Override
        public void onSuccess(List<HomeItemBean> result) {
            mHasMoreData = (result.size() == MVPlayerRequest.DEFAULT_PAGE_SIZE);
            mHomeItemBeanList.addAll(result);
            mHomeView.onLoadMoreHomeDataSuccess();
        }
    };

}
