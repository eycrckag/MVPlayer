package com.itheima.mvplayer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itheima.mvplayer.R;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
    }

    public abstract int getLayoutResID();
}
