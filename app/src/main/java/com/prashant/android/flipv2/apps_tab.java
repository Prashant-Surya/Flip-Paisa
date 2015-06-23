package com.prashant.android.flipv2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
public class apps_tab extends Fragment {
    ListView appsList;
    ArrayList<App> apps;
    JSONObject resp,json;
    JSONArray array;
    String response;
    getApps gap;
    App app;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.apps,container,false);
        appsList = (ListView) v.findViewById(R.id.appsList);
        app = new App();
        gap = new getApps();
        gap.execute();
        return v;
    }
    public class getApps extends AsyncTask<String,Void,String>{
        HttpClient httpClient;
        HttpGet httpGet;
        HttpResponse httpResponse;
        String res;
        @Override
        protected String doInBackground(String... params) {
            res = "";
            System.out.println("AT 87 "+R.id.couponsList);
            httpClient = new DefaultHttpClient();
            httpGet = new HttpGet("http://flippaisa.com/iprashant/getapps.php");
            try {
                httpResponse = httpClient.execute(httpGet);
                res = EntityUtils.toString(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            apps = new ArrayList<App>();
            //System.out.println("AT 101 "+R.id.couponsList);
            try {
                resp = new JSONObject(res);
                array = resp.getJSONArray("apps");
                for(int i=0;i<array.length();i++){
                    app = new App();
                    json = array.getJSONObject(i);
                    app.setAppid(json.getString("appid"));
                    app.setDetails(json.getString("details"));
                    app.setCashback(json.getString("cashback"));
                    apps.add(app);
                }
                if(apps.isEmpty()){
                    System.out.println("Refresh");
                }
                else{
                    ArrayAdapter<App> adapter = new ArrayAdapter<App>(MainActivity.mainContext,R.layout.coupon_list_item,apps);
                    appsList.setVisibility(appsList.VISIBLE);
                    appsList.setAdapter(adapter);
                    appsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String url = "https://www.flippaisa.com/apps/manager/?s=FP&appid=";
                            App temp = (App) parent.getItemAtPosition(position);
                            url+=temp.getAppid();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
