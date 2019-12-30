package com.example.zc.rat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @auth zc
 * @create 19-12-29-上午11:31
 * @des 波形图ｖｉｅｗ
 */
public class FreWAVView extends View {

    private Paint paint;

    public FreWAVView(Context context) {
        this(context, null);
    }

    public FreWAVView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FreWAVView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }


    private float verticalStep = 1;
    private float horizentalStep = 1;

    public void setMax(double max){
        verticalStep = (float) (getHeight()/max);
    }

    double[] frameLevel;

    public void setData(double[] frameLevel) {
        if (frameLevel == null) {
            return;
        }
        this.frameLevel = frameLevel;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (frameLevel == null) {
            return;
        }
        for (int i = 0; i < frameLevel.length; i++) {
            float startX = i * horizentalStep;
            float startY = getHeight() - 1;
            canvas.drawLine(startX, startY, startX, (float) (startY - frameLevel[i] * verticalStep), paint);
        }

    }
}
