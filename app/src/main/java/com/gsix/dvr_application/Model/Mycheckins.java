package com.gsix.dvr_application.Model;

public class Mycheckins {
 public String customerName;
 public String visitPurpose;
 public String timestamp;

    public Mycheckins(String customerName, String visitPurpose, String timestamp) {
        this.customerName = customerName;
       this.visitPurpose = visitPurpose;
        this.timestamp = timestamp;
    }

    public Mycheckins()
    {

    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
