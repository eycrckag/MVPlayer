package com.itheima.mvplayer.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.ListView;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.utils.AudioAsyncQueryHandler;

import butterknife.BindView;

public class VBangFragment extends BaseFragment {
    public static final String TAG = "VBangFragment";
    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_vlist;
    }

    @Override
    protected void init() {
        super.init();
        if (hasReadExternalStoragePermission()) {
            loadAudio();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
    }

    private boolean hasReadExternalStoragePermission() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void loadAudio() {
/*        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE};
        Cursor query = contentResolver.query(uri, projection, null, null, null);
        PrintUtils.printCursor(query);*/
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE};
        AudioAsyncQueryHandler audioAsyncQueryHandler = new AudioAsyncQueryHandler(getContext().getContentResolver());
        audioAsyncQueryHandler.startQuery(0, null, uri, projection, null, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadAudio();
                } else {
                    toast(R.string.read_storage_permission_deny);
                }
                break;
        }
    }
}
