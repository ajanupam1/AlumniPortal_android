package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

class DownloadTask extends AsyncTask<String , Void , Bitmap>{

    @Override
    protected Bitmap doInBackground(String... strings) {
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        }catch (Exception e){
            e.printStackTrace();
            return null ;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}


public class Profile_Page extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout ;
    RecyclerView recyclerView ;
    Recycler_Adapter recycler_adapter;
    ArrayList<Post_values> values ;
    String cc,cf,ll,l;
    FirebaseAuth firebaseAuth ;
   FirebaseDatabase firebaseDatabase ;
   FirebaseDatabase firebaseDatabase1 ,firebaseDatabase2;
   DatabaseReference databaseReference1;
   DatabaseReference databaseReference,databaseReference2 ;
   String CurrentUser,pic,gradyear,username ;
    ImageView profilepicture;
    Bitmap profilepic ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        //Instances and references
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase1 = FirebaseDatabase.getInstance();
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        CurrentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference().child("Users").child(CurrentUser);
        databaseReference1 = firebaseDatabase1.getReference().child("Posts").child(CurrentUser);
        databaseReference2 = firebaseDatabase2.getReference().child("Feeds").child(CurrentUser);

        //Varaibles
        EditText iecompany = findViewById(R.id.cn);
        EditText ieexp = findViewById(R.id.exp);
        Button submitbutton = findViewById(R.id.submitbutton);
        TextView name = findViewById(R.id.name) ;
        TextView current_company = findViewById(R.id.current_company) ;
        TextView current_position = findViewById(R.id.current_position) ;
        TextView gyear = findViewById(R.id.graduation_year) ;
        ImageView pu = findViewById(R.id.profileupdate);
        profilepicture = findViewById(R.id.profile_image);
        ImageView codeforces = findViewById(R.id.codeforces) ;
        ImageView codechef = findViewById(R.id.codechef) ;
        ImageView leetcode = findViewById(R.id.leetcode) ;
        ImageView linkedin = findViewById(R.id.linkedin) ;
        ImageView dropdown = findViewById(R.id.drop_menu);

        //recyclerview
        values = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);

  ValueEventListener preListener = new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
         for(DataSnapshot data :snapshot.getChildren()){
             Post_values post_values = new Post_values(data.child("Profilepic").getValue().toString() , data.child("Name").getValue().toString(),data.child("Year").getValue().toString() ,
                     data.child("Company").getValue().toString(),data.child("Exp").getValue().toString(),data.getRef());

             values.add(post_values);
         }
          recycler_adapter = new Recycler_Adapter(values);
          recyclerView.setAdapter(recycler_adapter);
          recycler_adapter.notifyDataSetChanged ();
      }

      @Override
      public void onCancelled(@NonNull  DatabaseError error) {

      }
  };

        databaseReference1.addValueEventListener(preListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager());



      ValueEventListener postListener = new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull  DataSnapshot snapshot) {
//              UserInfo userInfo ;
//              for(DataSnapshot snap :snapshot.getChildren()){
//                  userInfo = snap.getValue(UserInfo.class);
//                  Log.i("values" , userInfo.getCurrent_company().toString());
//              }
              UserInfo userInfo = snapshot.getValue(UserInfo.class);
              name.setText(userInfo.getFull_name());
              current_company.setText(userInfo.getCurrent_company());
              current_position.setText(userInfo.getCurrent_position());
              gradyear = userInfo.getGraduation_year();
              gyear.setText(userInfo.getGraduation_year());
              username = userInfo.getFull_name();
              l = userInfo.getLeetcode();
              ll = userInfo.getLinkedin();
              cc = userInfo.getCodechef();
              cf = userInfo.getCodeforces();
              pic= userInfo.getProfile();
//
//              //profilepic
              DownloadTask downloadTask = new DownloadTask();
              try{
                  profilepic = downloadTask.execute(userInfo.getProfile()).get();
                  profilepicture.setImageBitmap(profilepic);
              }catch (Exception e){
                  e.printStackTrace();
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      };

      databaseReference.addValueEventListener(postListener);

      // profile update
        pu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Profile_update.class);
                startActivity(i);
            }
        });

        //codechef
        codechef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cc.equals("")) Toast.makeText(Profile_Page.this, "Invalid link", Toast.LENGTH_SHORT).show();
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
                if(cf.equals("")) Toast.makeText(Profile_Page.this, "Invalid link", Toast.LENGTH_SHORT).show();
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
                if(l.equals("")) Toast.makeText(Profile_Page.this, "Invalid link", Toast.LENGTH_SHORT).show();
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
                if(ll.equals("")) Toast.makeText(Profile_Page.this, "Invalid link", Toast.LENGTH_SHORT).show();
                else{
                Intent intent = new Intent(getApplicationContext() , Url_visit.class);
                intent.putExtra("url" , ll);
                startActivity(intent);}
            }
        });

        //submit button
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ieexp.getText().toString().equals("")||iecompany.getText().toString().equals("")){
                    Toast.makeText(Profile_Page.this, "All fields requried", Toast.LENGTH_SHORT).show();
                }
                else{
                    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                    databaseReference1.child(currentDateTime).child("Profilepic").setValue(pic);
                    databaseReference1.child(currentDateTime).child("Name").setValue(username);
                    databaseReference1.child(currentDateTime).child("Year").setValue(gradyear);
                    databaseReference1.child(currentDateTime).child("Company").setValue(iecompany.getText().toString());
                    databaseReference1.child(currentDateTime).child("Exp").setValue(ieexp.getText().toString());


                    databaseReference2.child(currentDateTime).child("Profilepic").setValue(pic);
                    databaseReference2.child(currentDateTime).child("Name").setValue(username);
                    databaseReference2.child(currentDateTime).child("Year").setValue(gradyear);
                    databaseReference2.child(currentDateTime).child("Company").setValue(iecompany.getText().toString());
                    databaseReference2.child(currentDateTime).child("Exp").setValue(ieexp.getText().toString());
                    databaseReference2.child(currentDateTime).child("UserId").setValue(CurrentUser);

                    Toast.makeText(Profile_Page.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Profile_Page.class);
                    startActivity(i);
                }
            }
        });


     //menu button
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , p_menu.class);
                startActivity(i);
                finish();
            }
        });
    }
}
