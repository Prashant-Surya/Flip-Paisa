package com.prashant.android.flipv2;

/**
 * Created by Prashant on 20-06-2015.
 */
public class App {
    String appid,cashback,details;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCashback() {
        return cashback;
    }

    public void setCashback(String cashback) {
        this.cashback = cashback;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Appid: "+appid+"\n"+
                "Cashback: "+cashback+"\n"+
                "Details: "+details;
    }
}
