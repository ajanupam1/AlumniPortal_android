package com.example.alumniportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


//public class DownloadTask extends AsyncTask<String , void , String>(){
//
//};
public class Resources extends AppCompatActivity {
    DownloadManager.Request request ;
    Button uploadfile , selectfile ;
ListView list ;
String filepath= "" ;
StorageTask uploadtask ;
    Uri file ;
    ArrayList<String> notes = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter ;
    ArrayList<String> downloadlinks = new ArrayList<String>();
StorageReference storageReference ;
int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        //storage runtime permission
//        if(Build.VERSION.SDK >= Build.VERSION_CODES.M){
//
//        }

        ImageView dropdown = findViewById(R.id.drop_menu);

        uploadfile = findViewById(R.id.uploadfiles) ;
        selectfile = findViewById(R.id.selectfile) ;
        list = findViewById(R.id.listview) ;
        arrayAdapter = new ArrayAdapter<String>(Resources.this , android.R.layout.simple_list_item_1, notes) ;
        list.setAdapter(arrayAdapter);


        storageReference = FirebaseStorage.getInstance().getReference().child("Resources") ;

        // select file
        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent() ;
                i.setType("*/*") ;
                i.setAction(Intent.ACTION_GET_CONTENT) ;
                startActivityForResult(Intent.createChooser(i , "Select Picture" ) , flag);
            }
        });

        // upload file
        uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file != null){
                  final StorageReference s = storageReference.child(file.getLastPathSegment()) ;
                  uploadtask = s.putFile(file) ;
                  uploadtask.continueWithTask(new Continuation() {
                      @Override
                      public Object then(@NonNull Task task) throws Exception {
                         if(!task.isSuccessful()){
                             Log.i("error" , task.getException().toString());
                         }
                         return s.getDownloadUrl() ;
                      }
                  }).addOnCompleteListener(new OnCompleteListener() {
                      @Override
                      public void onComplete(@NonNull  Task task) {
                             if(task.isSuccessful()){
                              Log.i("success" , "yeahh");
                             }
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull  Exception e) {

                      }
                  });
                }
                else{
                    Log.i("error" , "lol") ;
                }

            }
        });

        // getting files back

      StorageReference s =  storageReference;
      s.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
          @Override
          public void onSuccess(ListResult listResult) {
              for (StorageReference item : listResult.getItems()) {
                 item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         String link = uri.toString() ;
                         downloadlinks.add(link);
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull  Exception e) {
                         Log.i("error" , e.toString()) ;
                     }
                 });
                  notes.add(item.getName()) ;
                  arrayAdapter.notifyDataSetChanged();
              }
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull  Exception e) {
           Log.i("error" , e.toString()) ;
          }
      });

//              addOnCompleteListener(new OnCompleteListener<ListResult>() {
//                  @Override
//                  public void onComplete(@NonNull  Task<ListResult> task) {
//                      if(!task.isSuccessful()) Log.i("error" , "error") ;
//                      else{
//                          for (StorageReference item : task.getResult().getItems()) {
//                              notes.add(item.getName()) ;
//                              arrayAdapter.notifyDataSetChanged();
//                          }
//                      }
//                  }
//              }).addOnFailureListener(new OnFailureListener() {
//          @Override
//          public void onFailure(@NonNull Exception e) {
//              Log.i("eroor" , "error") ;
//          }
//      });
      // list view listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Resources.this, "working", Toast.LENGTH_SHORT).show();
//                if(downloadlinks.size()!=1){
//                Log.i("herer" , downloadlinks.get(position)) ;}
                downloadfile(Resources.this , notes.get(position) , ".pdf" , DIRECTORY_DOWNLOADS , downloadlinks.get(position));
            }
        });
// drop down
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() ,r_menu.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == flag && resultCode == RESULT_OK) {
            file = data.getData();
        }
    }

    //downloading file
    public void downloadfile(Context context , String filename , String fileExtention ,String Destdic , String url){
 DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
 Uri uri = Uri.parse(url) ;
 DownloadManager.Request request= new DownloadManager.Request(uri) ;
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) ;
    request.setDestinationInExternalFilesDir(context , Destdic , filename +fileExtention) ;
    downloadManager.enqueue(request) ;
    }

}