package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.channels.InterruptedByTimeoutException;

public class Verification_page extends AppCompatActivity {
FirebaseAuth mAuth ;
Button resendbutton ;
TextView login ;

    FirebaseUser user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);
        login = findViewById(R.id.loginpage) ;
        // login swap
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Login_page.class) ;
                startActivity(i);
                finish();
            }
        });


//verification email
         mAuth = FirebaseAuth.getInstance();
       user = mAuth.getCurrentUser();
        if(user.isEmailVerified()){
            Intent i = new Intent(getApplicationContext() , Login_page.class) ;
            startActivity(i) ;
        }
        resendbutton = findViewById(R.id.resendbutton) ;

        resendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 user = mAuth.getCurrentUser();
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                   public void onSuccess(Void unused) {
                        Intent i = new Intent(getApplicationContext() , Login_page.class) ;
                        startActivity(i);
                    }
               }).addOnFailureListener(new OnFailureListener() {
                @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error", "createUserWithEmail:failure");
                   }
                });
            }
        });
    }
}
