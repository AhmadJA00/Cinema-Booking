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
import android.widget.Toast;

import com.example.cinemanabooking.Services.ApiResponse;
import com.example.cinemanabooking.Services.RequestServices.OtherPostServices;
import com.example.cinemanabooking.Services.RequestServices.PostServices;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class forgetPassword3 extends AppCompatActivity implements PostServices.PostListener {

    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnChnagePassword;
    private String strToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password3);
        txtPassword = (EditText) findViewById(R.id.newPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.NewPasswordConfirm);
        btnChnagePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChnagePassword.setOnClickListener(this::checkNewPassword);
        Intent i = getIntent();
        strToken = i.getStringExtra("token");
    }

    private void checkNewPassword(View view) {


        String pass = txtPassword.getText().toString();
        String cPass = txtConfirmPassword.getText().toString();

        if (!pass.equals("") && !cPass.equals("")) {
            if (pass.equals(cPass)) {
                String Url = "api/Account/Reset-Password";
                try {
                    JSONObject postData = new JSONObject();
                    postData.put("token", strToken);
                    postData.put("password", pass);
                    postData.put("confirmPassword", cPass);
                    new PostServices(this).execute(Url, postData.toString(),"");
                } catch (Exception e) {
                    Toast.makeText(this, "Error In pares Json", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "Opsss, Your confrim Password must be the same as Password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please Fill the form first", Toast.LENGTH_LONG).show();
        }
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
        Intent HomePage =new Intent(this, MainActivity.class);
        startActivity(HomePage);
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }
}
