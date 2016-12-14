package com.itheima.mvplayer.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.app.Constant;
import com.itheima.mvplayer.model.AudioItemBean;
import com.itheima.mvplayer.model.AudioManager;
import com.itheima.mvplayer.service.AudioPlayService;

import butterknife.BindView;
import butterknife.OnClick;

public class AudioPlayerActivity extends BaseActivity {

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

    @Override
    public int getLayoutResID() {
        return R.layout.activity_audio_player;
    }

    @Override
    protected void init() {
        super.init();
        int position = getIntent().getIntExtra(Constant.Extra.AUDIO_POSITION, -1);
        AudioItemBean itemBean = AudioManager.getInstance().getAudioItem(position);
        mTvTitle.setText(itemBean.getTitle());
        mTvArtist.setText(itemBean.getArtist());
        startService();
    }

    private void startService() {
        Intent intent = new Intent(getIntent());
        intent.setClass(this, AudioPlayService.class);
        startService(intent);
    }

    @OnClick({R.id.iv_play_mode, R.id.iv_pre, R.id.iv_play, R.id.iv_next, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play_mode:
                break;
            case R.id.iv_pre:
                break;
            case R.id.iv_play:
                break;
            case R.id.iv_next:
                break;
            case R.id.audio_list:
                break;
        }
    }
}
