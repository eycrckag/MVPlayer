package com.itheima.mvplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.itheima.mvplayer.app.Constant;
import com.itheima.mvplayer.model.AudioManager;

import java.io.IOException;

public class AudioPlayService extends Service {
    public static final String TAG = "AudioPlayService";
    private MediaPlayer mMediaPlayer;
    private AudioPlayerProxy mAudioPlayerProxy;
    private int mPosition;

    public static final String ACTION_START_PLAY = "start";

    private static final int PLAY_MODE_ORDER = 1;
    private static final int PLAY_MODE_RANDOM = 2;
    private static final int PLAY_MODE_SINGLE = 3;

    private int mCurrentMode = PLAY_MODE_ORDER;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAudioPlayerProxy;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioPlayerProxy = new AudioPlayerProxy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPosition = intent.getIntExtra(Constant.Extra.AUDIO_POSITION, -1);
        startPlay();
        return super.onStartCommand(intent, flags, startId);
    }


    public void startPlay() {
        if (mMediaPlayer !=  null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        String path = AudioManager.getInstance().getAudioItem(mPosition).getData();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mMediaPlayer.start();
            notifyStartPlay();
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    };

    private void notifyStartPlay() {
        Intent intent = new Intent(ACTION_START_PLAY);
        intent.putExtra(Constant.Extra.AUDIO_POSITION, mPosition);
        sendBroadcast(intent);
    }


    public class AudioPlayerProxy extends Binder {

        public void togglePlay() {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
        }

        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }

        public void playNext() {
            mPosition ++;
            startPlay();
        }

        public boolean isLast() {
            return mPosition == AudioManager.getInstance().getAudioCount() - 1;
        }

        public boolean isFirst() {
            return mPosition == 0;
        }

        public void playPre() {
            mPosition --;
            startPlay();
        }

        public int  getCurrentPosition() {
            return mPosition;
        }
    }

}
