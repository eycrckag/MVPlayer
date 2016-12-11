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

    private LinearLayoutManager mLinearLayoutManager;
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
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(mOnRefreshListener);
        mHomePresenter.loadHomeData();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mHomeListAdapter = new HomeListAdapter(getContext(), mHomePresenter.getHomeListItems());
        mRecyclerView.setAdapter(mHomeListAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onLoadHomeDataFailed() {
        toast(R.string.load_home_data_failed);
    }

    @Override
    public void onLoadHomeDataSuccess() {
        toast(R.string.load_home_data_success);
        mHomeListAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mHomePresenter.refresh();
        }
    };


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mLinearLayoutManager.findLastVisibleItemPosition() == mHomePresenter.getHomeListItems().size() - 1) {
                    mHomePresenter.loadMoreHomeData();
                }
            }
        }
    };

    @Override
    public void onLoadMoreHomeDataFailed() {
        toast(R.string.load_more_home_data_failed);
    }

    @Override
    public void onLoadMoreHomeDataSuccess() {
        toast(R.string.load_more_home_data_success);
        mHomeListAdapter.notifyDataSetChanged();
    }
}
