package com.example.zc.rat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

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
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }


    public void setData(WaveFileReader reader) {
        init(reader);
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    private double verticalStep = 1;
    private float horizentalStep = 1;

    WaveFileReader mReader;
    List<Float> frameLevel;

    private void init(WaveFileReader reader) {
        if (reader == null) {
            return;
        }
        this.mReader = reader;
        new Thread(new Runnable() {
            @Override
            public void run() {

                frameLevel = mReader.getFrameLevel(0);
                int rateLevel = mReader.getRateMinLevel();
                verticalStep = getHeight() * 1f / (mReader.max - mReader.min);
                horizentalStep = getWidth() * 1f / frameLevel.size();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(horizentalStep);
                if (cache == null) {
                    cache = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                }
                Canvas canvas1 = new Canvas(cache);
                canvas1.drawColor(Color.GREEN);
                for (int i = 0; i < frameLevel.size(); i++) {
                    float startX = i * horizentalStep;
                    float startY = getHeight();
                    canvas1.drawLine(startX, startY, startX, (float) (startY - frameLevel.get(i) * verticalStep), paint);
//                    if (frameLevel[i] > rateLevel) {
//                        canvas1.drawCircle(startX, 20, 10, paint);
//                    }
                }

                handler.sendEmptyMessage(0);
            }
        }).start();

    }

    Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            invalidate();
            return true;
        }
    });

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            cache = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            init(mReader);
        }
    }

    Bitmap cache = null;

    Paint cachePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cache != null && !cache.isRecycled()) {
            canvas.drawBitmap(cache, 0, 0, cachePaint);
        }

        if (percent > 0) {
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            float start = getWidth() * percent;
            canvas.drawLine(start, getHeight(), start, 0, paint);

            int index = (int) (frameLevel.size() * percent);
            double i = frameLevel.get(index);
            if (i > mReader.getRateMinLevel()) {
                paint.setColor(Color.RED);
                canvas.drawCircle(50, 50, 10, paint);
            }

        }

    }
}
