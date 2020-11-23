package com.gsix.dvr_application.Model;

public class Expense {

    public Expense(String title, String description, String image, String timestamp, String userid){

        this.title = title;
        this.description = description;
        this.image = image;
        this.timestamp = timestamp;
        this.userid = userid;
    }


    public String title;
    public String description;
    public String image;
    public String timestamp;
    public String userid;

    public Expense(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
