package com.itheima.mvplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.itheima.mvplayer.presenter.BaseListPresenter;
import com.itheima.mvplayer.presenter.impl.MVPagePresenterImpl;
import com.itheima.mvplayer.view.BaseListView;

public class MVPageFragment extends BaseListFragment {

    private String mCode;

    public static MVPageFragment newInstance(String code) {
        MVPageFragment itemFragment = new MVPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mCode = arguments.getString("code");
    }

    @Override
    public RecyclerView.Adapter getListAdapter() {
        return null;
    }

    @Override
    public BaseListPresenter getPresenter(BaseListView view) {
        return new MVPagePresenterImpl(view, mCode);
    }
}
