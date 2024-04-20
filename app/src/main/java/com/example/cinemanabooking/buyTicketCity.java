package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class buyTicketCity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket_city);

        //to fill first progress bar
        ProgressBar cityProgressBar = findViewById(R.id.cityProgressBar);
        cityProgressBar.setProgress(100);

         //to start on ticket
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavbar);
        bottomNavigationView.setSelectedItemId(R.id.ticket);

    }
}
