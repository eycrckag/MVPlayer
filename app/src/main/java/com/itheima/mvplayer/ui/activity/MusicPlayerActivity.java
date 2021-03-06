package com.itheima.mvplayer.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.app.Constant;
import com.itheima.mvplayer.model.AudioItemBean;
import com.itheima.mvplayer.model.MusicManager;
import com.itheima.mvplayer.service.MusicPlayerService;
import com.itheima.mvplayer.utils.SharedPreferenceUtils;
import com.itheima.mvplayer.utils.StringUtils;
import com.itheima.mvplayer.widget.LyricView;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicPlayerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_artist)
    TextView mTvArtist;
    @BindView(R.id.iv_animation)
    ImageView mIvAnimation;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.iv_play_mode)
    ImageView mIvPlayMode;
    @BindView(R.id.iv_pre)
    ImageView mIvPre;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.iv_next)
    ImageView mIvNext;

    @BindView(R.id.lyric)
    LyricView mLyricView;

    private static final int DEFAULT_DELAY = 500;
    private static final int UPDATE_LYRIC_INTERVAL = 100;

    private MusicPlayerService.AudioPlayerProxy mAudioPlayerProxy;
    private Handler mHandler = new Handler();

    @Override
    public int getLayoutResID() {
        return R.layout.activity_audio_player;
    }

    @Override
    protected void init() {
        super.init();
        initView();
        registerBroadcast();
        startService();
    }

    private void initView() {
        int position = getIntent().getIntExtra(Constant.Extra.AUDIO_POSITION, -1);
        AudioItemBean itemBean = MusicManager.getInstance().getAudioItem(position);
        mTvTitle.setText(itemBean.getTitle());
        mTvArtist.setText(itemBean.getArtist());
        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        updatePlayModeView(SharedPreferenceUtils.getInt(this, Constant.SP.PLAY_MODE));
    }

    private void startService() {
        Intent intent = new Intent(getIntent());
        intent.setClass(this, MusicPlayerService.class);
        startService(intent);
    }


    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        //监听开始播放的ACTION
        intentFilter.addAction(MusicPlayerService.ACTION_START_PLAY);
        registerReceiver(mUpdateReceiver, intentFilter);
    }

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicPlayerService.ACTION_START_PLAY)) {
                int pos = intent.getIntExtra(Constant.Extra.AUDIO_POSITION, -1);
                updateStartPlay(pos);
            }
        }
    };

    private void updateStartPlay() {
        AnimationDrawable animation = (AnimationDrawable) mIvAnimation.getBackground();
        animation.start();
        mIvPlay.setBackgroundResource(R.drawable.selector_btn_audio_play);
    }

    private void updateStartPlay(int pos) {
        AudioItemBean audioItem = MusicManager.getInstance().getAudioItem(pos);
        mTvTitle.setText(audioItem.getTitle());
        mTvArtist.setText(audioItem.getArtist());
        mSeekBar.setMax(audioItem.getDuration());
        updateStartPlay();
        startUpdateProgress();

        String directory = "/storage/sdcard0/Download/audio/";
        String lyricPath = directory + audioItem.getDisplayName().split("\\.")[0] + ".lrc" ;
        mLyricView.setLyricFilePath(lyricPath);
        startUpdateLyric();
    }

    private void startUpdateLyric() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAudioPlayerProxy != null) {
                    mLyricView.roll(mAudioPlayerProxy.getProgress(), mAudioPlayerProxy.getDuration());
                    startUpdateLyric();
                }
            }
        }, UPDATE_LYRIC_INTERVAL);
    }

    private void startUpdateProgress() {
        if (mAudioPlayerProxy != null) {
            int progress = mAudioPlayerProxy.getProgress();
            int duration = mAudioPlayerProxy.getDuration();
            if (progress > duration) {
                progress = duration;
            }
            String time = StringUtils.formatDuration(progress) + "/" + StringUtils.formatDuration(duration);
            mTime.setText(time);
            mSeekBar.setProgress(mAudioPlayerProxy.getProgress());
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startUpdateProgress();
            }
        }, DEFAULT_DELAY);
    }

    private void stopUpdateProgress() {
        mHandler.removeCallbacksAndMessages(null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
        stopUpdateProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUpdateReceiver);
    }

    @OnClick({R.id.iv_play_mode, R.id.iv_pre, R.id.iv_play, R.id.iv_next, R.id.back, R.id.audio_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_play_mode:
                mAudioPlayerProxy.updatePlayMode();
                updatePlayModeView(mAudioPlayerProxy.getPlayMode());
                break;
            case R.id.iv_pre:
                if (mAudioPlayerProxy.isFirst()) {
                    toast(R.string.is_first_audio);
                } else {
                    mAudioPlayerProxy.playPre();
                }
                break;
            case R.id.iv_play:
                mAudioPlayerProxy.togglePlay();
                if (mAudioPlayerProxy.isPlaying()) {
                    updateStartPlay();
                }  else {
                    updatePausePlay();
                }
                break;
            case R.id.iv_next:
                if (mAudioPlayerProxy.isLast()) {
                    toast(R.string.is_last_audio);
                } else {
                    mAudioPlayerProxy.playNext();
                }
                break;
            case R.id.audio_list:
                finish();
                break;
        }
    }

    private void updatePlayModeView(int playMode) {
        switch (playMode) {
            case MusicPlayerService.PLAY_MODE_ORDER:
                mIvPlayMode.setBackgroundResource(R.drawable.selector_btn_playmode_order);
                break;
            case MusicPlayerService.PLAY_MODE_RANDOM:
                mIvPlayMode.setBackgroundResource(R.drawable.selector_btn_playmode_random);
                break;
            case MusicPlayerService.PLAY_MODE_SINGLE:
                mIvPlayMode.setBackgroundResource(R.drawable.selector_btn_playmode_single);
                break;
        }
    }

    private void updatePausePlay() {
        AnimationDrawable animationDrawable = (AnimationDrawable) mIvAnimation.getBackground();
        animationDrawable.stop();
        mIvPlay.setBackgroundResource(R.drawable.selector_btn_audio_pause);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAudioPlayerProxy = (MusicPlayerService.AudioPlayerProxy) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAudioPlayerProxy = null;
        }
    };

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mAudioPlayerProxy.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mAudioPlayerProxy.pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mAudioPlayerProxy.start();
        }
    };
}
