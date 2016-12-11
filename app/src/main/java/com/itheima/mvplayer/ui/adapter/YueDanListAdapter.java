package com.itheima.mvplayer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.itheima.mvplayer.model.YueDanBean;
import com.itheima.mvplayer.widget.YueDanListItemView;

import java.util.List;

public class YueDanListAdapter extends RecyclerView.Adapter {
    public static final String TAG = "YueDanListAdapter";

    private Context mContext;
    private List<YueDanBean.PlayListsBean> mPlayListsBean;

    public YueDanListAdapter(Context context, List<YueDanBean.PlayListsBean> listsBean) {
        mContext = context;
        mPlayListsBean = listsBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YueDanListItemViewHolder(new YueDanListItemView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    private class YueDanListItemViewHolder extends RecyclerView.ViewHolder {
        private YueDanListItemView mYueDanListItemView;

        public YueDanListItemViewHolder(YueDanListItemView itemView) {
            super(itemView);
            mYueDanListItemView = itemView;
        }
    }
}
