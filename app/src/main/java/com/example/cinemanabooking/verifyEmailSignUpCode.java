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
import com.example.cinemanabooking.Services.RequestServices.OtherPostServices;
import com.example.cinemanabooking.Services.RequestServices.PostServices;

import org.json.JSONObject;

public class verifyEmailSignUpCode extends AppCompatActivity implements PostServices.PostListener , OtherPostServices.otherPostListener {
    private EditText txtVerifyCode1;
    private EditText txtVerifyCode2;
    private EditText txtVerifyCode3;
    private EditText txtVerifyCode4;
    private TextView txtEmail;
    private Button btnVerify;

    private TextView labResendCodeVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_sign_up_code);
        txtVerifyCode1 = (EditText) findViewById(R.id.txtVerifyCode1);
        txtVerifyCode2 = (EditText) findViewById(R.id.txtVerifyCode2);
        txtVerifyCode3 = (EditText) findViewById(R.id.txtVerifyCode3);
        txtVerifyCode4 = (EditText) findViewById(R.id.txtVerifyCode4);
        labResendCodeVerify = (TextView) findViewById(R.id.labResendCodeVerify);
        labResendCodeVerify.setOnClickListener(this::ResendCode);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(this::verifyEmail);
        txtEmail = (TextView) findViewById(R.id.getEmailTextView);
        Intent thisIntent = getIntent();
        String email = thisIntent.getStringExtra("email");
        txtEmail.setText(email);


        txtVerifyCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    txtVerifyCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtVerifyCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    txtVerifyCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtVerifyCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    txtVerifyCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtVerifyCode4.addTextChangedListener(new TextWatcher() {
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

    @Override
    public void onPostSuccess(ApiResponse response) {
        String StrMessage = "";
        try {
            JSONObject JsonResponse = new JSONObject(response.Result.toString());
            StrMessage = JsonResponse.getString("result");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, StrMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }

    private void verifyEmail(View v) {

        String verifyCode;
        if (txtVerifyCode1.getText() != null && txtVerifyCode2.getText() != null && txtVerifyCode3.getText() != null && txtVerifyCode4.getText() != null) {

            verifyCode = (txtVerifyCode1.getText().toString() +
                    txtVerifyCode2.getText().toString() +
                    txtVerifyCode3.getText().toString() +
                    txtVerifyCode4.getText().toString())+"";

            try {
                String url = "api/Account/Verify-Email";
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

    private void ResendCode(View v) {
        String Url = "api/Account/Resend-Code-Verify";
        String strEmail = (String) txtEmail.getText();
        try {
            JSONObject postData = new JSONObject();
            postData.put("email", strEmail);
            new OtherPostServices(this).execute(Url, postData.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Error In pares Json", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onOtherPostSuccess(ApiResponse response) {
        String StrMessage = "";
        try {
            JSONObject JsonResponse = new JSONObject(response.Result.toString());
            StrMessage = JsonResponse.getString("result");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, StrMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOtherPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }
}
