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

public class Get_appointments extends AppCompatActivity {
    ArrayList<My_booking> values ;
    My_booking_adapter appointment_confirmed_adapter ;
    DatabaseReference databaseReference ;
    FirebaseAuth firebaseAuth ;
    FirebaseDatabase firebaseDatabase ;
    String CurrentUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_appointments);

        ImageView back =findViewById(R.id.back_arrow);

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = firebaseAuth.getUid() ;
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference().child("Appointments").child(CurrentUser).child("My_Booking");

        RecyclerView recyclerView = findViewById(R.id.recyclerview) ;
        values = new ArrayList<>() ;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    My_booking ap = new My_booking(data.child("Name").getValue().toString(),data.child("Date").getValue().toString(),
                            data.child("User").getValue().toString(),data.child("Time").getValue().toString(),data.child("App").getValue().toString());
                    values.add(ap);
                }
                appointment_confirmed_adapter = new My_booking_adapter(values);
                recyclerView.setAdapter(appointment_confirmed_adapter);
//                My_booking_adapter.notifyDataSetChanged ();
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