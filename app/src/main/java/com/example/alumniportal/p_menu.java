package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class p_menu extends AppCompatActivity {
    FirebaseAuth firebaseAuth ;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;
    String CurrentUser,name="",url ;
    Bitmap profilepic ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmenu);
        NavigationView navigationView = findViewById(R.id.nav_view);
       ImageView back_arrow = findViewById(R.id.back_arrow);

        //profile image
        ImageView profile = findViewById(R.id.pro_image);
        TextView fname = findViewById(R.id.name123);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        CurrentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference().child("Users").child(CurrentUser);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                name = snapshot.child("full_name").getValue().toString();
                url = snapshot.child("profile").getValue().toString() ;
                fname.setText(name);

                //profilepic
                DownloadTask downloadTask = new DownloadTask();
                try{
                    profilepic = downloadTask.execute(url).get();
                    profile.setImageBitmap(profilepic);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(listener);


// navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.feed){
                    Intent i = new Intent(getApplicationContext() , Home_Page.class);
                    startActivity(i);
                    finish();
                    return true ;
                }
                else if(item.getItemId() == R.id.appointment){
                    Intent i = new Intent(getApplicationContext() , Appointment.class);
                    startActivity(i);
                    finish();
                    return true ;
                }
                else if(item.getItemId() == R.id.res){
                    Intent i = new Intent(getApplicationContext() , Resources.class);
                    startActivity(i);
                    finish();
                    return true ;
                }
                else if(item.getItemId() == R.id.faq){
                    Intent i = new Intent(getApplicationContext() , FAQ.class);
                    startActivity(i);
                    finish();
                    return true ;
                }
                else if(item.getItemId() == R.id.dev){
                    Intent i = new Intent(getApplicationContext() , About_us.class);
                    startActivity(i);
                    finish();
                    return true ;
                }
                else if(item.getItemId() == R.id.logout){
                    Intent i = new Intent(getApplicationContext() , Login_page.class);
                    startActivity(i);
                    finish();
                    firebaseAuth.signOut();
                    return true ;
                }

                return false;
            }
        });

        // back arrow
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Profile_Page.class);
                startActivity(i);
                finish();
            }
        });
    }
}