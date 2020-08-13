package com.example.quest2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MovingCircle extends ConstraintLayout {

    float yOffset;

    public MovingCircle(Context context) {
        super(context);
        init(context, null);
    }

    public MovingCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.canvacircle_layout, this);

        final ImageView ivSmall = findViewById(R.id.ivSmall);
        final ImageView ivBig = findViewById(R.id.ivBig);

        yOffset = getToolBarHeight() + getStatusBarHeight();

        ivSmall.setOnTouchListener(new View.OnTouchListener() {
            float bX, bY, bR, sR;
            double distSq;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int[] coordinates = new int[2];
                ivBig.getLocationOnScreen(coordinates);

                bX = coordinates[0] + (ivBig.getWidth() / 2f);
                bY = coordinates[1] + (ivBig.getHeight() / 2f);
                bR = ivBig.getWidth() / 2f;
                sR = view.getWidth() / 2f;

                if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    distSq = Math.sqrt(((bX - event.getRawX()) * (bX - event.getRawX()))
                            + ((bY - event.getRawY()) * (bY - event.getRawY())));

                    if ((distSq + sR) < bR) {
                        view.setX(event.getRawX() - sR);
                        view.setY(event.getRawY() - yOffset - sR);
                    }
                }
                return true;
            }
        });
    }

    public int getToolBarHeight() {
        int[] attrs = new int[]{R.attr.actionBarSize};
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
