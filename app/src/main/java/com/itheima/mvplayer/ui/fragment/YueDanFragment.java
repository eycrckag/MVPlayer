package com.itheima.mvplayer.ui.fragment;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.presenter.YueDanPresenter;
import com.itheima.mvplayer.presenter.impl.YueDanPresenterImpl;
import com.itheima.mvplayer.view.YueDanView;

public class YueDanFragment extends BaseFragment implements YueDanView {

    private YueDanPresenter mYueDanPresenter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_yue_list;
    }

    @Override
    protected void init() {
        super.init();
        mYueDanPresenter = new YueDanPresenterImpl(this);

        mYueDanPresenter.loadYueDanData();
    }

    @Override
    public void onLoadYueDanDataFailed() {
        toast(R.string.load_yue_dan_failed);
    }

    @Override
    public void onLoadYueDanDataSuccess() {
        toast(R.string.load_yue_dan_success);
    }
}
