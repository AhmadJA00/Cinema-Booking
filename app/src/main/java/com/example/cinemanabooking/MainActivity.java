package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void signUpPage(View v){
        Intent i=new Intent(this, SignUpPage.class);
        startActivity(i);
    }
    public void FPpage(View v){
        Intent i=new Intent(this, ForgetPassword1.class);
        startActivity(i);
    }

    public void logIn(View view) {

        TextView res = findViewById(R.id.result_login);
        EditText userEmail = findViewById(R.id.email_sing_up);
        EditText password = findViewById(R.id.loginPassword);

        String email = userEmail.getText().toString();
        String pass = password.getText().toString();

        if(!email.equals("") && !pass.equals("")) {
            res.setText("Done");
            res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
            res.setVisibility(View.VISIBLE);

        } else {
            res.setText("Please Fill the form first");
            res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
            res.setVisibility(View.VISIBLE);
        }
    }
    public void setCheck(View view){

        CheckBox checkBox= findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            checkBox.setChecked(true);

            checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main)));
        }else {
            checkBox.setChecked(false);
            checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));

        }
    }
}