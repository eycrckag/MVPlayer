package com.itheima.mvplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
    public static final String TAG = "SharedPreferenceUtils";

    private static final String FILE_NAME = "mv_player";

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }
}
