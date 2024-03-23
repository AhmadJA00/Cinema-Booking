package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cinemanabooking.Services.ApiResponse;
import com.example.cinemanabooking.Services.RequestServices.PostServices;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPassword1 extends AppCompatActivity implements PostServices.PostListener {
    private EditText Vemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);
        Vemail = (EditText) findViewById(R.id.FPemail);
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateemail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public void forgetPass2(View v) {

        String strEmail = Vemail.getText().toString();
        if (validateemail(strEmail)) {
            String Url = "api/Account/ForgotPassword";
            try {
                JSONObject postData = new JSONObject();
                postData.put("email", strEmail);
                new PostServices(this).execute(Url, postData.toString());
            }
            catch (Exception e){

            }
        } else {
            Toast.makeText(this, "Email Incorrect.", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onPostSuccess(ApiResponse response) {


        String StrMessage="";
        try {
            JSONObject JsonResponse = new JSONObject(response.Result.toString());
            StrMessage = JsonResponse.getString("result");
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, StrMessage, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, forgetPassword2.class);
        i.putExtra("Email", Vemail.getText().toString());
        startActivity(i);
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }
}