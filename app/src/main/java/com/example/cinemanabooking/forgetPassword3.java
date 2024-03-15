package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class forgetPassword3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password3);
    }

    public void FPpage1(View v){
        Intent i= new Intent(this, ForgetPassword1.class);
        startActivity(i);
    }
    public void checkNewPassword(View view){
        TextView res = (TextView) findViewById(R.id.resultConfirmPass);
        EditText password = findViewById(R.id.newPassword);
        EditText cPassword = findViewById(R.id.NewPasswordConfirm);

        String pass = password.getText().toString();
        String cPass = cPassword.getText().toString();

        if (!pass.equals("") && !cPass.equals("")) {
            if (!pass.equals(cPass)) {
                res.setText("Opsss, Your confrim Password must be the same as Password");
                res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
                res.setVisibility(View.VISIBLE);

            } else {
                res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grayText)));
                res.setText("Your Password has been updated");
                res.setVisibility(View.VISIBLE);
            }
        } else {
            res.setText("Please Fill the form first");
            res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
            res.setVisibility(View.VISIBLE);
        }
    }

    }
