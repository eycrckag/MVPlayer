package com.itheima.mvplayer.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.model.AreaBean;
import com.itheima.mvplayer.network.MVAreaRequest;
import com.itheima.mvplayer.network.NetworkListener;
import com.itheima.mvplayer.ui.adapter.MVAdapter;
import com.itheima.mvplayer.utils.URLProviderUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MVFragment extends BaseFragment{
    public static final String TAG = "MVFragment";
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private MVAdapter mMVAdapter;

    private List<AreaBean> mAreas;


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mv;
    }

    @Override
    protected void init() {
        super.init();
        mAreas = new ArrayList<AreaBean>();
        mMVAdapter = new MVAdapter(getChildFragmentManager(), mAreas);
        mViewPager.setAdapter(mMVAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        loadAreaData();
    }

    public void loadAreaData() {
        new MVAreaRequest(URLProviderUtil.getMVareaUrl(), mNetworkListener).execute();
    }

    private NetworkListener<List<AreaBean>> mNetworkListener = new NetworkListener<List<AreaBean>>() {
        @Override
        public void onError(String errorMsg) {
        }

        @Override
        public void onSuccess(List<AreaBean> result) {
            mAreas.addAll(result);
            mMVAdapter.notifyDataSetChanged();

        }
    };

}
