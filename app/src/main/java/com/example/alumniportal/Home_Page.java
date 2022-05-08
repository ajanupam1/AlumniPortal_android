package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Home_Page extends AppCompatActivity {
    ArrayList<Home_post_values> values ;
    Home_Adapter recycler_adapter;
    FirebaseFirestore firebaseFirestore ;
    FirebaseAuth firebaseAuth ;
    FirebaseDatabase firebaseDatabase ;
    String CurrentUser ;
    DatabaseReference databaseReference,databaseReference1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView dropview = findViewById(R.id.drop_menu);
        ImageView profile = findViewById(R.id.profile_image);
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Feeds");
        databaseReference1 = firebaseDatabase.getReference().child("Users").child(CurrentUser);


        //profile image
        DownloadTask downloadTask = new DownloadTask();
        try{
            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Profile_values profile_values = snapshot.getValue(Profile_values.class);
                    Bitmap profilepic = null;
                    try {
                        profilepic = downloadTask.execute(profile_values.getProfile()).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    profile.setImageBitmap(profilepic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //drop menu
        dropview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , h_menu.class);
                startActivity(i);
                finish();
            }
        });
      // Recycler view

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        values = new ArrayList<>();
        ValueEventListener preListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data :snapshot.getChildren()){
                    for(DataSnapshot data1 : data.getChildren()){
                        Home_post_values post_values = new Home_post_values(data1.child("Profilepic").getValue().toString() , data1.child("Name").
                                getValue().toString(),data1.child("Year").getValue().toString() ,
                                data1.child("Company").getValue().toString(),data1.child("Exp").getValue().toString(),data1.child("UserId").getValue().toString(),data1.getRef());
//             Log.i("val" , post_values.getCompany_name());
                        values.add(post_values);
                    }
                }
                recycler_adapter = new Home_Adapter(values);
                recyclerView.setAdapter(recycler_adapter);
                recycler_adapter.notifyDataSetChanged ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(preListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Profile_Page.class);
                startActivity(i);
            }
        });

    }

//    @Override
//    public void OnpostItemSelected(int position) {
//        Post_values p = values.get(position);
//        Log.i("value" , p.getCompany_name().toString());
//    }
}