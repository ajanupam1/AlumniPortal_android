package com.example.alumniportal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.ViewHolder> {
    public ArrayList<Post_values> post_values;
    public Recycler_Adapter(ArrayList<Post_values> post_values){
        this.post_values = post_values ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
     View view = layoutInflater.inflate(R.layout.post_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Recycler_Adapter.ViewHolder holder, int position) {
         Post_values values = post_values.get(position);
        holder.name.setText(values.getName());
        holder.gyear.setText(values.getYear());
        holder.exp.setText(values.getExp());
        holder.company_name.setText(values.getCompany_name());

        //profilepic
        DownloadTask downloadTask = new DownloadTask();
        try{
            Bitmap profilepic = downloadTask.execute(values.getProfile_pic()).get();
           holder.profile_picture.setImageBitmap(profilepic);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return post_values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        ImageView profile_picture ;
        TextView name ;
        TextView gyear ;
        EditText company_name ;
        EditText exp ;
        ImageView menubutton ;
        Button button ;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.ivprofile_image);
            name = itemView.findViewById(R.id.ivname);
            gyear = itemView.findViewById(R.id.ivgyear);
            company_name = itemView.findViewById(R.id.ivcompany_name);
            exp = itemView.findViewById(R.id.ivexp);
            button = itemView.findViewById(R.id.save);
            menubutton = itemView.findViewById(R.id.dropdown_menu);
            recyclerView = itemView.findViewById(R.id.recyclerview);
            menubutton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Showpopupmenu(v);
        }

        private void Showpopupmenu(View v){
            PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId()==R.id.edit){
                menubutton.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
                company_name.setEnabled(true);
                exp.setEnabled(true);
                company_name.setFocusable(true);
                exp.setFocusable(true);
                company_name.setFocusableInTouchMode(true);
                exp.setFocusableInTouchMode(true);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("Feeds").child(firebaseAuth.getUid());
                        Post_values post = post_values.get(getAdapterPosition());
                        DatabaseReference databaseReference = post.getDatabaseReference();
                        databaseReference.child("Company").setValue(company_name.getText().toString());
                        databaseReference.child("Exp").setValue(exp.getText().toString());
                        menubutton.setVisibility(View.VISIBLE);
                        button.setVisibility(View.INVISIBLE);
                        company_name.setEnabled(false);
                        exp.setEnabled(false);
                        company_name.setFocusable(false);
                        exp.setFocusable(false);
                        company_name.setFocusableInTouchMode(false);
                        exp.setFocusableInTouchMode(false);
//feeds value
                        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                        databaseReference1.child(currentDateTime).child("Profilepic").setValue(post.getProfile_pic());
                        databaseReference1.child(currentDateTime).child("Name").setValue(post.getName());
                        databaseReference1.child(currentDateTime).child("Year").setValue(post.getYear());
                        databaseReference1.child(currentDateTime).child("Company").setValue(company_name.getText().toString());
                        databaseReference1.child(currentDateTime).child("Exp").setValue(exp.getText().toString());
                        databaseReference1.child(currentDateTime).child("UserId").setValue(firebaseAuth.getUid());

                    }
                });
               return true ;
            }
            else if(item.getItemId()==R.id.delete){
                Post_values post = post_values.get(getAdapterPosition());
                DatabaseReference databaseReference = post.getDatabaseReference();
                databaseReference.removeValue();
                post_values.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());

                return true ;
            }
           else {
                    return false;
            }
        }
    }
}
