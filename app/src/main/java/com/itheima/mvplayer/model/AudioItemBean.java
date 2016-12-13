package com.itheima.mvplayer.model;

import android.database.Cursor;
import android.provider.MediaStore;

public class AudioItemBean {
    public static final String TAG = "AudioItemBean";

    private String title;
    private String artist;
    private String size;
    private String duration;

    public static AudioItemBean from(Cursor cursor) {
        AudioItemBean audioItemBean = new AudioItemBean();
        audioItemBean.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        audioItemBean.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        audioItemBean.size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
        audioItemBean.duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        return audioItemBean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
