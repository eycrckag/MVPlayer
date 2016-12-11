package com.itheima.mvplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.itheima.mvplayer.R;

public class YueDanListItemView extends RelativeLayout {
    public static final String TAG = "YueDanListItemView";

    public YueDanListItemView(Context context) {
        this(context, null);
    }

    public YueDanListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_yue_dan_list_item, this);
    }
}
