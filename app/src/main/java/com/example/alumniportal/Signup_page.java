package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup_page extends AppCompatActivity {
private FirebaseAuth mAuth;
    EditText user ; EditText password ; EditText fullname ;
    TextView loginpage ;
//    FirebaseFirestore fstore ;
    DatabaseReference databaseReference ;
    FirebaseDatabase firebaseDatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
          user = findViewById(R.id.username) ;
          password = findViewById(R.id.password) ;
          loginpage = findViewById(R.id.loginpage) ;
          fullname = findViewById(R.id.fullname) ;

        mAuth = FirebaseAuth.getInstance();
//        fstore = FirebaseFirestore.getInstance();
         firebaseDatabase = FirebaseDatabase.getInstance();

        // full screen

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // loginpage switch
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , Login_page.class) ;
                startActivity(i);
                finish();
            }
        });

        // sign up button
        Button signup = findViewById(R.id.signupbutton) ;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(isValid(user.getText().toString())){
               firebaseSignup(user.getText().toString() , password.getText().toString()) ;
                }
                else {
                   Toast.makeText(Signup_page.this , "Enter Valid email address" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // firebase authenication
    void firebaseSignup(String email , String pass){
        mAuth.createUserWithEmailAndPassword(email , pass)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isComplete()){
                             // email verification
                             FirebaseUser user = mAuth.getCurrentUser();
                             user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     // sending full name to firestore
//                                     DocumentReference db = fstore.collection("users").document(userId) ;
//                                     Map<String , Object> name = new HashMap<>() ;
//                                     name.put("fname" , fullname.getText().toString());

                                     databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
                                     UserInfo userInfo = new UserInfo(fullname.getText().toString(),"","","","","","","","");

                                     databaseReference.setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void unused) {
                                             Intent i = new Intent(getApplicationContext() , Verification_page.class) ;
                                             startActivity(i);
                                         }
                                     }).addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull  Exception e) {
                                             Log.i("error" , e.getMessage().toString()) ;
                                         }
                                     });
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull  Exception e) {
                                     Log.w("error", "createUserWithEmail:failure", task.getException());
                                 }
                             });

                             Log.i("User sign up" , "createUserWithEmail:success");

                         }
                         else {
                             FirebaseAuthException e = (FirebaseAuthException )task.getException();
                             Log.i("error", e.getMessage());
                             Toast.makeText(Signup_page.this, "Signup Unsuccessfull",
                                     Toast.LENGTH_SHORT).show();
                         }
                    }
                });
    }
// valid email address
    boolean isValid(String s){

        if(s.length() == 0)return false ;

        for(int i =0 ; i<s.length() ; i++){
            if(s.charAt(i) == '@'){
                s = s.substring(i) ;
                break ;
            }
        }
        Log.i("this" , s) ;
        if(s.equals("@iiitl.ac.in")){return true ;}

        return false ;
    }


}