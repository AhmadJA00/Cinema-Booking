package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemanabooking.Services.ApiResponse;
import com.example.cinemanabooking.Services.RequestServices.OtherPostServices;
import com.example.cinemanabooking.Services.RequestServices.PostServices;

import org.json.JSONObject;

public class verifyEmailSignUpCode extends AppCompatActivity implements PostServices.PostListener , OtherPostServices.otherPostListener{
    EditText verifyCode1;
    EditText verifyCode2;
    EditText verifyCode3;
    EditText verifyCode4;

    TextView emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);

        verifyCode1 = (EditText) findViewById(R.id.verifyCode1);
        verifyCode2 = (EditText) findViewById(R.id.verifyCode2);
        verifyCode3 = (EditText) findViewById(R.id.verifyCode3);
        verifyCode4 = (EditText) findViewById(R.id.verifyCode4);
        emailTV = (TextView) findViewById(R.id.getEmailTextView);
        Intent i = getIntent();
        String email = i.getStringExtra("Email");
        emailTV.setText(email);

        verifyCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
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
                if (s.length() == 1) {
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
                if (s.length() == 1) {
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

    public void FPpage3(View v) {
        String Url = "api/Account/Validate-Reset-Token";
        String strToken = ""+ verifyCode1.getText()+ verifyCode2.getText()+
                verifyCode3.getText() + verifyCode4.getText();
        try {
            JSONObject postData = new JSONObject();
            postData.put("token", strToken);
            new PostServices(this).execute(Url, postData.toString());
        }
        catch (Exception e){
            Toast.makeText(this, "Error In pares Json", Toast.LENGTH_LONG).show();
        }
    }

    public void ResendCode(View v) {
        String Url = "api/Account/Resend-Code-Reset";
        String strEmail = (String) emailTV.getText();
        try {
            JSONObject postData = new JSONObject();
            postData.put("email", strEmail);
            new OtherPostServices(this).execute(Url, postData.toString());
        }
        catch (Exception e){
            Toast.makeText(this, "Error In pares Json", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPostSuccess(ApiResponse response) {
        Dialog popupDialog= new Dialog(this);
        popupDialog.setContentView(R.layout.verify_pop_up);
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOtherPostSuccess(ApiResponse response) {

        String StrMessage="";
        try {
            JSONObject JsonResponse = new JSONObject(response.Result.toString());
            StrMessage = JsonResponse.getString("result");
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, StrMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOtherPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }


}