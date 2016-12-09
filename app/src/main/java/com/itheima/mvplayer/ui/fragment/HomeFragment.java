package com.itheima.mvplayer.ui.fragment;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.presenter.HomePresenter;
import com.itheima.mvplayer.presenter.impl.HomePresenterImpl;
import com.itheima.mvplayer.view.HomeView;

public class HomeFragment extends BaseFragment implements HomeView{
    public static final String TAG = "HomeFragment";


    private HomePresenter mHomePresenter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        super.init();
        mHomePresenter = new HomePresenterImpl(this);
        mHomePresenter.loadHomeData();
    }
}
