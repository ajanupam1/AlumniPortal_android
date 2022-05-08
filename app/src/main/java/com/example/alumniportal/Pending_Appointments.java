package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Pending_Appointments extends AppCompatActivity {
ArrayList<Appointment_values>values ;
RecyclerView recyclerView ;
Appointment_Adapter appointment_adapter ;
FirebaseAuth firebaseAuth ;
FirebaseDatabase firebaseDatabase ;
DatabaseReference databaseReference ,databaseReference1;
String CurrentUser ;
String name ;
    ValueEventListener valueEventListener ;
    Profile_values post_values ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_appointments);
           ImageView back = findViewById(R.id.back_arrow);
        recyclerView = findViewById(R.id.recyclerview);
        values = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference().child("Appointments").child(CurrentUser).child("Pending");
        databaseReference1 = firebaseDatabase.getReference().child("Users");

        //getting data
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
//                    String Userid = data.child("User").getValue().toString() ;
                    Log.i("HERE",data.child("Date").getValue().toString()+data.child("Time").getValue().toString()+data.child("Name").getValue().toString() );
                    Appointment_values appointment_values = new Appointment_values(data.child("Date").getValue().toString() , data.child("Time").getValue().toString(),data.child("Name").getValue().toString(),data.child("Profile").getValue().toString(),
                            data.getRef(),CurrentUser);
                    values.add(appointment_values);
                }
                appointment_adapter = new Appointment_Adapter(values);
                recyclerView.setAdapter(appointment_adapter);
                appointment_adapter.notifyDataSetChanged ();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Appointment.class);
                startActivity(i);
                finish();
            }
        });
    }
}