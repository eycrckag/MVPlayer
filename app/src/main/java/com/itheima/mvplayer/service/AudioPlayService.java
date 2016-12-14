package com.itheima.mvplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.itheima.mvplayer.app.Constant;

import java.io.IOException;

public class AudioPlayService extends Service {
    public static final String TAG = "AudioPlayService";
    private MediaPlayer mMediaPlayer;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMediaPlayer = new MediaPlayer();
        String path = intent.getStringExtra(Constant.Extra.AUDIO_PATH);
        try {
            Log.d(TAG, "init: audio path " + path);
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        return super.onStartCommand(intent, flags, startId);
    }


    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mMediaPlayer.start();
        }
    };

}
