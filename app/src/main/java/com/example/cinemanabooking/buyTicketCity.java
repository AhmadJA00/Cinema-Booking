package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

public class buyTicketCity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket_city);

        ProgressBar cityProgressBar = findViewById(R.id.cityProgressBar);

        cityProgressBar.setProgress(100);

    }
}