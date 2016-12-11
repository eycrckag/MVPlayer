package com.itheima.mvplayer.ui.fragment;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.presenter.YuePresenter;
import com.itheima.mvplayer.presenter.impl.YuePresenterImpl;
import com.itheima.mvplayer.view.YueListView;

public class YueListFragment extends BaseFragment implements YueListView{

    private YuePresenter mYuePresenter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_yue_list;
    }

    @Override
    protected void init() {
        super.init();
        mYuePresenter = new YuePresenterImpl(this);

        mYuePresenter.loadYueData();
    }
}
