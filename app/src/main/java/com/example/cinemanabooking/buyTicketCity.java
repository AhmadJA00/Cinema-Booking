package com.example.cinemanabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class buyTicketCity extends AppCompatActivity {


    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket_city);

        //to fill first progress bar
//        ProgressBar cityProgressBar = findViewById(R.id.cityProgressBar);
//        cityProgressBar.setProgress(100);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavbar);
//        bottomNavigationView.setSelectedItemId(R.id.ticket);
        frameLayout = (FrameLayout) findViewById(R.id.frame_container);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new city())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();


    }

    private void fragment_handler(int city) {
        Fragment fragment = null;
        switch (city) {
            case 0:
                fragment = new city();
                break;
            case 1:
                fragment = new erbil_fragment();
                break;
            case 2:
                fragment = new suli_fragment();
                break;
            case 3:
                fragment = new karkuk_fragment();
            case 4:
                fragment = new dhock_fragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();


    }


    public void backToCity(View view) {
        fragment_handler(0);
    }

    public void erbilFragmentHandler(View view) {
        fragment_handler(1);
    }
    public void suliFragmentHandler(View view) {
        fragment_handler(2);
    }
    public void karkukFragmentHandler(View view) {
        fragment_handler(3);
    }
    public void dhockFragmentHandler(View view) {
        fragment_handler(1);
    }


}


