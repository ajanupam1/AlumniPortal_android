package com.example.alumniportal;

import com.google.firebase.database.DatabaseReference;

public class Appointment_values {
    String date,time,name,url,userid,admin ;
    DatabaseReference databaseReference ;

    public Appointment_values(String date, String time,String name,String url,DatabaseReference databaseReference,String userid) {
        this.url = url ;
        this.name = name;
        this.date = date;
        this.time = time;
        this.databaseReference = databaseReference ;
        this.userid = userid ;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
