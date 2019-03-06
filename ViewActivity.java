package com.example.hanish.bmianalysis;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    TextView tvDBData;
    MeradbHandler db;
    SharedPreferences sp2;
    View lay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        lay = this.getWindow().getDecorView();
        sp2 = getSharedPreferences("D2",MODE_PRIVATE);
        String cc = sp2.getString("cc","");
        String pro = sp2.getString("pro","");

        if(cc.equalsIgnoreCase("Black")) {
            lay.setBackgroundColor(Color.parseColor("#726f6f"));
        }



        db = new MeradbHandler(this);
        tvDBData = (TextView) findViewById(R.id.tvDBData);
        String DData = db.viewUser();
        tvDBData.append(DData);
        tvDBData.append("----------------------------------------------------------------------------------\n");
        if(pro.equalsIgnoreCase("On"))
        {
            tvDBData.setText("No History to show");
            pro="Off";
            SharedPreferences sp2 = getSharedPreferences("D2",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp2.edit();
            editor.putString("pro",pro);
            editor.commit();
        }



    }
}
