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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.ViewHolder> {
    public ArrayList<Home_post_values> post_values;
//    public ViewHolder.OnpostSelected onpostSelected ;
    public Home_Adapter(ArrayList<Home_post_values> post_values){
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
    public void onBindViewHolder(@NonNull  Home_Adapter.ViewHolder holder, int position) {
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
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(firebaseAuth.getUid().equals(post_values.get(position).getUserid())){
                   Intent i = new Intent(v.getContext() , Profile_Page.class);
                   v.getContext().startActivity(i);
               }
               else {
                   Intent i = new Intent(v.getContext(), Profile_visit.class);
                   i.putExtra("Userid", post_values.get(position).getUserid());
                   v.getContext().startActivity(i);
               }
            }
        });
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
//        OnpostSelected onpostSelected ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.ivprofile_image);
            name = itemView.findViewById(R.id.ivname);
            gyear = itemView.findViewById(R.id.ivgyear);
            company_name = itemView.findViewById(R.id.ivcompany_name);
            exp = itemView.findViewById(R.id.ivexp);
//            this.onpostSelected = onpostSelected ;
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//           onpostSelected.OnpostItemSelected(getAdapterPosition());
//        }
//
//        public interface OnpostSelected{
//            void OnpostItemSelected(int position);
//        }
    }
}
