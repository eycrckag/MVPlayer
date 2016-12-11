package com.itheima.mvplayer.presenter;

import com.itheima.mvplayer.model.HomeItemBean;

import java.util.List;

public interface HomePresenter {
    void loadHomeData();

    List<HomeItemBean> getHomeListItems();

    void refresh();

    void loadMoreHomeData();
}
