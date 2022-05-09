package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {
    FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener ;
TextView signup , forget;
EditText username , password ;
Button loginbutton ;
FirebaseDatabase firebaseDatabase ;
DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        signup = findViewById(R.id.signuppage) ;
        username = findViewById(R.id.username) ;
        password = findViewById(R.id.password) ;
        loginbutton = findViewById(R.id.loginbutton) ;
       mAuth = FirebaseAuth.getInstance();
       firebaseDatabase = FirebaseDatabase.getInstance() ;
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        // checking if user is already signined
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser mfirebaseuser = mAuth.getCurrentUser() ;
//                if(mfirebaseuser!=null){
//                    Toast.makeText(Login_page.this, "Logged in", Toast.LENGTH_SHORT).show();
////                    Intent i = new Intent(getApplicationContext() , ) ;
////                    startActivity(i);
////                    finish();
//                }
//                else {
//                    Toast.makeText(Login_page.this, "Please Login", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };

        //checks if user is already logged in
        if(mAuth.getCurrentUser() != null){
            databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Profile_values profile_values = snapshot.getValue(Profile_values.class);
                    Toast.makeText(Login_page.this, "Welcome "+profile_values.getFull_name(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Profile_Page.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {

                }
            });
        }
        // reset password
        forget = findViewById(R.id.forget) ;
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reset = username.getText().toString() ;
                mAuth.sendPasswordResetEmail(reset).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Login_page.this, "Password reset link sent", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login_page.this, "Error occured!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // login button
   loginbutton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
               firebaseSignin(username.getText().toString() , password.getText().toString());
           }
           else{
               Toast.makeText(Login_page.this, "Enter Valid username", Toast.LENGTH_SHORT).show();
           }
       }
   });
   // sign up page swap
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Signup_page.class) ;
                startActivity(i) ;
                finish();
            }
        });
    }
    // firebase authenication
    void firebaseSignin(String email , String pass){
        mAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(Login_page.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login_page.this, "Logged In!!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser() ;
                    if(user.isEmailVerified()){
                        Intent i = new Intent(getApplicationContext() ,Profile_Page.class ) ;
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(Login_page.this, "Verify to login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
//            Intent i = new Intent(getApplicationContext() , ) ;
//            startActivity(i) ;
//            finish() ;
        }
    }
}