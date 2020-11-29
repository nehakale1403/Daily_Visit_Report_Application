package com.gsix.dvr_application.Model;

public class Mycheckins {
 public String customerName;
 public String visitpurpose;
 public String customerType;
 public String timestamp;

    public Mycheckins(String customerName, String visitpurpose, String timestamp,String customerType) {
        this.customerName = customerName;
       this.visitpurpose = visitpurpose;
        this.timestamp = timestamp;
        this.customerType=customerType;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp)

    {
        this.timestamp = timestamp;
    }

    public String getvisitpurpose() { return visitpurpose; }

    public void setVisitpurpose(String visitpurpose) { visitpurpose = visitpurpose; }
    public void setvisitpurpose(String visitpurpose) {
        visitpurpose = visitpurpose;
    }

    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String customerType) { customerType = customerType; }

    public Mycheckins()
    {

    }
}
