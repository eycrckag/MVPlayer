package com.itheima.mvplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mvplayer.R;

public class MVItemFragment extends BaseFragment {

    public static MVItemFragment newInstance(String code) {
        MVItemFragment itemFragment = new MVItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String code = arguments.getString("code");
        Log.d(TAG, "onCreateView: " + code);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mv_item;
    }
}
