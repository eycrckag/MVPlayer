package com.itheima.mvplayer.ui.activity;

import android.media.MediaPlayer;
import android.util.Log;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.app.Constant;

import java.io.IOException;

public class AudioPlayerActivity extends BaseActivity{
    public static final String TAG = "AudioPlayerActivity";
    private MediaPlayer mMediaPlayer;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_audio_player;
    }

    @Override
    protected void init() {
        super.init();
        mMediaPlayer = new MediaPlayer();
        String path = getIntent().getStringExtra(Constant.Extra.AUDIO_PATH);
        try {
            Log.d(TAG, "init: audio path " + path);
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
    }

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mMediaPlayer.start();
        }
    };
}
