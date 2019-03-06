package com.example.hanish.bmianalysis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Result_Activity extends AppCompatActivity {
    TextView tvResult,tvUnderWeight,tvOverWeight,tvObese,tvNormal;
    ImageButton ibtnShare,ibtnSave;
    TextToSpeech tts;
    FloatingActionButton fabMic;
    SharedPreferences sp2;
    View lay;
    int BMI;
    MeradbHandler db;
    SharedPreferences sp;
    Date dt;

    String msg = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_);
        Intent i = getIntent();
        db = new MeradbHandler(this);
        String SBMI = i.getStringExtra("SBMI");
        final int BMI =(int) Double.parseDouble(SBMI);
        sp = getSharedPreferences("D1",MODE_PRIVATE);
        final String name  =sp.getString("name","X");


        tvResult = (TextView)findViewById(R.id.tvResult);
        tvNormal = (TextView)findViewById(R.id.tvNormal);
        tvUnderWeight = (TextView)findViewById(R.id.tvUnderWeight);
        tvOverWeight = (TextView)findViewById(R.id.tvOverWeight);
        tvObese = (TextView)findViewById(R.id.tvObese);
        fabMic = (FloatingActionButton)findViewById(R.id.fabMic);
        ibtnSave = (ImageButton)findViewById(R.id.ibtnSave);
        ibtnShare = (ImageButton)findViewById(R.id.ibtnShare);
        lay = this.getWindow().getDecorView();
        lay.setBackgroundColor(Color.WHITE);

        msg  = "The BMI  is "+BMI;

        sp2 = getSharedPreferences("D2",MODE_PRIVATE);
        String cc = sp2.getString("cc","");
        if(cc.equalsIgnoreCase("Black"))
            lay.setBackgroundColor(Color.parseColor("#726f6f"));


        if(BMI<19) {
            tvUnderWeight.setTextColor(Color.RED);
            tvUnderWeight.setTextSize(20);
            msg = msg +" which is in "+ tvUnderWeight.getText().toString();
        }
        else if(BMI<25) {
            tvNormal.setTextColor(Color.RED);
            tvNormal.setTextSize(20);
            msg = msg +" which is in "+ tvNormal.getText().toString();


        }
        else if(BMI<30) {
            tvOverWeight.setTextColor(Color.RED);
            tvOverWeight.setTextSize(20);
            msg = msg +" which is in "+ tvOverWeight.getText().toString();


        }
        else if(BMI>30){
            tvObese.setTextColor(Color.RED);
            tvObese.setTextSize(20);
            msg = msg + " which is "+tvObese.getText().toString();


        }
        tvResult.setText(msg);



        ibtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                String k = tvResult.getText().toString();
                String res = "Name:" + name + "\n" +  k;
                i.putExtra(Intent.EXTRA_TEXT, res);
                startActivity(i);

            }
        });
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });
        fabMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(msg,TextToSpeech.QUEUE_FLUSH,null);


            }
        });
        ibtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt = new Date();
                DateFormat dtformat = new SimpleDateFormat();
                String Date = dtformat.format(dt);
                db.addData(BMI,name,Date);

            }
        });
    }
}
