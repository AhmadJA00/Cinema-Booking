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
    Button DOB;
    TextView res;
    EditText fName;
    EditText lName;
    EditText userEmail;
    EditText password;
    EditText cPassword;
    Button btn_sing_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        DOB = findViewById(R.id.DOB);

        res = findViewById(R.id.result);
        fName = findViewById(R.id.fname);
        lName = findViewById(R.id.lName);
        userEmail = findViewById(R.id.email_sing_up);
        password = findViewById(R.id.signPassword);
        cPassword = findViewById(R.id.signPasswordConfirm);
        btn_sing_up = (Button) findViewById(R.id.sing_up_button);
        btn_sing_up.setOnClickListener(this::signUp);
    }


    public void loginPage(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateemail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public void signUp(View view) {


        String f_name = fName.getText().toString();
        String l_name = lName.getText().toString();
        String DOB_o = DOB.getText().toString();
        String email = userEmail.getText().toString();
        String pass = password.getText().toString();
        String cPass = cPassword.getText().toString();

        if (!f_name.equals("") && !l_name.equals("") && !DOB_o.equals("") && !email.equals("") && !pass.equals("") && !cPass.equals("")) {
            if (!pass.equals(cPass) && pass.length() >= 6) {
                res.setText("Opsss, Your confrim Password must be the same as Password, or Password length minimum 6 characters");
                res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
                res.setVisibility(View.VISIBLE);

            } else {

                if (validateemail(email)) {
                    res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grayText)));
                    res.setText("You have been reqister");
                    res.setVisibility(View.VISIBLE);
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

                    }catch (Exception e){
                        Toast.makeText(this, "Error In pares to json", Toast.LENGTH_LONG).show();
                    }



                } else {
                    res.setText("Opsss, Your email Is Incorect.");
                    res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
                    res.setVisibility(View.VISIBLE);
                }

            }
        } else {
            res.setText("Please Fill the form first");
            res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
            res.setVisibility(View.VISIBLE);
        }
    }

    public void datePicker(View view) {
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

        String strMessage ="";
        try {
            JSONObject JsonResponse = new JSONObject(response.Result.toString());
            strMessage = JsonResponse.getString("result");
        }
        catch (Exception e){
            Toast.makeText(this,"Error Find Message Successfully.", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, strMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPostFailure(ApiResponse response) {
        Toast.makeText(this, response.ErrorMessage.get(0), Toast.LENGTH_LONG).show();
    }
}


