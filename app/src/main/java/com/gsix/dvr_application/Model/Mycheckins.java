package com.gsix.dvr_application.Model;

public class Mycheckins {
 public String customerName, visitPurpose;

    public Mycheckins(String customerName, String visitPurpose) {
        this.customerName = customerName;
        this.visitPurpose = visitPurpose;
    }

    public Mycheckins(){}

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
}
