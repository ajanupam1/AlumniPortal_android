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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Profile_Adapter extends RecyclerView.Adapter<Profile_Adapter.ViewHolder> {
    public ArrayList<Home_post_values> post_values;
    //    public ViewHolder.OnpostSelected onpostSelected ;
    public Profile_Adapter(ArrayList<Home_post_values> post_values){
        this.post_values = post_values ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Profile_Adapter.ViewHolder holder, int position) {
        Home_post_values values = post_values.get(position);
        holder.name.setText(values.getName());
        holder.gyear.setText(values.getYear());
        holder.exp.setText(values.getExp());
        holder.company_name.setText(values.getCompany_name());

        Log.i("value" , post_values.get(position).getUserid());

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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_picture ;
        TextView name ;
        TextView gyear ;
        EditText company_name ;
        EditText exp ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.ivprofile_image);
            name = itemView.findViewById(R.id.ivname);
            gyear = itemView.findViewById(R.id.ivgyear);
            company_name = itemView.findViewById(R.id.ivcompany_name);
            exp = itemView.findViewById(R.id.ivexp);
        }

    }
}
