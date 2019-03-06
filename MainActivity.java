package com.example.hanish.bmianalysis;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{



    TextView tvMName, tvLocation, tvTemp, tvHeight, tvFeet, tvInch, tvWeight;
    EditText etWeight;
    Button btnSsave, btnHistory;
    FloatingActionButton fabCall;
    Spinner spnFt, spnInch;
    GoogleApiClient gac;
    Location loc;
    String msg;
    View lay;

    @Override
    protected void onPause() {
        if(gac!= null)
            gac.disconnect();
        super.onPause();
    }

    @Override
    protected void onResume() {

        if(gac!= null)
            gac.connect();
        super.onResume();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        String Gex = i.getStringExtra("Gex");
        tvMName = (TextView) findViewById(R.id.tvMName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        etWeight = (EditText) findViewById(R.id.etWeight);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        fabCall = (FloatingActionButton)findViewById(R.id.fabCall);
        btnSsave = (Button) findViewById(R.id.btnSsave);
        spnInch = (Spinner) findViewById(R.id.spnInch);
        spnFt = (Spinner) findViewById(R.id.spnFt);
        lay = this.getWindow().getDecorView();
        lay.setBackgroundColor((Color.WHITE));
        GoogleApiClient.Builder  builder= new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        gac = builder.build();

        SharedPreferences sp = getSharedPreferences("D1", MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences("D2", MODE_PRIVATE);
        String cc = sp2.getString("cc","");
            if (cc.equalsIgnoreCase("White"))
                lay.setBackgroundColor((Color.WHITE));

            else if (cc.equalsIgnoreCase("Black"))
                lay.setBackgroundColor(Color.parseColor("#726f6f"));










        String n = sp.getString("name", "");
        String pro = sp2.getString("pro", "");
        tvTemp.setText(pro);
        msg = "Welcome " +n;



        tvMName.setText(msg);
        ArrayList<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        spnFt.setAdapter(adapter);
        spnInch.setAdapter(adapter);
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+"9960888140"));
                startActivity(i);
            }
        });
        btnSsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                double wt,ht,BMI;
                ht = (((double) (spnFt.getSelectedItemId()*12))+(((double) spnInch.getSelectedItemId())))*0.0254;
                if(ht<0.5){
                    Snackbar.make(v, "Invalid Height", Snackbar.LENGTH_SHORT).show();
                return;}

                if(etWeight.getText().toString().length()==0) {
                    etWeight.setError("Enter Weight");
                    etWeight.requestFocus();
                    return;
                }
                wt = Double.parseDouble(etWeight.getText().toString());
                if(wt<10||wt>500)
                {
                    etWeight.setError("Weight is too low");
                    etWeight.requestFocus();
                    etWeight.setText("");
                    return;
                }

                    BMI = (wt) / (ht * ht);

                Intent i = new Intent(MainActivity.this,Result_Activity.class);
                String SBMI=""+ BMI;
                i.putExtra("SBMI",SBMI);
                startActivity(i);

                Toast.makeText(MainActivity.this,SBMI, Toast.LENGTH_SHORT).show();
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(i);
            }
        });


    }
    class MyTask extends AsyncTask<String,Void,Double>
    {

        @Override
        protected Double doInBackground(String... params) {
            double temp = 26.0;
            String line="",json ="";
            try{
                URL url = new URL(params[0]);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.connect();
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                while ((line = br.readLine())!=null)
                {
                    json = json + line;
                }
                JSONObject o = new JSONObject(json);
                JSONObject p = o.getJSONObject("main");
                    temp = p.getDouble("temp");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return  temp;

        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            StringBuffer SBT = new StringBuffer(String.valueOf(aDouble));

            tvTemp.setText(SBT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.Facebook.com"));
            startActivity(i);

        }
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "THIS APPLICATION IS DEVELOPED BY Sachin Kanjan...", Toast.LENGTH_SHORT).show();

        }
       /* if (item.getItemId() == R.id.settings){
            Intent i = new Intent(MainActivity.this,Settings_Activity.class);
            startActivity(i);
            finish();
        }*/
        if (item.getItemId() == R.id.exit){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onConnected(Bundle bundle) {
        loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            Geocoder gc = new Geocoder(this, Locale.ENGLISH);
            try {
                List<Address> la = gc.getFromLocation(lat, lon, 1);
                android.location.Address add = la.get(0);
                String msgi = (add.getLocality());
                tvLocation.setText(msgi);
                String url = "http://api.openweathermap.org/data/2.5/weather?units=metric";
                String q  = "&q"+msgi;
                String id = "e4c4aadb5cf75c2f2bc18a4e2ce8ce76";
                String m = url+q+id;


                MyTask t = new MyTask();
                t.execute(m);
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "Unable to detect Location", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do You Want to Exit?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog a = builder.create();
        a.setTitle("Exit");
        a.show();

    }
}


