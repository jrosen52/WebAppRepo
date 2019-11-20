package com.example.webapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ScorePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_main);
        setTitle("Web app");
    }
}
