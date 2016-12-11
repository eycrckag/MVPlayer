package com.itheima.mvplayer.presenter;

import com.itheima.mvplayer.model.YueDanBean;

import java.util.List;

public interface YueDanPresenter {

    void loadYueDanData();

    void loadMoreYueDanData();

    void refresh();

    List<YueDanBean.PlayListsBean> getPlayList();
}
