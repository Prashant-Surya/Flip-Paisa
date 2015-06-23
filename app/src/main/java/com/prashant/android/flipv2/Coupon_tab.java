package com.prashant.android.flipv2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Prashant on 16-06-2015.
 */
public class Coupon_tab extends Fragment {
    Coupon c;
    getCoupons gp;
    ArrayList<Coupon> cps;
    JSONObject resp,json;
    JSONArray array;
    String response;
    ListView ls;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.coupons,container,false);
        ls = (ListView) v.findViewById(R.id.couponsList);
        System.out.println("AT 40 "+R.id.couponsList);
        c = new Coupon();
        gp = new getCoupons();
        response = "";
        gp.execute();
    /*    response = gp.getRes();
        System.out.println("At 45 "+ response);
            try {
                resp = new JSONObject(response);
                array = resp.getJSONArray("coupons");
                for(int i=0;i<array.length();i++){
                    c = new Coupon();
                    json = array.getJSONObject(i);
                    c.setStore(json.getString("store"));
                    c.setCoupon(json.getString("coupon"));
                    c.setDetails(json.getString("details"));
                    c.setExpiry(json.getString("expiry"));
                    System.out.println("At 53,getCoupons " + json.getString("expiry"));
                    cps.add(c);
                }
                if(cps.isEmpty()){
                    System.out.println("Refresh");
                }
                else{
                    ArrayAdapter<Coupon> adapter = new ArrayAdapter<Coupon>(MainActivity.mainContext,R.layout.coupon_list_item,cps);
                    ls.setVisibility(ls.VISIBLE);
                    ls.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
        return v;
    }
    public class getCoupons extends AsyncTask<String,Void,String>{
        HttpClient httpClient;
        HttpGet httpGet;
        HttpResponse httpResponse;
        String res;

        public String getRes() {
            return res;
        }

        @Override
        protected String doInBackground(String... params) {
            res = "";
            System.out.println("AT 87 "+R.id.couponsList);
            httpClient = new DefaultHttpClient();
            httpGet = new HttpGet("http://flippaisa.com/iprashant/getcoupons.php");
            try {
                httpResponse = httpClient.execute(httpGet);
                res = EntityUtils.toString(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            cps = new ArrayList<Coupon>();
            System.out.println("AT 101 "+R.id.couponsList);
            try {
                resp = new JSONObject(res);
                array = resp.getJSONArray("coupons");
                for(int i=0;i<array.length();i++){
                    c = new Coupon();
                    json = array.getJSONObject(i);
                    c.setStore(json.getString("store"));
                    c.setCoupon(json.getString("coupon"));
                    c.setDetails(json.getString("details"));
                    c.setExpiry(json.getString("expiry"));
                    System.out.println("At 53,getCoupons " + json.getString("expiry"));
                    cps.add(c);
                }
                if(cps.isEmpty()){
                    System.out.println("Refresh");
                }
                else{
                    ArrayAdapter<Coupon> adapter = new ArrayAdapter<Coupon>(MainActivity.mainContext,R.layout.coupon_list_item,cps);
                    ls.setVisibility(ls.VISIBLE);
                    ls.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
