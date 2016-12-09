package com.itheima.mvplayer.model;

public interface NetworkCallback<T> {

    void onError();

    void onSuccess(T result);
}
