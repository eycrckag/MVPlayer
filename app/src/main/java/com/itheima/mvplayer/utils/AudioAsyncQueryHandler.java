package com.itheima.mvplayer.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import com.itheima.mvplayer.ui.adapter.AudioListAdapter;

public class AudioAsyncQueryHandler extends AsyncQueryHandler {
    public static final String TAG = "AudioAsyncQueryHandler";

    public AudioAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        Log.d(TAG, "onQueryComplete: ");
        PrintUtils.printCursor(cursor);
        ((AudioListAdapter)cookie).swapCursor(cursor);
    }
}
