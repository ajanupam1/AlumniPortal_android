package com.example.alumniportal;

public class Profile_values {
    String full_name,graduation_year,current_company,current_position,leetcode,codechef,codeforces,linkedin,profile ;

    public Profile_values(String full_name, String graduation_year, String current_company, String current_position, String leetcode, String codechef, String codeforces, String linkedin, String profile) {
        this.full_name = full_name;
        this.graduation_year = graduation_year;
        this.current_company = current_company;
        this.current_position = current_position;
        this.leetcode = leetcode;
        this.codechef = codechef;
        this.codeforces = codeforces;
        this.linkedin = linkedin;
        this.profile = profile;
    }

    public Profile_values() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(String graduation_year) {
        this.graduation_year = graduation_year;
    }

    public String getCurrent_company() {
        return current_company;
    }

    public void setCurrent_company(String current_company) {
        this.current_company = current_company;
    }

    public String getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(String current_position) {
        this.current_position = current_position;
    }

    public String getLeetcode() {
        return leetcode;
    }

    public void setLeetcode(String leetcode) {
        this.leetcode = leetcode;
    }

    public String getCodechef() {
        return codechef;
    }

    public void setCodechef(String codechef) {
        this.codechef = codechef;
    }

    public String getCodeforces() {
        return codeforces;
    }

    public void setCodeforces(String codeforces) {
        this.codeforces = codeforces;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
