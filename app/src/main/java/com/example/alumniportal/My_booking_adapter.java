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
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.text.DateFormat;
        import java.util.ArrayList;
        import java.util.Date;

public class My_booking_adapter extends RecyclerView.Adapter<My_booking_adapter.ViewHolder> {
    public ArrayList<My_booking> post_values;
    public My_booking_adapter(ArrayList<My_booking> post_values){
        this.post_values = post_values ;
    }


    @NonNull
    @Override
    public My_booking_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.confirmed_appointments,parent,false);
        return new My_booking_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  My_booking_adapter.ViewHolder holder, int position) {
        My_booking values = post_values.get(position);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(values.getName_user());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Profile_values profile_values = snapshot.getValue(Profile_values.class);
                holder.name.setText(profile_values.getFull_name());
                //profilepic
                DownloadTask downloadTask = new DownloadTask();
                try{
                    Bitmap profilepic = downloadTask.execute(profile_values.getProfile()).get();
                    holder.profile_picture.setImageBitmap(profilepic);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        holder.dateandtime.setText(values.getDate()+","+values.getTime());
        if(values.getApp().equals("true"))holder.app.setVisibility(View.VISIBLE);
        else if(values.getApp().equals("false"))holder.rej.setVisibility(View.VISIBLE);

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
