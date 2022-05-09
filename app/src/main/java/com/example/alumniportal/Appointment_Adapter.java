package com.example.alumniportal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Appointment_Adapter extends RecyclerView.Adapter<Appointment_Adapter.ViewHolder> {
    public ArrayList<Appointment_values> post_values;
    public Appointment_Adapter(ArrayList<Appointment_values> post_values){
        this.post_values = post_values ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.appointment_queue,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Appointment_Adapter.ViewHolder holder, int position) {
        Appointment_values values = post_values.get(position);
        holder.name.setText(values.getName());
        holder.dateandtime.setText(values.getDate()+","+values.getTime());


        //profilepic
        DownloadTask downloadTask = new DownloadTask();
        try{
            Bitmap profilepic = downloadTask.execute(values.getUrl()).get();
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
        TextView dateandtime;
        ImageView menubutton ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.ivprofile_image);
            name = itemView.findViewById(R.id.ivname);
            dateandtime = itemView.findViewById(R.id.dateandtime);
            menubutton = itemView.findViewById(R.id.dropdown_menu);
            menubutton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Showpopupmenu(v);
        }
        private void Showpopupmenu(View v){
            PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.acceptance);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId()==R.id.accept){
                            int redColorValue = Color.RED;
                            int greenColorValue = Color.GREEN;
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                Appointment_values post = post_values.get(getAdapterPosition());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("Appointments").child(post.getUserid()).child("Confirmed");
                DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("Appointments").child(post.getUserid()).child("My_Booking");

                String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

                databaseReference2.child(currentDateTime).child("Name").setValue(firebaseAuth.getUid());
                databaseReference2.child(currentDateTime).child("User").setValue(post.getUserid());
                databaseReference2.child(currentDateTime).child("Date").setValue(post.getDate());
                databaseReference2.child(currentDateTime).child("Time").setValue(post.getTime());
                databaseReference2.child(currentDateTime).child("App").setValue("true");

                databaseReference1.child(currentDateTime).child("Profile").setValue(post.getUrl());
                databaseReference1.child(currentDateTime).child("Name").setValue(post.getName());
                databaseReference1.child(currentDateTime).child("User").setValue(post.getUserid());
                databaseReference1.child(currentDateTime).child("Date").setValue(post.getDate());
                databaseReference1.child(currentDateTime).child("Time").setValue(post.getTime());
                DatabaseReference databaseReference = post.getDatabaseReference();
                databaseReference.removeValue();
                post_values.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                return true ;
            }
            else if(item.getItemId()==R.id.decline){
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                Appointment_values post = post_values.get(getAdapterPosition());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("Appointments").child(post.getUserid()).child("My_Booking");

                String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

                databaseReference2.child(currentDateTime).child("Name").setValue(firebaseAuth.getUid());
                databaseReference2.child(currentDateTime).child("User").setValue(post.getUserid());
                databaseReference2.child(currentDateTime).child("Date").setValue(post.getDate());
                databaseReference2.child(currentDateTime).child("Time").setValue(post.getTime());
                databaseReference2.child(currentDateTime).child("App").setValue("false");

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
