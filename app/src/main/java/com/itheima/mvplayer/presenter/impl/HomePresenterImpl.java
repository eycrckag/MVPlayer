package com.itheima.mvplayer.presenter.impl;

import com.itheima.mvplayer.model.HomeItemBean;
import com.itheima.mvplayer.model.NetworkCallback;
import com.itheima.mvplayer.model.NetworkManager;
import com.itheima.mvplayer.presenter.HomePresenter;
import com.itheima.mvplayer.view.HomeView;

import java.util.ArrayList;
import java.util.List;


public class HomePresenterImpl implements HomePresenter {
    public static final String TAG = "HomePresenterImpl";

    private HomeView mHomeView;

    private List<HomeItemBean> mHomeItemBeanList;

    private boolean mHasMoreData;

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
        NetworkManager.getInstance().loadHomeData(new NetworkCallback<List<HomeItemBean>>() {
            @Override
            public void onError() {
                mHomeView.onLoadHomeDataFailed();
            }

            @Override
            public void onSuccess(List<HomeItemBean> result) {
                mHomeItemBeanList.addAll(result);
                mHomeView.onLoadHomeDataSuccess();
            }
        });
    }

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
            NetworkManager.getInstance().loadHomeData(mHomeItemBeanList.size() + 1, new NetworkCallback<List<HomeItemBean>>() {
                @Override
                public void onError() {
                    mHomeView.onLoadMoreHomeDataFailed();
                }

                @Override
                public void onSuccess(List<HomeItemBean> result) {
                    mHasMoreData = (result.size() == NetworkManager.DEFAULT_PAGE_SIZE);
                    mHomeItemBeanList.addAll(result);
                    mHomeView.onLoadMoreHomeDataSuccess();
                }
            });
        } else {
            mHomeView.onNoMoreData();
        }

    }


}
