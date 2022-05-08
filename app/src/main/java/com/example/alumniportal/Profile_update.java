package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_update extends AppCompatActivity {
EditText fullname , current_company , position , gyear , lp , ccp , cfp , llp ;
//FirebaseFirestore fstore ;
FirebaseDatabase firebaseDatabase ;
    FirebaseAuth mAuth ;
StorageReference storage ;
CircleImageView profile_image ;
StorageTask upload ;
Button submit , updatepp;
String userId ; String profilepicurl ="" ;
Uri imageuri ;
int flag = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        fullname = findViewById(R.id.fullname) ;
        current_company = findViewById(R.id.current_company) ;
        position = findViewById(R.id.position);
        gyear = findViewById(R.id.gyear) ;
        lp = findViewById(R.id.leetcode) ;
        ccp = findViewById(R.id.codechef) ;
        cfp = findViewById(R.id.codeforces) ;
        llp = findViewById(R.id.linkedin) ;
        submit = findViewById(R.id.submitbutton) ;
        profile_image = findViewById(R.id.profile_image) ;
     Log.i("hererre" , gyear.getText().toString());
        mAuth = FirebaseAuth.getInstance() ;
//        fstore = FirebaseFirestore.getInstance() ;
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        userId = mAuth.getCurrentUser().getUid() ;
        DatabaseReference databaseReference = firebaseDatabase.getReference() ;
        storage = FirebaseStorage.getInstance().getReference().child("Profile Pic") ;

//         upload profile pic
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent() ;
                i.setType("image/*") ;
                i.setAction(Intent.ACTION_GET_CONTENT) ;
                startActivityForResult(Intent.createChooser(i , "Select Picture" ) , flag);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // sending full name to firestore
//                DocumentReference db = fstore.collection("users").document(userId) ;
//
//                Map<String , Object> name = new HashMap<>() ;
//                name.put("fname" , fullname.getText().toString());
//                name.put("current_company" , current_company.getText().toString());
//                name.put("current_position" , position.getText().toString());
//                name.put("graduation_year" , gyear.getText().toString());
//                name.put("leetcode" , lp.getText().toString());
//                name.put("codechef" , ccp.getText().toString());
//                name.put("codeforces" , cfp.getText().toString());
//                name.put("linkedin" , llp.getText().toString());

                if (!fullname.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("full_name").setValue(fullname.getText().toString());

                if (!current_company.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("current_company").setValue(current_company.getText().toString());

                if (!position.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("current_position").setValue(position.getText().toString());

                if (!gyear.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("graduation_year").setValue(gyear.getText().toString());

                if (!ccp.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("codechef").setValue(ccp.getText().toString());

                if (!cfp.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("codeforces").setValue(cfp.getText().toString());

                if (!llp.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("linkedin").setValue(llp.getText().toString());

                if (!lp.getText().toString().equals(""))
                    databaseReference.child("Users").child(userId).child("leetcode").setValue(lp.getText().toString());

                if (imageuri != null) {
                    //Progress bar
                    ProgressDialog progressDialog = new ProgressDialog(Profile_update.this);
                    progressDialog.show();
                    final StorageReference s = storage.child(userId + ".jpg");
                    Log.i("tag", imageuri.toString());

                    upload = s.putFile(imageuri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            int current = (int)progress;
                            progressDialog.setMessage("Uploaded "+current+"%");
                        }
                    });

                    upload.continueWithTask(new Continuation() {
                        @Override
                        public Object then(@NonNull Task task) throws Exception {
                            if (!task.isSuccessful()) {
                                Log.i("error", task.getException().toString());
                            }
                            return s.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Uri downloadlink = (Uri) task.getResult();
                                profilepicurl = downloadlink.toString();
//                                Log.i("here!!!!!!!!!!!!!!!!!!" , profilepicurl) ;
//                                name.put("profilepic" , profilepicurl) ;

                                if (!profilepicurl.equals(""))
                                    databaseReference.child("Users").child(userId).child("profile").setValue(profilepicurl);
                                     progressDialog.dismiss();
                                Toast.makeText(Profile_update.this, "Profile Updated!!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Profile_Page.class);
                                startActivity(i);

//                                UserInfo userInfo = new UserInfo(fullname.getText().toString(),current_company.getText().toString(),
//                                        position.getText().toString()," ",lp.getText().toString(), ccp.getText().toString(),
//                                        cfp.getText().toString(),llp.getText().toString(),profilepicurl);
//                                     Log.i("username" , fullname.getText().toString());
//                                databaseReference.child("Users").child(userId).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(Profile_update.this, "Success", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(getApplicationContext() , Profile_Page.class) ;
//                                        startActivity(i);
//                                    }
//                                });

//                                db.set(name).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(Profile_update.this, "Success", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(getApplicationContext() , Verification_page.class) ;
//                                        startActivity(i);
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.i("error" , e.getMessage().toString()) ;
//                                    }
//                                });

                            } else {
                                Log.i("error", task.getException().toString());
                            }
                        }
                    });
                }
                else{

                    Toast.makeText(Profile_update.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Profile_Page.class);
                    startActivity(i);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == flag && resultCode == RESULT_OK){
            imageuri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri) ;
                profile_image.setImageBitmap(bitmap);

            }catch (IOException e){
                Log.i("error" , e.toString()) ;
            }
        }
    }
}