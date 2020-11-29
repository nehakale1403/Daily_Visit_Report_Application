package com.gsix.dvr_application.Model;

public class Mycheckins {
 public String customerName;
 public String Visitpurpose;
 public String timestamp;

    public Mycheckins(String customerName, String visitpurpose, String timestamp) {
        this.customerName = customerName;
       this.Visitpurpose = visitpurpose;
        this.timestamp = timestamp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerNmae) {
        this.customerName = customerName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp)

    {
        this.timestamp = timestamp;
    }

    public String getVisitpurpose() {
        return Visitpurpose;
    }

    public void setVisitpurpose(String visitpurpose) {
        Visitpurpose = visitpurpose;
    }

    public Mycheckins()
    {

    }
}
