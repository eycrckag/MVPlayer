package com.itheima.mvplayer.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.presenter.YueDanPresenter;
import com.itheima.mvplayer.presenter.impl.YueDanPresenterImpl;
import com.itheima.mvplayer.ui.adapter.YueDanListAdapter;
import com.itheima.mvplayer.view.YueDanView;

import butterknife.BindView;

public class YueDanFragment extends BaseFragment implements YueDanView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private YueDanPresenter mYueDanPresenter;

    private LinearLayoutManager mLinearLayoutManager;

    private YueDanListAdapter mYueDanListAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_yue_list;
    }

    @Override
    protected void init() {
        super.init();
        mYueDanPresenter = new YueDanPresenterImpl(this);

        initRecyclerView();
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(mOnRefreshListener);

        mYueDanPresenter.loadYueDanData();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mYueDanListAdapter = new YueDanListAdapter(getContext(), mYueDanPresenter.getPlayList());
        mRecyclerView.setAdapter(mYueDanListAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onLoadYueDanDataFailed() {
        toast(R.string.load_yue_dan_failed);
    }

    @Override
    public void onLoadYueDanDataSuccess() {
        toast(R.string.load_yue_dan_success);
        mYueDanListAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadMoreYueDanDataFailed() {
        toast(R.string.load_more_yue_dan_data_failed);
    }

    @Override
    public void onLoadMoreYueDanDataSuccess() {
        toast(R.string.load_more_yue_dan_data_success);
        mYueDanListAdapter.notifyDataSetChanged();
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mYueDanPresenter.refresh();
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mLinearLayoutManager.findLastVisibleItemPosition() == mYueDanPresenter.getPlayList().size() - 1) {
                    mYueDanPresenter.loadMoreYueDanData();
                }
            }
        }
    };
}
