package com.example.alumniportal;

import com.google.firebase.database.DatabaseReference;

public class Home_post_values {
    private String profile_pic , name , year , company_name , exp , userid ;
    DatabaseReference databaseReference ;
public Home_post_values(){

}
    public Home_post_values(String profile_pic, String name, String year, String company_name, String exp, String userid, DatabaseReference databaseReference) {
        this.profile_pic = profile_pic;
        this.name = name;
        this.year = year;
        this.company_name = company_name;
        this.exp = exp;
        this.userid = userid;
        this.databaseReference = databaseReference;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
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
}
