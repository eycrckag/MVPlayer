package com.itheima.mvplayer.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.presenter.HomePresenter;
import com.itheima.mvplayer.presenter.impl.HomePresenterImpl;
import com.itheima.mvplayer.ui.adapter.HomeListAdapter;
import com.itheima.mvplayer.view.HomeView;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeView {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private HomeListAdapter mHomeListAdapter;


    private HomePresenter mHomePresenter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        super.init();
        mHomePresenter = new HomePresenterImpl(this);

        initRecyclerView();

        mHomePresenter.loadHomeData();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeListAdapter = new HomeListAdapter(getContext(), mHomePresenter.getHomeListItems());
        mRecyclerView.setAdapter(mHomeListAdapter);
    }

    @Override
    public void onLoadHomeDataFailed() {
        toast(R.string.load_home_data_failed);
    }

    @Override
    public void onLoadHomeDataSuccess() {
        mHomeListAdapter.notifyDataSetChanged();
    }
}
