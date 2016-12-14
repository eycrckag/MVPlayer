package com.itheima.mvplayer.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.service.AudioPlayService;

public class AudioPlayerActivity extends BaseActivity{
    public static final String TAG = "AudioPlayerActivity";

    @Override
    public int getLayoutResID() {
        return R.layout.activity_audio_player;
    }

    @Override
    protected void init() {
        super.init();

        Intent intent = new Intent(getIntent());
        intent.setClass(this, AudioPlayService.class);
        startService(intent);
    }

}
