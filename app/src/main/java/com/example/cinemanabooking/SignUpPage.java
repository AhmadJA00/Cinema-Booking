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

import java.util.Calendar;

public class SignUpPage extends AppCompatActivity {

    Button DOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        DOB = findViewById(R.id.DOB);
    }


    public void loginPage(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    public void signUp(View view) {
        TextView res = findViewById(R.id.result);
        EditText fName = findViewById(R.id.fname);
        EditText lName = findViewById(R.id.lName);


        EditText userEmail = findViewById(R.id.email_sing_up);
        EditText password = findViewById(R.id.signPassword);
        EditText cPassword = findViewById(R.id.signPasswordConfirm);
        Button btn_sing_up = (Button) findViewById(R.id.sing_up_button);


        String f_name = fName.getText().toString();
        String l_name = lName.getText().toString();
        String DOB_o = DOB.getText().toString();
        String email = userEmail.getText().toString();
        String pass = password.getText().toString();
        String cPass = cPassword.getText().toString();

        if (!f_name.equals("") && !l_name.equals("") && !DOB_o.equals("") && !email.equals("") && !pass.equals("") && !cPass.equals("")) {
            if (!pass.equals(cPass)) {
                res.setText("Opsss, Your confrim Password must be the same as Password");
                res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_text)));
                res.setVisibility(View.VISIBLE);

            } else {
                res.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grayText)));
                res.setText("You have been reqister");
                res.setVisibility(View.VISIBLE);
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
                DOB.setText(String.valueOf(year) + " - " + String.valueOf(month + 1) + " - " + String.valueOf(day));
            }
        }, year, month, day);
        dateDialog.show();
    }


}


