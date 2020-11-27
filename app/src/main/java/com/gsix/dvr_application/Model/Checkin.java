package com.gsix.dvr_application.Model;

public class Checkin {
    public String name,totalcheckin;

    public Checkin(String name, String totalcheckin) {
        this.name = name;
        this.totalcheckin = totalcheckin;
    }

    public Checkin(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalcheckin() {
        return totalcheckin;
    }

    public void setTotalcheckin(String totalcheckin) {
        this.totalcheckin = totalcheckin;
    }
}

