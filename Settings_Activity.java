package com.example.hanish.bmianalysis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Settings_Activity extends AppCompatActivity {
    Switch switchDM;
    ProgressBar pbMG;
    ListView lvSettings;
    View lay;
    ToggleButton tbPro;
    String cc = "";
    String pro="";
    MeradbHandler db;
    SQLiteDatabase sql;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        Intent i = getIntent();
        lvSettings = (ListView)findViewById(R.id.lvSettings);
        tbPro = (ToggleButton)findViewById(R.id.tbPro);
        pbMG = (ProgressBar)findViewById(R.id.pbMG);
        switchDM = (Switch)findViewById(R.id.switchDM);
        lay = (this.getWindow().getDecorView());
        SharedPreferences sp2 = getSharedPreferences("D2",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp2.edit();


        ArrayList<String> SList = new ArrayList<String>();

        SList.add("Clear History");
        SList.add("Pro Mode");
        SList.add("Reset Settings");
        SList.add("Back");
        SList.add("Exit");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,SList);
        lvSettings.setAdapter(adapter);
        pro=sp2.getString("pro","");
        cc = sp2.getString("cc","");
        if(pro.equalsIgnoreCase("on"))
        {
            tbPro.setChecked(true);
        }
        if(cc.equalsIgnoreCase("Black")) {
            switchDM.setChecked(true);
            DM();
        }
        db = new MeradbHandler(this);





        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchDM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                        DM();
                    }
                });



                if(position == 0)
                {
                    if (switchDM.isChecked()) {
                        switchDM.setChecked(false);
                    } else {
                        switchDM.setChecked(true);
                    }


                    switchDM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                          DM();
                        }
                    });

                }
                if(position == 1)
                {
                    pro="On";
                    db.Clear();
                    editor.putString("pro",pro);
                    editor.apply();

                }
                if(position == 2)
                {
                    toggle();
                }
                if (position == 3)
                {
                    int value=(int)(Math.random()*100);
                    pbMG.setProgress(value);

                }

                if(position == 6)
                {
                    pbMG.setProgress(0);
                    switchDM.setChecked(false);
                    tbPro.setChecked(false);
                    pro="off";
                    editor.putString("pro",pro);
                    editor.apply();

                    Toast.makeText(Settings_Activity.this, "Settings have been Reseted", Toast.LENGTH_SHORT).show();                }
                if (position==7)
                {
                    onBackPressed();
                }
                if (position==8)
                    finish();


            }

        });


    }



    public void DM()
    {
        if (switchDM.isChecked()) {
            lay.setBackgroundColor(Color.parseColor("#726f6f"));
            cc = "Black";
        } else {
            lay.setBackgroundColor(Color.WHITE);
            cc = "White";

        }
        SharedPreferences sp2 = getSharedPreferences("D2",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp2.edit();
        editor.putString("cc", cc);
        editor.apply();

    }

    public  void toggle()
    {


        if(tbPro.isChecked())
                {
                    pro= "off";

                    tbPro.setChecked(false);

                }
                else
                {
                     pro = "on";
                    tbPro.setChecked(true);


                    Toast.makeText(Settings_Activity.this, "You are now a pro user", Toast.LENGTH_SHORT).show();
                }
        SharedPreferences sp2 = getSharedPreferences("D2",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sp2.edit();
        //editor.putString("pro",pro);
        //editor.commit();

    }


    @Override
    public void onBackPressed() {

        Intent i = new Intent(Settings_Activity.this,MainActivity.class);
        i.putExtra("cc",cc);
        startActivity(i);
        finish();
    }
}

