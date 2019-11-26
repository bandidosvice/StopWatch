package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int secs;
    private boolean isRunning = false;
    private TextView textViewTimer;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.timerView);
        if (savedInstanceState != null) {
            secs = savedInstanceState.getInt("secs");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("secs", secs);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStartTimer(View view) {
        isRunning = true;
    }

    public void onClickPauseTimer(View view) {
        isRunning = false;
    }

    public void onClicklResetTimer(View view) {
        isRunning = false;
        secs = 0;
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int millis = (int)((secs * 1.66666667) % 100);
                int seconds = (secs / 60) % 60;
                int minutes = (secs / 3600) % 60;
                int hours = (secs / 216000) % 24;


                String time = String.format(Locale.getDefault(), "%d:%02d:%02d:%02d", hours, minutes, seconds, millis);
                textViewTimer.setText(time);
                if (isRunning){
                    secs++;
                }
                handler.postDelayed(this, 1);
            }
        });
    }
}

