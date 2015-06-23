package com.prashant.android.flipv2;

/**
 * Created by Prashant on 15-06-2015.
 */
public class Coupon {
    private String store,details,coupon,expiry;

    public String getStore() {
        return store.toUpperCase();
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
    public String toString(){
        return getStore()+"\n"+
                getDetails()+"\n"+
                "Coupon: "+getCoupon()+"\n"+
                "Expiry: "+getExpiry();


    }
}
