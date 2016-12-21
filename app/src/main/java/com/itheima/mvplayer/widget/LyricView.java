package com.itheima.mvplayer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.itheima.mvplayer.R;
import com.itheima.mvplayer.model.LyricBean;
import com.itheima.mvplayer.utils.LyricParser;

import java.util.List;

public class LyricView extends View {
    public static final String TAG = "LyricView";
    private Paint mPaint;
    private Rect mTextRect;

    private float mCenterX;
    private float mCenterY;
    private float mLineHeight;

    private int mHighLightPosition = 0;

    private float mHighLightTextSize;
    private float mNormalTextSize;

    private static final int DEFAULT_DELAY = 1000;

    private String[] mMultipleLyrics = {"啦", "啦啦", "啦啦啦"};

    private List<LyricBean> mLyrics;

    private float mOffset;

    public LyricView(Context context) {
        this(context, null);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHighLightTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        mNormalTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mHighLightTextSize);
        mTextRect = new Rect();
        mLineHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mLyrics == null) {
            drawLoadingText(canvas);
        } else if (mLyrics.size() == 0) {
            drawLyricNotFound(canvas);
        } else {
            drawLyrics(canvas);
        }

    }

    /**
     * 绘制歌词
     * @param canvas 画布
     */
    private void drawLyrics(Canvas canvas) {
        for (int i = 0; i < mLyrics.size(); i++) {
            //初始化画笔
            if (mHighLightPosition == i) {
                mPaint.setColor(Color.GREEN);
                mPaint.setTextSize(mHighLightTextSize);
            } else {
                mPaint.setColor(Color.WHITE);
                mPaint.setTextSize(mNormalTextSize);
            }
            //测量歌词文本
            String text = mLyrics.get(i).getLyric();
            mPaint.getTextBounds(text, 0, text.length(), mTextRect);
            //计算歌词绘制的位置
            float x = mCenterX - mTextRect.width() / 2;
            float y = mCenterY + mTextRect.height() / 2 + (i - mHighLightPosition) * mLineHeight - mOffset;
            canvas.drawText(text, x, y, mPaint);
        }
    }

    private void drawLyricNotFound(Canvas canvas) {
        String lyricNotFound = getResources().getString(R.string.lyric_not_found);
        drawSingleLine(canvas, lyricNotFound);
    }

    private void drawLoadingText(Canvas canvas) {
        String loadingText = getResources().getString(R.string.loading_lyric);
        drawSingleLine(canvas, loadingText);
    }

    private void drawMultipleLines(Canvas canvas) {
        for (int i = 0; i < mMultipleLyrics.length; i++) {
            //Init paint first, then measure text size
            if (mHighLightPosition == i) {
                mPaint.setColor(Color.GREEN);
                mPaint.setTextSize(mHighLightTextSize);
            } else {
                mPaint.setColor(Color.WHITE);
                mPaint.setTextSize(mNormalTextSize);
            }
            String text = mMultipleLyrics[i];
            mPaint.getTextBounds(text, 0, text.length(), mTextRect);
            float x = mCenterX - mTextRect.width() / 2;
            float y = mCenterY + mTextRect.height() / 2 + (i - mHighLightPosition) * mLineHeight;

            canvas.drawText(text, x, y, mPaint);
        }
    }

    private void drawSingleLine(Canvas canvas, String text) {
        mPaint.getTextBounds(text, 0, text.length(), mTextRect);
        mPaint.setColor(Color.WHITE);
        float x = mCenterX - mTextRect.width() / 2;
        float y = mCenterY + mTextRect.height() / 2;
        canvas.drawText(text, x, y, mPaint);
    }


    public void setLyricFilePath(final String lyricFilePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLyrics = LyricParser.parseLyric(lyricFilePath);
                postInvalidate();
            }
        }).start();
    }

    /**
     *
     *  根据当前歌曲播放的进度，计算出高亮歌词的位置
     *
     * @param progress 当前歌曲的播放进度
     * @param duration 歌曲的时长
     */
    public void roll(int progress, int duration) {
        for (int i = 0; i < mLyrics.size(); i++) {
            int start = mLyrics.get(i).getTimestamp();
            int end = 0;
            if (i == mLyrics.size() - 1) {
                end = duration;//如果是最后一行歌词，该行歌词的结束时间为歌曲的时长
            } else {
                end = mLyrics.get(i + 1).getTimestamp();
            }
            //判断当前播放进度是否在该行歌词内
            if (progress > start && progress <= end) {
                mHighLightPosition = i;
                int lineDuration  = end - start;//获取该行歌词的时长
                int passed = progress - start;//获取当前该行歌词已播放了多长时间
                //获取歌词的偏移量
                mOffset = passed * 1.0f / lineDuration * mLineHeight;
                invalidate();
                break;
            }
        }
    }
}
