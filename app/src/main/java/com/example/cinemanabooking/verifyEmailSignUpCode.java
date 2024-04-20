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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemanabooking.Services.ApiResponse;
import com.example.cinemanabooking.Services.RequestServices.PostServices;

import org.json.JSONObject;

public class verifyEmailSignUpCode extends AppCompatActivity implements PostServices.PostListener {
    private EditText verifyCode1;
    private EditText verifyCode2;
    private EditText verifyCode3;
    private EditText verifyCode4;
    private TextView emailTV;
    private Button btnVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_sign_up_code);

        verifyCode1 = (EditText) findViewById(R.id.verifyCode1);
        verifyCode2 = (EditText) findViewById(R.id.verifyCode2);
        verifyCode3 = (EditText) findViewById(R.id.verifyCode3);
        verifyCode4 = (EditText) findViewById(R.id.verifyCode4);

        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(this::verifyEmail);
        emailTV = (TextView) findViewById(R.id.getEmailTextView);
        Intent thisIntent = getIntent();
        String email = thisIntent.getStringExtra("email");
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

    private void FPpage3(View v) {
        String Url = "api/Account/Validate-Reset-Token";
        String strToken = "" + verifyCode1.getText() + verifyCode2.getText() +
                verifyCode3.getText() + verifyCode4.getText();
        try {
            JSONObject postData = new JSONObject();
            postData.put("token", strToken);
            new PostServices(this).execute(Url, postData.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Error In pares Json", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPostSuccess(ApiResponse response) {
        Dialog popupDialog = new Dialog(this);
        popupDialog.setContentView(R.layout.verify_pop_up);
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }

    private void verifyEmail(View v) {

        String verifyCode;
        if (verifyCode1.getText() != null && verifyCode2.getText() != null && verifyCode3.getText() != null && verifyCode4.getText() != null) {

            verifyCode = String.valueOf(verifyCode1.getText().toString().charAt(0) +
                    verifyCode2.getText().toString().charAt(0) +
                    verifyCode3.getText().toString().charAt(0) +
                    verifyCode4.getText().toString().charAt(0));

            try {
                String url = "/api/Account/Verify-Email";
                JSONObject postData = new JSONObject();
                postData.put("token", verifyCode);
                new PostServices(this).execute(url, postData.toString());
            } catch (Exception e) {
                Toast.makeText(this, "Send Json error.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please write the verify code", Toast.LENGTH_LONG).show();
        }
    }

}
