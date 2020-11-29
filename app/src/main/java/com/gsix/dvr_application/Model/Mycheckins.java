package com.gsix.dvr_application.Model;

public class Mycheckins {
 public String customerName;
 public String visitpurpose;
 public String timestamp;

    public Mycheckins(String customerName, String visitpurpose, String timestamp) {
        this.customerName = customerName;
       this.visitpurpose = visitpurpose;
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

    public String getvisitpurpose() {
        return visitpurpose;
    }

    public void setVisitpurpose(String visitpurpose) {
        visitpurpose = visitpurpose;
    }

    public Mycheckins()
    {

    }
}
