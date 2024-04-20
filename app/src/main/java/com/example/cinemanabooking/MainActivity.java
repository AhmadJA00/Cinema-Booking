package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
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
import com.example.cinemanabooking.Services.ApiResponse;
import com.example.cinemanabooking.Services.RequestServices.PostServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements PostServices.PostListener {
    private Button btnLogin;
    File _file = null;
    FileInputStream _fileReader = null;
    FileOutputStream _fileWriter = null;
    private LoginResponseDto _LoginResponse;
    private static final String loginUrl = "api/Account/Login";

    CheckBox CheckRemember;
    EditText userEmail;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btLogin);
        CheckRemember = findViewById(R.id.checkBox);
        userEmail = findViewById(R.id.email_sing_up);
        password = findViewById(R.id.loginPassword);
        loadRemember();
    }

    public void signUpPage(View v) {
        Intent i = new Intent(this, SignUpPage.class);
        startActivity(i);
    }

    public void FPpage(View v) {
        Intent i = new Intent(this, ForgetPassword1.class);
        startActivity(i);
    }


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateemail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public void logIn(View view) {

        TextView res = findViewById(R.id.result_login);
        String email = userEmail.getText().toString();
        String pass = password.getText().toString();
        if (!email.equals("") && !pass.equals("")) {
            if (validateemail(email)) {
                try {
                    JSONObject postData = new JSONObject();
                    postData.put("email", email);
                    postData.put("password", pass);
                    new PostServices(this).execute(loginUrl, postData.toString());

                } catch (Exception e) {
                    Toast.makeText(this, "Send Json error.", Toast.LENGTH_LONG).show();
                }

            } else {
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

    public void setCheck(View view) {

        if (CheckRemember.isChecked()) {
            CheckRemember.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main)));
            CheckRemember.setChecked(true);
        } else {
            CheckRemember.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            CheckRemember.setChecked(false);
            try {
                saveRemember("","");
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onPostSuccess(ApiResponse response) {
        // If email and password correct response token  and refresh token to header request
        _LoginResponse = new LoginResponseDto();
        try {
            JSONObject JsonResponse = new JSONObject(response.Result.toString());
            JSONObject Jasonaccount = JsonResponse.getJSONObject("result").getJSONObject("account");
            _LoginResponse.accountId = Jasonaccount.getString("accountId");
            _LoginResponse.firstName = Jasonaccount.getString("firstName");
            _LoginResponse.lastName = Jasonaccount.getString("lastName");
            _LoginResponse.role = Jasonaccount.getInt("role");
            _LoginResponse.imageUrl = Jasonaccount.getString("imageUrl");
            _LoginResponse.email = Jasonaccount.getString("email");
            _LoginResponse.token = JsonResponse.getJSONObject("result").getString("token");
            _LoginResponse.refreshToken = JsonResponse.getJSONObject("result").getString("refreshToken");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            _LoginResponse.created = sdf.parse(JsonResponse.getJSONObject("result").getString("created"));
        } catch (Exception e) {
            Toast.makeText(this, "Error In Login response in success.", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();
        TextView res = findViewById(R.id.result_login);
        res.setText("Login Successfully");
        res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
        res.setVisibility(View.VISIBLE);
        if (CheckRemember.isChecked()) {
            String email = userEmail.getText().toString();
            String pass = password.getText().toString();
            saveRemember(email, pass);
        }
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        // if email or password incorrect 
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
        TextView res = findViewById(R.id.result_login);
        res.setText(response.ErrorMessage.get(0));
        res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
        res.setVisibility(View.VISIBLE);
    }


    private void saveRemember(String email, String password) {
        try {
            _fileWriter = openFileOutput("Remember.txt", Context.MODE_PRIVATE);
            _fileWriter.write((email + "," + password).getBytes());
            _fileWriter.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error In File Write.", Toast.LENGTH_LONG).show();
        } finally {
            try {
                _fileWriter.close();
            } catch (Exception e) {
            }
        }
    }

    private void loadRemember() {
        try {
            _fileReader = openFileInput("Remember.txt");
            int size;
            String line = "";
            while ((size = _fileReader.read()) != -1) {
                // add & append content
                line += Character.toString((char) size);
            }
            if (line != null) {
                String[] strSplict = line.split(",");
                userEmail.setText(strSplict[0]);
                password.setText(strSplict[1]);
                CheckRemember.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main)));
                CheckRemember.setChecked(true);
                _fileReader.close();
            }
        } catch (Exception e) {
        } finally {
            try {
                _fileReader.close();
            } catch (Exception e) {
            }
        }
    }


}