package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Profile_visit extends AppCompatActivity {
    String data = "" ;
    String Currentuser ;
    Profile_values post_values,profile_values ;
    FirebaseDatabase firebaseDatabase ;
    FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3,databaseReference4 ;
    String cc,cf,ll,l;
    Bitmap profilepic ;
    ArrayList<Home_post_values> values ;
    Profile_Adapter home_adapter ;
    RecyclerView recyclerView ;
    String full_name,url,user_name,profile_url ;

    int day, month, year, hour, minute,flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_visit);
      data = getIntent().getExtras().getString("Userid") ;
      // variables
        TextView name = findViewById(R.id.name);
        TextView gy = findViewById(R.id.graduation_year);
        TextView cpos = findViewById(R.id.current_position);
        TextView ccom = findViewById(R.id.current_company);
         ImageView profile = findViewById(R.id.profile_image);
        ImageView codeforces = findViewById(R.id.codeforces) ;
        ImageView codechef = findViewById(R.id.codechef) ;
        ImageView leetcode = findViewById(R.id.leetcode) ;
        ImageView linkedin = findViewById(R.id.linkedin) ;
        ImageView back = findViewById(R.id.back_arrow);
        ImageView dnt = findViewById(R.id.dateandtime);
        TextView dnt1 = findViewById(R.id.dnt);
        Button submit = findViewById(R.id.submitbutton);

        //recyclerview
        values = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1);

       firebaseAuth = FirebaseAuth.getInstance();
       Currentuser = firebaseAuth.getUid().toString();
      firebaseDatabase = FirebaseDatabase.getInstance() ;
      databaseReference = firebaseDatabase.getReference().child("Users");
      databaseReference1 = firebaseDatabase.getReference().child("Posts");
      databaseReference2 = firebaseDatabase.getReference().child("Appointments").child(data).child("Pending");
      databaseReference3 = firebaseDatabase.getReference().child("Appointments").child(data).child("Confirmed");
      databaseReference4 = firebaseDatabase.getReference().child("Appointments").child(Currentuser).child("My_Booking");



      //getting data of the user
        databaseReference.child(Currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                profile_values = snapshot.getValue(Profile_values.class);
                full_name = profile_values.getFull_name().toString();
                url = profile_values.getProfile().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference.orderByChild(Currentuser).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull  DataSnapshot snapshot,  String previousChildName) {
//                profile_values = snapshot.getValue(Profile_values.class);
//                full_name = profile_values.getFull_name().toString();
//                url = profile_values.getProfile().toString();
//                Log.i("here" , full_name);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull  DataSnapshot snapshot,  String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//
//            }
//        });

        databaseReference.child(data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                post_values = snapshot.getValue(Profile_values.class);
                name.setText(post_values.getFull_name());
                user_name = post_values.getFull_name();
                gy.setText(post_values.getGraduation_year());
                cpos.setText(post_values.getCurrent_position());
                ccom.setText(post_values.getCurrent_company());
                l = post_values.getLeetcode();
                ll = post_values.getLinkedin();
                cc = post_values.getCodechef();
                cf = post_values.getCodeforces();
                profile_url = post_values.getProfile();
                //profilepic
                DownloadTask downloadTask = new DownloadTask();
                try{
                    profilepic = downloadTask.execute(post_values.getProfile()).get();
                    profile.setImageBitmap(profilepic);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
//        databaseReference.orderByChild(data).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
//             post_values = snapshot.getValue(Profile_values.class);
//             name.setText(post_values.getFull_name());
//             user_name = post_values.getFull_name();
//             gy.setText(post_values.getGraduation_year());
//             cpos.setText(post_values.getCurrent_position());
//             ccom.setText(post_values.getCurrent_company());
//                l = post_values.getLeetcode();
//                ll = post_values.getLinkedin();
//                cc = post_values.getCodechef();
//                cf = post_values.getCodeforces();
//                profile_url = post_values.getProfile();
//                //profilepic
//                DownloadTask downloadTask = new DownloadTask();
//                try{
//                    profilepic = downloadTask.execute(post_values.getProfile()).get();
//                    profile.setImageBitmap(profilepic);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//
//            }
//        });

        //codechef
        codechef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cc.equals("")) Toast.makeText(Profile_visit.this, "Invalid link", Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(getApplicationContext() , Url_visit.class);
                    intent.putExtra("url" , cc);
                    startActivity(intent);}
            }
        });
        //codeforces
        codeforces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cf.equals("")) Toast.makeText(Profile_visit.this, "Invalid link", Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(getApplicationContext() , Url_visit.class);
                    intent.putExtra("url" , cf);
                    startActivity(intent);}
            }
        });
        //leetcode
        leetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l.equals("")) Toast.makeText(Profile_visit.this, "Invalid link", Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(getApplicationContext() , Url_visit.class);
                    intent.putExtra("url" , l);
                    startActivity(intent);}
            }
        });
        // linkedin
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll.equals("")) Toast.makeText(Profile_visit.this, "Invalid link", Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(getApplicationContext() , Url_visit.class);
                    intent.putExtra("url" , ll);
                    startActivity(intent);}
            }
        });
  // back arrow
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Home_Page.class);
                startActivity(i);
                finish();
            }
        });

        // getting posts for user
        databaseReference1.child(data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                for(DataSnapshot data1 :snapshot.getChildren()){
                    Home_post_values post_values = new Home_post_values(data1.child("Profilepic").getValue().toString() , data1.child("Name").getValue().toString(),data1.child("Year").getValue().toString() ,
                            data1.child("Company").getValue().toString(),data1.child("Exp").getValue().toString(),data ,data1.getRef());
                   Log.i("posts", data1.child("Company").getValue().toString());
                    values.add(post_values);
                }
                home_adapter = new Profile_Adapter(values);
                recyclerView.setAdapter(home_adapter);
                home_adapter.notifyDataSetChanged ();
                recyclerView.setLayoutManager(new LinearLayoutManager(Profile_visit.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference1.orderByChild(data).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
//                for(DataSnapshot data1 :snapshot.getChildren()){
//                    Home_post_values post_values = new Home_post_values(data1.child("Profilepic").getValue().toString() , data1.child("Name").getValue().toString(),data1.child("Year").getValue().toString() ,
//                            data1.child("Company").getValue().toString(),data1.child("Exp").getValue().toString(),data ,data1.getRef());
//                   Log.i("posts", data1.child("Company").getValue().toString());
//                    values.add(post_values);
//                }
//                home_adapter = new Profile_Adapter(values);
//                recyclerView.setAdapter(home_adapter);
//                home_adapter.notifyDataSetChanged ();
//                recyclerView.setLayoutManager(new LinearLayoutManager(Profile_visit.this));
//            }
//
//            @Override
//            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//
//            }
//        });

        //date and time dialogue
          dnt.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Calendar calendar = Calendar.getInstance();
                  year = calendar.get(Calendar.YEAR);
                  month = calendar.get(Calendar.MONTH);
                  day = calendar.get(Calendar.DAY_OF_MONTH);
                  DatePickerDialog datePickerDialog = new DatePickerDialog(Profile_visit.this, new DatePickerDialog.OnDateSetListener() {
                      @Override
                      public void onDateSet(DatePicker view, int y, int m, int dayOfMonth) {
                          year = y ;
                          month = m ;
                          day = dayOfMonth ;
                          Calendar c = Calendar.getInstance() ;
                          hour = c.get(Calendar.HOUR);
                          minute = c.get(Calendar.MINUTE);
                          TimePickerDialog timePickerDialog = new TimePickerDialog(Profile_visit.this, new TimePickerDialog.OnTimeSetListener() {
                              @Override
                              public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                                  hour = hourOfDay ;
                                  minute = min ;
                                  dnt1.setText(day+"/"+month+"/"+year+" , "+hour+":"+minute);
                              }
                          },hour,minute,true);
                          timePickerDialog.show();
                      }
                  },year,month,day);
                  datePickerDialog.show();
              }
          });

          //submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        for(DataSnapshot d:snapshot.getChildren()){
                            if(d.child("Date").getValue().equals(day+"/"+month+"/"+year)&& d.child("Time").getValue().equals(hour+":"+minute)){
                                Log.i("here" , "oloo");
                                flag =0 ;
                                Toast.makeText(Profile_visit.this, "Please Select different time", Toast.LENGTH_SHORT).show();
                                break ;
                            }
                        }
                        if(flag == 1){
                                       String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
//                            databaseReference4.child(currentDateTime).child("Name").setValue(user_name);
//                            databaseReference4.child(currentDateTime).child("Profile").setValue(profile_url);
//                            databaseReference4.child(currentDateTime).child("User").setValue(data);
//                            databaseReference4.child(currentDateTime).child("Date").setValue(day+"/"+month+"/"+year);
//                            databaseReference4.child(currentDateTime).child("Time").setValue(hour+":"+minute);




                                       databaseReference2.child(currentDateTime).child("Admin").setValue(data);
                                       databaseReference2.child(currentDateTime).child("Profile").setValue(url);
                                       databaseReference2.child(currentDateTime).child("Name").setValue(full_name);
                                       databaseReference2.child(currentDateTime).child("User").setValue(Currentuser);
                                       databaseReference2.child(currentDateTime).child("Date").setValue(day+"/"+month+"/"+year);
                                       databaseReference2.child(currentDateTime).child("Time").setValue(hour+":"+minute);
                               Toast.makeText(Profile_visit.this, "Slot Booked!!", Toast.LENGTH_SHORT).show();
                                   }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                databaseReference3.addValueEventListener(valueEventListener);
            }
        });
    }

}