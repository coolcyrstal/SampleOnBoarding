package com.example.chayent.sampleonboarding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent iOnBoarding = new Intent(MainActivity.this, OnBoardingActivity.class);
        startActivity(iOnBoarding);
    }
}
