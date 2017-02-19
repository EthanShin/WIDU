package com.example.avoirom.widu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
    }

    public static class Paper extends View {

        Paint paint = new Paint();
        Path path = new Path();

        float y ;
        float x ;

        public Paper(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected void onDraw(Canvas canvas) {
            paint.setStrokeWidth(3);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            x = event.getX();
            y = event.getY();

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    x = event.getX();
                    y = event.getY();
                    path.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }

            invalidate();

            return true;
        }
    }
}