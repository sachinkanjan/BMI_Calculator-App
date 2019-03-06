package com.example.hanish.bmianalysis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    ImageView ivLoad,ivMain;
    Animation animation;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLoad = (ImageView)findViewById(R.id.ivLoad);
        ivMain = (ImageView) findViewById(R.id.ivMain);
        animation = AnimationUtils.loadAnimation(this,R.anim.a1);
        ivLoad.startAnimation(animation);
        final SharedPreferences sp = getSharedPreferences("D1", MODE_PRIVATE);




        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                  String n = sp.getString("name","");
                    if(n.length()!= 0)
                    {
                        Intent i = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{

                    Intent i = new Intent(SplashActivity.this,Welcome_Activity.class);
                    startActivity(i);
                    finish();}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


