package com.gsix.dvr_application.Model;

public class Mycheckins {
 public String customerNmae;
 public String Visitpurpose;
 public String timestamp;

    public Mycheckins(String customerNmae, String visitpurpose, String timestamp) {
        this.customerNmae = customerNmae;
       this.Visitpurpose = visitpurpose;
        this.timestamp = timestamp;
    }

    public String getCustomerNmae() {
        return customerNmae;
    }

    public void setCustomerNmae(String customerNmae) {
        this.customerNmae = customerNmae;
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
