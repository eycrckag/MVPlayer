package com.itheima.mvplayer.model;

public class NetworkManager {
    public static final String TAG = "NetworkManager";

    private static NetworkManager mNetworkManager;

    private NetworkManager() {}

    public NetworkManager getInstance() {
        if (mNetworkManager == null) {
            synchronized (NetworkManager.class) {
                if (mNetworkManager == null) {
                    mNetworkManager = new NetworkManager();
                }
            }
        }
        return mNetworkManager;
    }


}
