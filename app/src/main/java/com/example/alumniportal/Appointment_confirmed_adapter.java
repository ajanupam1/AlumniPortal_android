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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumniportal.Appointment_values;
import com.example.alumniportal.DownloadTask;
import com.example.alumniportal.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Appointment_confirmed_adapter extends RecyclerView.Adapter<Appointment_confirmed_adapter.ViewHolder> {
    public ArrayList<Appointment_values> post_values;
    public Appointment_confirmed_adapter(ArrayList<Appointment_values> post_values){
        this.post_values = post_values ;
    }

    @NonNull
    @Override
    public Appointment_confirmed_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.confirmed_appointments,parent,false);
        return new Appointment_confirmed_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Appointment_confirmed_adapter.ViewHolder holder, int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_picture ;
        TextView name ;
        TextView dateandtime;
        TextView app,rej ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.ivprofile_image);
            name = itemView.findViewById(R.id.ivname);
            dateandtime = itemView.findViewById(R.id.dateandtime);
            app = itemView.findViewById(R.id.approved);
            rej = itemView.findViewById(R.id.rejected);

        }

    }
}
