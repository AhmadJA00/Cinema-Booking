package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ForgetPassword1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);
    }

    public void forgetPass2(View v){
        Intent i= new Intent(this,forgetPassword2.class);
        EditText Vemail=(EditText) findViewById(R.id.FPemail);
        i.putExtra("Email", Vemail.getText().toString());
        startActivity(i);
    }


}