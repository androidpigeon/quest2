package com.example.quest2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MovingCircle extends View {

    private Paint paint;
    private float sR = 100;
    private boolean smallTouched = false;
    private PointF screanCenter;
    private PointF sLocation;

    public MovingCircle(Context context) {
        super(context);
        init();
    }

    public MovingCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setUpPaint();
        screanCenter = new PointF(getResources().getDisplayMetrics().widthPixels / 2f, getResources().getDisplayMetrics().heightPixels / 2f);
        sLocation = new PointF(screanCenter.x, screanCenter.y);
    }

    private void setUpPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(screanCenter.x, screanCenter.y, screanCenter.x, paint);
        canvas.drawCircle(sLocation.x, sLocation.y, 100, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouched(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (smallTouched) {
                    double distSq = Math.sqrt(((screanCenter.x - event.getX()) * (screanCenter.x - event.getX()))
                            + ((screanCenter.y - event.getY()) * (screanCenter.y - event.getY())));
                    if ((distSq + sR) < screanCenter.x)
                        sLocation = new PointF(event.getX(), event.getY());
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                smallTouched = false;
                break;
        }
        return true;
    }

    private void isTouched(float eX, float eY) {
        if (Math.sqrt(((sLocation.x - eX) * (sLocation.x - eX))
                + ((sLocation.y - eY) * (sLocation.y - eY))) < sR)
            smallTouched = true;
    }
}
