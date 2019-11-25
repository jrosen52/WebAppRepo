package com.example.webapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.Math.abs;

public class CanvasClass extends SurfaceView implements Runnable
{
    Thread mGameThread = null;
    private SurfaceHolder surfaceHolder = null;
    private Paint paint = null;
    Random r = new Random();
    public int xLoc;
    public int yLoc;
    long mFPS;
    public boolean gameStarted = false;
    public boolean targetAdded = false;
    int score;
    int screenX, screenY;
    TextView textView;

    boolean mPaused = true;

    public CanvasClass(Context context) {
        super(context);
        surfaceHolder = getHolder();

        paint = new Paint();
        paint.setColor(Color.RED);

    }

    public void pause() {
        gameStarted = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        gameStarted = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    public void drawTarget(int xTar,int yTar)
    {
        //Toast.makeText(getApplicationContext(), "X: " +xCoor + "Y: " + yCoor, Toast.LENGTH_SHORT).show();
        surfaceHolder = getHolder();

        // Get and lock canvas object from surfaceHolder.
        Canvas canvas = surfaceHolder.lockCanvas();

        Paint surfaceBackground = new Paint();
        // Set the surfaceview background color.
        surfaceBackground.setColor(Color.BLUE);
        // Draw the surfaceview background color.
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), surfaceBackground);

        // Draw the circle.
        //Random r1 = new Random();
        //int color = r1.nextInt((255 - 0) + 1) + 0;
        int rValue, bValue;
        paint.setARGB(255,255,0,0);

        canvas.drawCircle(xTar, yTar, 80, paint);

        // Unlock the canvas object and post the new draw.
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                mPaused = false;
                if(abs(motionEvent.getX() - xLoc) < 30 && abs(motionEvent.getY() - yLoc) < 30)
                {
                    score++;
                    targetAdded=false;
                }
                else {

                }

                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    public void run() {
        System.out.println("This is Game");
        while (gameStarted) {
            //System.out.println("Game ");
            System.out.println("This is Game");
            long startFrameTime = System.currentTimeMillis();

            if(targetAdded == false)
            {
                xLoc = r.nextInt((1000 - 30) + 1) + 30;
                yLoc = r.nextInt((1500 - 30) + 1) + 30;
                drawTarget(xLoc,yLoc);
                targetAdded = true;
            }
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                mFPS = 1000 / timeThisFrame;
            }

        }

    }

    public float getXLoc() {
        return xLoc;
    }

    public void setXLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public float getYLoc() {
        return yLoc;
    }

    public void setYLoc(int yLoc) {
        this.yLoc = yLoc;
    }
}
