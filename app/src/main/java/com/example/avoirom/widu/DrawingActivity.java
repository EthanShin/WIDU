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

import java.util.ArrayList;

public class DrawingActivity extends AppCompatActivity {

    Paper mPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        mPaper = (Paper) findViewById(R.id.paper);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_undo:
                mPaper.undo();
                break;
            case R.id.btn_redo:
                mPaper.redo();
                break;
        }
    }

    public static class Vertex{
        float x;
        float y;
        boolean isDraw;

        public Vertex(float x, float y, boolean isDraw){
            this.x = x;
            this.y = y;
            this.isDraw = isDraw;
        }
    }

    public static class Paper extends View {

        private Paint paint = new Paint();
        private ArrayList<Vertex> mShapes = new ArrayList<Vertex>();
        private ArrayList<Vertex> mRedoShapes = new ArrayList<Vertex>();

        Path path = new Path();

        float y ;
        float x ;

        public Paper(Context context, AttributeSet attrs) {
            super(context, attrs);

            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(3);

            mRedoShapes = new ArrayList<Vertex>();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mShapes.add(new Vertex(event.getX(), event.getY(), false));
                mRedoShapes.add(new Vertex(event.getX(), event.getY(), false));

                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mShapes.add(new Vertex(event.getX(), event.getY(), true));
                mRedoShapes.add(new Vertex(event.getX(), event.getY(), true));
                invalidate();

                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {

            }
            return false;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for(int i = 0; i < mShapes.size(); i++) {
                if(mShapes.get(i).isDraw) {
                    canvas.drawLine(mShapes.get(i - 1).x, mShapes.get(i - 1).y,
                            mShapes.get(i).x, mShapes.get(i).y, paint);
                }
            }
        }

        public void undo() {
            int idx = 0;
            int mShapesSize = mShapes.size();

            for(idx = mShapesSize - 1; idx >= 0; idx--) {
                if(mShapes.get(idx).isDraw) {
                    mShapes.remove(idx);
                }
                else {
                    mShapes.remove(idx);
                    break;
                }
            }
            invalidate();
        }

        public void redo() {
            boolean isFirstFalse = true;

            int idx = 0;
            int mShapesSize = mShapes.size();
            int mRedoSize = mRedoShapes.size();

            for(idx = mShapesSize; idx < mRedoSize; idx++) {
                if(mRedoShapes.get(idx).isDraw) {
                    mShapes.add(mRedoShapes.get(idx));
                }
                else {
                    if(isFirstFalse) {
                        mShapes.add(mRedoShapes.get(idx));
                        isFirstFalse = false;
                    }
                    else {
                        break;
                    }
                }
            }
            invalidate();
        }
    }
}