package com.example.iseenero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        YoYo.with(Techniques.BounceInUp)
                .duration(800).delay(500)
                .playOn(findViewById(R.id.imageView));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.imageView2).setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp)
                        .duration(1000)
                        .playOn(findViewById(R.id.imageView2));
            }
        }, 100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.Tada)
                        .duration(1000)
                        .playOn(findViewById(R.id.imageView));
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.textView2).setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInUp)
                        .duration(1000)
                        .playOn(findViewById(R.id.textView2));
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
            }, 3000);

    }

}