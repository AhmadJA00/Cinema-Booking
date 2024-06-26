package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemanabooking.Services.ApiResponse;
import com.example.cinemanabooking.Services.RequestServices.PostServices;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity implements PostServices.PostListener {
    private final static String UrlRegister = "api/Account/Register";
    private Button DOB;
    private TextView res;
    private EditText fName;
    private EditText lName;
    private EditText userEmail;
    private EditText password;
    private EditText cPassword;
    private Button btn_sing_up;
    private TextView labLoginPage;
    private Button dateTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        DOB = findViewById(R.id.btnDateTimePicker);

        res = findViewById(R.id.result);
        fName = findViewById(R.id.fname);
        lName = findViewById(R.id.lName);
        userEmail = findViewById(R.id.email_sing_up);
        password = findViewById(R.id.signPassword);
        cPassword = findViewById(R.id.signPasswordConfirm);
        btn_sing_up = (Button) findViewById(R.id.sing_up_button);
        btn_sing_up.setOnClickListener(this::signUp);
        labLoginPage = (TextView) findViewById(R.id.labLoginPage);
        labLoginPage.setOnClickListener(this::loginPage);
        dateTimePicker = (Button) findViewById(R.id.btnDateTimePicker);
        dateTimePicker.setOnClickListener(this::datePicker);

    }


    private void loginPage(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateemail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    private void signUp(View view) {


        String f_name = fName.getText().toString();
        String l_name = lName.getText().toString();
        String DOB_o = DOB.getText().toString();
        String email = userEmail.getText().toString();
        String pass = password.getText().toString();
        String cPass = cPassword.getText().toString();

        if (!f_name.equals("") && !l_name.equals("") && !DOB_o.equals("") && !email.equals("") && !pass.equals("") && !cPass.equals("")) {
            if (!pass.equals(cPass) && pass.length() >= 6) {
                Toast.makeText(this, "Opsss, Your confrim Password must be the same as Password, or Password length minimum 6 characters", Toast.LENGTH_LONG).show();
            } else {

                if (validateemail(email)) {
                    Toast.makeText(this, "You have been reqister", Toast.LENGTH_LONG).show();
                    try {
                        JSONObject postData = new JSONObject();
                        postData.put("firstName", f_name);
                        postData.put("lastName", l_name);
                        postData.put("dateOfBirthDay", DOB_o);
                        postData.put("email", email);
                        postData.put("password", pass);
                        postData.put("confirmPassword", cPass);
                        postData.put("acceptTerms", true);
                        new PostServices(this).execute(UrlRegister, postData.toString());
                    } catch (Exception e) {
                        Toast.makeText(this, "Error In pares to json", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Opsss, Your email Is Incorect.", Toast.LENGTH_LONG).show();
                }

            }
        } else {
            Toast.makeText(this, "Please Fill the form first", Toast.LENGTH_LONG).show();

        }


    }

    private void datePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dateDialog = new DatePickerDialog(SignUpPage.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String StrMonthe;
                String StrDay;
                if ((month + 1) < 10) {
                    StrMonthe = ("0" + (month + 1));
                } else {
                    StrMonthe = ("" + (month + 1));
                }

                if ((dayOfMonth) < 10) {
                    StrDay = ("0" + (dayOfMonth));
                } else {
                    StrDay = ("" + (dayOfMonth));
                }

                DOB.setText(String.valueOf(year) + "-" + String.valueOf(StrMonthe) + "-" + String.valueOf(StrDay));
            }
        }, year, month, day);
        dateDialog.show();
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

        Intent VerifyPage = new Intent(this, verifyEmailSignUpCode.class);
        String email = userEmail.getText().toString();
        VerifyPage.putExtra("email", email);
        startActivity(VerifyPage);
        Toast.makeText(this, StrMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }
}


