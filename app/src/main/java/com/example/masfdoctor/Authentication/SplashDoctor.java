package com.example.masfdoctor.Authentication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.masfdoctor.R;

public class SplashDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_doctor);   Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent( SplashDoctor.this,AdminEmailLoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    } }
