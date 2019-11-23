package com.example.webapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScorePage extends AppCompatActivity
{
    Button button;
    static int curHigh;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_main);
        setTitle("Score Screen");

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScorePage.this, MainActivity.class));
            }
        });

        final TextView highScore = findViewById(R.id.high_score);
        highScore.setText(curHigh);

    }

    static void setHigh(int set)
    {
        curHigh = set;
    }

    static int getHigh()
    {
        return curHigh;
    }
}
