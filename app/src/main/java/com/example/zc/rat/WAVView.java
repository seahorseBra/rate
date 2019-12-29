package com.example.zc.rat;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @auth zc
 * @create 19-12-29-上午11:31
 * @des 波形图ｖｉｅｗ
 */
public class WAVView extends View {
    public WAVView(Context context) {
        this(context, null);
    }

    public WAVView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WAVView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
