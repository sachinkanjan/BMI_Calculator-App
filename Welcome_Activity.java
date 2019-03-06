package com.example.hanish.bmianalysis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class Welcome_Activity extends AppCompatActivity {

    TextView tvDetails;
    EditText etName, etPhone, etAge;
    RadioGroup ragSex;
    Button btnSubmit;
    RadioButton rbMale;
    SharedPreferences sp, sp2;
    View lay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);
        lay = this.getWindow().getDecorView();
        lay.setBackgroundColor((Color.WHITE));
        tvDetails = (TextView) findViewById(R.id.tvDetails);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        etPhone = (EditText) findViewById(R.id.etPhone);
        ragSex = (RadioGroup) findViewById(R.id.ragSex);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        sp = getSharedPreferences("D1", MODE_PRIVATE);
        sp2 = getSharedPreferences("D2", MODE_PRIVATE);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String num = etPhone.getText().toString();
                String Age = etAge.getText().toString();
                if (name.length() == 0) {
                    etName.setError("Name is too short");
                    etName.requestFocus();
                    return;
                }
                if (name.length() == 0) {
                    etName.setError("Name is too short");
                    etName.requestFocus();
                    return;
                }
                if (num.length() == 0) {
                    etPhone.setError("Enter a Number");
                    etPhone.requestFocus();
                    return;
                }
                if (num.length() != 10) {
                    etPhone.setError("Invalid Number");
                    etPhone.setText("");
                    etPhone.requestFocus();
                    return;
                }

                if (Integer.parseInt(Age) <= 11) {
                    etAge.setError("You are Under Age");
                    etAge.requestFocus();
                    return;
                }
                rbMale.setChecked(true);
                int id = ragSex.getCheckedRadioButtonId();
                RadioButton rbtnGender = (RadioButton) ragSex.findViewById(id);
                String Gex = rbtnGender.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", name);
                editor.putString("Age", Age);
                editor.putString("phoneno", num);
                editor.commit();
                Intent i = new Intent(Welcome_Activity.this, MainActivity.class);
                finish();
                startActivity(i);

                Toast.makeText(Welcome_Activity.this, "Thanks", Toast.LENGTH_SHORT).show();

            }
        });


    }
}

