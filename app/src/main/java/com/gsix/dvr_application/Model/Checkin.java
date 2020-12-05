package com.gsix.dvr_application.Model;

public class Checkin {
    public String name,totalcheckin,empID;

    public Checkin(String name, String totalcheckin, String empID) {
        this.name = name;
        this.totalcheckin = totalcheckin;
        this.empID = empID;
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

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }
}

