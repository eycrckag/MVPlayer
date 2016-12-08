package com.itheima.mvplayer.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        init();
    }

    protected void init(){}

    public abstract int getLayoutResID();

    protected void goTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        startActivity(intent);
    }
}
