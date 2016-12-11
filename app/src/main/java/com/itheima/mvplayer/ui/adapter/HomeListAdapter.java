package com.itheima.mvplayer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.itheima.mvplayer.model.HomeItemBean;
import com.itheima.mvplayer.widget.HomeListItemView;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeListItemViewHolder> {
    public static final String TAG = "HomeListAdapter";

    private Context mContext;
    private List<HomeItemBean> mHomeItemBeanList;

    public HomeListAdapter(Context context, List<HomeItemBean> homeItemBeanList) {
        mContext = context;
        mHomeItemBeanList = homeItemBeanList;
    }

    @Override
    public HomeListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeListItemViewHolder(new HomeListItemView(mContext));
    }

    @Override
    public void onBindViewHolder(HomeListItemViewHolder holder, int position) {
        holder.mHomeListItemView.bindView(mHomeItemBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHomeItemBeanList.size();
    }

    ;public class HomeListItemViewHolder extends RecyclerView.ViewHolder {

        private HomeListItemView mHomeListItemView;

        public HomeListItemViewHolder(HomeListItemView itemView) {
            super(itemView);
            mHomeListItemView = itemView;
        }
    }
}
