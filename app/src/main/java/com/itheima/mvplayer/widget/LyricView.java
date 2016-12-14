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

public class LyricView extends View {
    public static final String TAG = "LyricView";
    private Paint mPaint;
    private Rect mTextRect;

    private float mCenterX;
    private float mCenterY;

    public LyricView(Context context) {
        this(context, null);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
        mPaint.setColor(Color.WHITE);
        mTextRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSingleLine(canvas);
    }

    private void drawSingleLine(Canvas canvas) {
        String loadingText = getResources().getString(R.string.loading_lyric);
        mPaint.getTextBounds(loadingText, 0, loadingText.length(), mTextRect);
        float x = mCenterX - mTextRect.width() / 2;
        float y = mCenterY + mTextRect.height() / 2;
        canvas.drawText(loadingText, x, y, mPaint);
    }
}
