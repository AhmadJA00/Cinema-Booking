package com.example.cinemanabooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class seats extends AppCompatActivity {

    TextView seatsA;
    boolean [] selectedSeatA;
    ArrayList<Integer> AseatsList = new ArrayList<>();
    int numOFSeats =20;
    String AseatsArray [] = new String[numOFSeats];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);

        //creating the seats for each row and saving it in an array
        for (int i = 1; i <=numOFSeats ; i++) {
            AseatsArray[i]="A"+i;
        }

        seatsA=findViewById(R.id.dropdownSeatsA);

        selectedSeatA=new boolean[AseatsArray.length];

        seatsA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builderA=new AlertDialog.Builder(
                        seats.this
                );
                //title for the dialog
                builderA.setTitle("Select the seats");

                //set choosing multiple values
                builderA.setMultiChoiceItems(AseatsArray, selectedSeatA, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked){
                            //when checkbox is selected
                            //add item to arrayList
                            AseatsList.add(which);
                            // for sorting the choices
                            Collections.sort(AseatsList);
                        }else {
                            // if checkbox is not selected
                            //remove item from list
                            AseatsList.remove(which);
                        }
                    }
                });
                builderA.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        seatsA.setText("");
                        //initialize String Builder
                        StringBuilder stringBuilder=new StringBuilder();
                        //using for loop to build a string from selected items
                        for (int i = 0; i < AseatsList.size(); i++) {
                            //concate array value
                            stringBuilder.append(AseatsArray[AseatsList.get(i)]);
                            //check the condition if the i is not equal to AseatsList size -1
                            if (i != AseatsList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }

                        //setting text on textView to be shown when clicking ok
                        seatsA.setText(stringBuilder.toString());

                    }
                });

                builderA.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancelling the process
                        dialog.dismiss();
                    }
                });

                builderA.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //using for loop to make all values unselected
                        for (int i = 0; i < selectedSeatA.length; i++) {
                            selectedSeatA[i]= false;
                            //clear list
                            AseatsList.clear();
                            //clear the text in text view
                            seatsA.setText("A");
                        }
                    }
                });
                builderA.show();
            }
        });


    }
}