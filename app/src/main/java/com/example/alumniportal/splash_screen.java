package com.example.alumniportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class splash_screen extends AppCompatActivity {
ImageView logo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logo = findViewById(R.id.logo) ;
        CountDownTimer countDownTimer = new CountDownTimer(3000 , 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("start" , "working") ;
            }

            @Override
            public void onFinish() {
                Intent i = new Intent(getApplicationContext() , Login_page.class) ;
                startActivity(i) ;
                finish();
            }
        }.start() ;
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Intent i = new Intent(getApplicationContext() , MainActivity.class) ;
//                startActivity(i) ;
//            }
//        },0 , 300);
    }
}