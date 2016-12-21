package com.itheima.mvplayer.model;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.CursorAdapter;

import com.itheima.mvplayer.utils.AudioAsyncQueryHandler;

public class MusicManager {
    public static final String TAG = "MusicManager";

    private static MusicManager mAudioManager;

    private CursorAdapter mCursorAdapter;
    private AudioAsyncQueryHandler mAudioAsyncQueryHandler;

    private MusicManager() {}

    public static MusicManager getInstance() {
        if (mAudioManager == null) {
            synchronized (MusicManager.class) {
                if (mAudioManager == null) {
                    mAudioManager = new MusicManager();
                }
            }
        }
        return mAudioManager;
    }

    public void loadAudio(Context context, CursorAdapter cursorAdapter) {
        mCursorAdapter = cursorAdapter;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DISPLAY_NAME};
        mAudioAsyncQueryHandler = new AudioAsyncQueryHandler(context.getContentResolver());
        mAudioAsyncQueryHandler.startQuery(0, cursorAdapter, uri, projection, null, null, null);
    }

    public AudioItemBean getAudioItem(int position) {
        return mAudioAsyncQueryHandler.getAudioItemBeanList().get(position);
    }

    public int getAudioCount() {
        return mAudioAsyncQueryHandler.getAudioItemBeanList().size();
    }
}
