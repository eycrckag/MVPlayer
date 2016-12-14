package com.itheima.mvplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.itheima.mvplayer.app.Constant;
import com.itheima.mvplayer.model.AudioManager;

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
        int position = intent.getIntExtra(Constant.Extra.AUDIO_POSITION, -1);
        String path = AudioManager.getInstance().getAudioItem(position).getData();
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
