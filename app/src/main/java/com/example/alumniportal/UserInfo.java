package com.example.alumniportal;

public class UserInfo {
    public String full_name , current_company , current_position , graduation_year , leetcode ,
    codechef , codeforces , linkedin , profile ;

    UserInfo(){

    }
    UserInfo(String full_name){
        this.full_name = full_name ;
    }
    UserInfo(String full_name , String current_company , String current_position , String graduation_year , String leetcode , String codechef,
    String codeforces, String linkedin , String profile){
        this.codechef = codechef ;
        this.codeforces = codeforces ;
        this.leetcode = leetcode ;
        this.linkedin = linkedin ;
        this.full_name = full_name ;
        this.current_company = current_company ;
        this.current_position = current_position ;
    }
    String getFull_name(){return full_name;}
    String getCurrent_company(){return current_company;}
    String getCurrent_position(){return current_position;}
    String getGraduation_year(){return graduation_year;}
    String getProfile(){ return profile;}
    String getLeetcode(){return leetcode;}
    String getCodechef(){return codechef;}
    String getCodeforces(){return codeforces;}
    String getLinkedin(){return linkedin;}
}
