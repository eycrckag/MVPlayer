package com.itheima.mvplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.itheima.mvplayer.app.Constant;
import com.itheima.mvplayer.model.MusicManager;

import java.io.IOException;
import java.util.Random;

public class MusicPlayerService extends Service {
    public static final String TAG = "MusicPlayerService";
    private MediaPlayer mMediaPlayer;
    private AudioPlayerProxy mAudioPlayerProxy;

    private static final int POSITION_NOT_FOUND = -1;
    private int mPosition = POSITION_NOT_FOUND;

    public static final String ACTION_START_PLAY = "start";
    public static final String ACTION_COMPLETE_PLAY = "complete";


    public static final int PLAY_MODE_ORDER = 0;
    public static final int PLAY_MODE_RANDOM = 1;
    public static final int PLAY_MODE_SINGLE = 2;

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
        int position = intent.getIntExtra(Constant.Extra.AUDIO_POSITION, -1);
        if (position != POSITION_NOT_FOUND) {
            //如果MusicPlayService正在播放的歌曲就是用户打开播放界面要播放的歌曲，则直接通知Activity已经开始播放，
            //直接更新进度
            if (mPosition == position) {
                notifyStartPlay();
            } else {
                mPosition = position;
                startPlay();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    public void startPlay() {
        //当开始播放一首歌曲时，如果MediaPlayer不为空，表示之前已经播放了一首歌曲，这时需要重置MediaPlayer
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        String path = MusicManager.getInstance().getAudioItem(mPosition).getData();
        try {
            mMediaPlayer.setDataSource(path);//设置歌曲路径
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);//设置准备监听器
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);//设置播放结束监听器
            mMediaPlayer.prepare();//准备
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
            notifyCompletePlay();
            playByMode();
        }
    };

    private void playByMode() {
        int count = MusicManager.getInstance().getAudioCount();
        switch (mCurrentMode) {
            case PLAY_MODE_ORDER:
                mPosition = (mPosition + 1) % count;
                break;
            case PLAY_MODE_RANDOM:
                mPosition = new Random().nextInt(count);
                break;
            case PLAY_MODE_SINGLE:
                break;
        }
        Log.d(TAG, "playByMode: mode" + mCurrentMode + " position " + mPosition);
        startPlay();
    }

    private void notifyCompletePlay() {
        Intent intent = new Intent(ACTION_COMPLETE_PLAY);
        sendBroadcast(intent);
    }

    private void notifyStartPlay() {
        Intent intent = new Intent(ACTION_START_PLAY);
        intent.putExtra(Constant.Extra.AUDIO_POSITION, mPosition);
        sendBroadcast(intent);
    }


    public class AudioPlayerProxy extends Binder {


        /**
         *  播放或者暂停
         */
        public void togglePlay() {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
        }

        /**
         * 是否正在播放
         */
        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }

        /**
         *  播放下一首
         */
        public void playNext() {
            mPosition++;
            startPlay();
        }

        /**
         *  是否播放到最后一首
         */
        public boolean isLast() {
            return mPosition == MusicManager.getInstance().getAudioCount() - 1;
        }

        /**
         *
         * @return
         */
        public boolean isFirst() {
            return mPosition == 0;
        }

        public void playPre() {
            mPosition--;
            startPlay();
        }

        public int getProgress() {
            if (mMediaPlayer != null) {
                return mMediaPlayer.getCurrentPosition();
            }
            return 0;
        }

        public int getDuration() {
            if (mMediaPlayer != null) {
                return mMediaPlayer.getDuration();
            }
            return 0;
        }

        public void seekTo(int progress) {
            mMediaPlayer.seekTo(progress);
        }

        public void pause() {
            mMediaPlayer.pause();
        }

        public void start() {
            mMediaPlayer.start();
        }

        public void updatePlayMode() {
            mCurrentMode = (mCurrentMode + 1) % 3;
        }

        public int getPlayMode() {
            return mCurrentMode;
        }
    }

}
