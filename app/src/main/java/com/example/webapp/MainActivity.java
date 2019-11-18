package com.example.webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    public int counter;
    Button button;
    TextView textView;
    private TextView coors;
    private LinearLayout canvasLayout = null;
    CanvasClass customSurfaceView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Web app");

        initControls();
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        coors = (TextView) findViewById(R.id.coor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textView.setText(String.valueOf(counter));
                        counter++;
                    }

                    public void onFinish() {
                        textView.setText("FINISH!!");
                    }
                }.start();
            }
        });
    }

    private void initControls()
    {
        // This layout is used to contain custom surfaceview object.
        if(canvasLayout == null)
        {
            canvasLayout = (LinearLayout)findViewById(R.id.customViewLayout);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // If user touch the custom SurfaceView object.
        if (view instanceof SurfaceView) {
            view.invalidate();

            float x = motionEvent.getX();

            float y = motionEvent.getY();

            String coorNums = (int) x + ", " + (int) y;
            coors.setText(coorNums);
            return true;
        }
        else
        {
            return false;
        }
    }
}