package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpVerify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_verify);
        Button buttonVerify= findViewById(R.id.buttonVerify);
    }

    public void verifyEmailCodePage(View v){
        Intent i = new Intent(this , verifyEmailSignUpCode.class);
        startActivity(i);
    }

}