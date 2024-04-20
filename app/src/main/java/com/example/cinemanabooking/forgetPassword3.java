package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class forgetPassword3 extends AppCompatActivity {

    private TextView txtResult;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnChnagePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password3);
        txtResult = (TextView) findViewById(R.id.resultConfirmPass);
        txtPassword = (EditText) findViewById(R.id.newPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.NewPasswordConfirm);
        btnChnagePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChnagePassword.setOnClickListener(this::checkNewPassword);
    }

    private void checkNewPassword(View view) {


        String pass = txtPassword.getText().toString();
        String cPass = txtConfirmPassword.getText().toString();

        if (!pass.equals("") && !cPass.equals("")) {
            if (!pass.equals(cPass)) {
                txtResult.setText("Opsss, Your confrim Password must be the same as Password");
                txtResult.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
                txtResult.setVisibility(View.VISIBLE);

            } else {
                txtResult.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grayText)));
                txtResult.setText("Your Password has been updated");
                txtResult.setVisibility(View.VISIBLE);
            }
        } else {
            txtResult.setText("Please Fill the form first");
            txtResult.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
            txtResult.setVisibility(View.VISIBLE);
        }
    }

}
