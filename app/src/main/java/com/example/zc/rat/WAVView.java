package com.example.zc.rat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @auth zc
 * @create 19-12-29-上午11:31
 * @des 波形图ｖｉｅｗ
 */
public class WAVView extends View {

    private Paint paint;
    private float percent;

    public WAVView(Context context) {
        this(context, null);
    }

    public WAVView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WAVView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int[] data = null;

    public void setData(int[] data) {
        if (data == null) {
            return;
        }
        init(data);
        invalidate();
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    private float verticalStep = 1;
    private float horizentalStep = 1;
    private int simpleSize = 100;

    private void init(int[] data) {
        if (data == null) {
            return;
        }
        int max = 0;
        int[] result = new int[simpleSize];
        int step = data.length / simpleSize;
        for (int i = 0; i < simpleSize; i++) {
            int value = data[i * step];
            if (max < value) {
                max = value;
            }
            result[i] = value;
        }
        verticalStep = getHeight() * 1f / max;
        horizentalStep = getWidth() * 1f / data.length;
        isDirty = true;
        this.data = result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            cache = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        }
    }

    Bitmap cache = null;
    boolean isDirty = false;

    Paint cachePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null) {
            return;
        }
        if (paint == null) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
        }

        if (isDirty) {
            paint.setColor(Color.RED);
            paint.setStrokeWidth(horizentalStep);
            if (cache == null) {
                cache = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas1 = new Canvas(cache);
            canvas1.drawColor(Color.GREEN);
            for (int i = 0; i < data.length; i++) {
                float startX = i * horizentalStep;
                float startY = getHeight() - 1;
                canvas1.drawLine(startX, startY, startX, startY - data[i] * verticalStep, paint);
            }
            isDirty = false;
        } else if (cache != null && !cache.isRecycled()) {
            canvas.drawBitmap(cache, 0, 0, cachePaint);
        }


//        if (percent > 0) {
//            paint.setStrokeWidth(1);
//            paint.setColor(Color.BLACK);
//            float start = getWidth() * percent;
//            canvas.drawLine(start, getHeight(), start, 0, paint);
//        }

    }
}
