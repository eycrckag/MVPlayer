package com.itheima.mvplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.itheima.mvplayer.R;

public class MVPageListItemView extends RelativeLayout {
    public static final String TAG = "MVPageListItemView";

    public MVPageListItemView(Context context) {
        this(context, null);
    }

    public MVPageListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_mv_page_list_item, this);
    }
}
