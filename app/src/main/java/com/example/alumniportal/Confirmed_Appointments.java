package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Confirmed_Appointments extends AppCompatActivity {
ArrayList<Appointment_values> values ;
Appointment_confirmed_adapter appointment_confirmed_adapter ;
DatabaseReference databaseReference ;
FirebaseAuth firebaseAuth ;
FirebaseDatabase firebaseDatabase ;
String CurrentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_appointments);

        ImageView back =findViewById(R.id.back_arrow);

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = firebaseAuth.getUid() ;
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference().child("Appointments").child(CurrentUser).child("Confirmed");

        RecyclerView recyclerView = findViewById(R.id.recyclerview) ;
        values = new ArrayList<>() ;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                       Appointment_values ap = new Appointment_values(data.child("Date").getValue().toString(),data.child("Time").getValue().toString(),
                               data.child("Name").getValue().toString(),data.child("Profile").getValue().toString(),data.getRef() ,data.child("User").getValue().toString());
                       values.add(ap);
                }
                appointment_confirmed_adapter = new Appointment_confirmed_adapter(values);
                recyclerView.setAdapter(appointment_confirmed_adapter);
                appointment_confirmed_adapter.notifyDataSetChanged ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Appointment.class);
                startActivity(i);
                finish();
            }
        });

    }
}