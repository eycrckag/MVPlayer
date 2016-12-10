package com.itheima.mvplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.itheima.mvplayer.R;

public class HomeListItemView extends RelativeLayout {
    public static final String TAG = "HomeListItemView";

    public HomeListItemView(Context context) {
        this(context, null);
    }

    public HomeListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_home_list_item, this);
    }
}
