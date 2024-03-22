package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemanabooking.Dtos.AccountDto.LoginResponseDto;
import com.example.cinemanabooking.Services.Account.LoginTask;
import com.example.cinemanabooking.Services.ApiResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements  LoginTask.LoginListener {


    private Button btnLogin;
    private static final String  loginUrl = "api/Account/Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=(Button) findViewById(R.id.btLogin);
    }
    public void signUpPage(View v){
        Intent i=new Intent(this, SignUpPage.class);
        startActivity(i);
    }
    public void FPpage(View v){
        Intent i=new Intent(this, ForgetPassword1.class);
        startActivity(i);
    }



    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static boolean validateemail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public void logIn(View view) {

        TextView res = findViewById(R.id.result_login);
        EditText userEmail = findViewById(R.id.email_sing_up);
        EditText password = findViewById(R.id.loginPassword);
        String email = userEmail.getText().toString();
        String pass = password.getText().toString();


        if(!email.equals("") && !pass.equals("")) {
            if (validateemail(email)){
                new LoginTask(this).execute(loginUrl,email,pass);
            }
            else {
                res.setText("Email Incorect.");
                res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
                res.setVisibility(View.VISIBLE);
            }

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

    @Override
    public void onLoginSuccess(ApiResponse response) {
        // If email and password correct response token  and refresh token to header request
        LoginResponseDto login= (LoginResponseDto)response.Result;
        Toast.makeText(this,"Login Successfully",Toast.LENGTH_LONG).show();
        TextView res = findViewById(R.id.result_login);
        res.setText("Login Successfully");
        res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
        res.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoginFailure(ApiResponse response) {
        // if email or password incorrect 
        Toast.makeText(this,response.ErrorMessage.get(0),Toast.LENGTH_LONG).show();
        TextView res = findViewById(R.id.result_login);
        res.setText(response.ErrorMessage.get(0));
        res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
        res.setVisibility(View.VISIBLE);
    }
}