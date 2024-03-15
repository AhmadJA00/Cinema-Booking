package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class forgetPassword2 extends AppCompatActivity {

    EditText verifyCode1 ;
    EditText verifyCode2 ;
    EditText verifyCode3 ;
    EditText verifyCode4 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);

        verifyCode1 = (EditText) findViewById(R.id.verifyCode1);
        verifyCode2 = (EditText) findViewById(R.id.verifyCode2);
        verifyCode3 = (EditText) findViewById(R.id.verifyCode3);
        verifyCode4 = (EditText) findViewById(R.id.verifyCode4);

        TextView emailTV= (TextView) findViewById(R.id.getEmailTextView);
        Intent i =getIntent();
        String email = i.getStringExtra("Email");
        emailTV.setText(email);



        verifyCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1){
                    verifyCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        verifyCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1){
                    verifyCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        verifyCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1){
                    verifyCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        verifyCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void FPpage3(View v){
        Intent i= new Intent(this, forgetPassword3.class);
        startActivity(i);

    }


}