package com.itheima.mvplayer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mvplayer.R;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    public static final String TAG = "BaseFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResID(), null);
        ButterKnife.bind(this, root);
        return root;
    }

    protected abstract int getLayoutResID();
}