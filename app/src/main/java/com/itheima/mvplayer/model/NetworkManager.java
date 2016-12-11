package com.itheima.mvplayer.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.mvplayer.utils.URLProviderUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {
    public static final String TAG = "NetworkManager";

    private Gson mGson;

    private static NetworkManager mNetworkManager;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static final int DEFAULT_PAGE_SIZE = 10;

    private NetworkManager() {
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
    }


    public static NetworkManager getInstance() {
        if (mNetworkManager == null) {
            synchronized (NetworkManager.class) {
                if (mNetworkManager == null) {
                    mNetworkManager = new NetworkManager();
                }
            }
        }
        return mNetworkManager;
    }

    public void loadHomeData(NetworkCallback callback) {
        loadHomeData(0, callback);
    }

    public void loadHomeData(int offset, final NetworkCallback callback) {
        Request request = new Request.Builder().get().url(URLProviderUtil.getHomeUrl(offset, DEFAULT_PAGE_SIZE)).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultString = response.body().string();//Notice it not toString(), is string()
                Log.d(TAG, "onResponse: " + resultString );
                final List<HomeItemBean> result = mGson.fromJson(resultString, new TypeToken<List<HomeItemBean>>(){}.getType());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    }
                });
            }
        });
    }


    public void loadYueData(final NetworkCallback callback) {
        Request request = new Request.Builder().url(URLProviderUtil.getYueDanUrl(0, DEFAULT_PAGE_SIZE)).get().build();
        Log.d(TAG, "loadYueData: " + URLProviderUtil.getYueDanUrl(0, DEFAULT_PAGE_SIZE));
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                if (callback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                YueDanBean yueDanBean = mGson.fromJson(response.body().string(), YueDanBean.class);
                Log.d(TAG, "onResponse: " + yueDanBean.getTotalCount());
                if (callback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
//                            callback.onSuccess();
                        }
                    });
                }
            }
        });
    }
}
