package com.example.alumniportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ImageView ca = findViewById(R.id.confirm_appointments);
        ImageView pa = findViewById(R.id.pending_appointments);
        ImageView ma = findViewById(R.id.get_appoinmtent);
        ImageView back = findViewById(R.id.back_arrow);

        //back arrow
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Home_Page.class);
                startActivity(i);
                finish();
            }
        });

        //confirm Appointments
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Confirmed_Appointments.class);
                startActivity(i);
                finish();
            }
        });

        //pending Appointments
        pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Pending_Appointments.class);
                startActivity(i);
                finish();
            }
        });

        // my appointments
        ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Get_appointments.class);
                startActivity(i);
                finish();
            }
        });
    }
}