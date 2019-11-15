package com.example.webapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class CanvasClass extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder surfaceHolder = null;
    private Paint paint = null;
    private float circleX = 0;
    private float circleY = 0;
    Random r = new Random();
    public int xCoor = r.nextInt((1000 - 30) + 1) + 30;
    public int yCoor = r.nextInt((1500 - 30) + 1) + 30;

    public CanvasClass(Context context) {
        super(context);

        surfaceHolder = getHolder();

        paint = new Paint();
        paint.setColor(Color.RED);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        paint = null;

    }
}
