package com.example.alumniportal;

public class My_booking {
    String name_user,date,userid,time,app;


    public My_booking(String name_user, String date, String userid,String time, String app) {
        this.name_user = name_user;
        this.date = date;
        this.userid = userid;
        this.app = app;
        this.time = time ;
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

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getDnt() {
        return date;
    }

    public void setDnt(String dnt) {
        this.date = dnt;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
