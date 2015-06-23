package com.prashant.android.flipv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

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


public class Store_secondary extends ActionBarActivity {
    ImageView img;
    String store,sp1,sp2,sp3,sp4;
    TableLayout table;
    ArrayList<String> rows;
    ListView flist,olist;
    ArrayAdapter<String> adapter;
    TextView f1,f2,f3,o1;
    getCashBack gb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp1="%   ";
        sp2= "%         ";
        sp3="%   ";
        sp4="%      ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_secondary);
        flist = (ListView) findViewById(R.id.flipList);
        olist = (ListView) findViewById(R.id.others);
        f1 = (TextView) findViewById(R.id.mob);
        f2 = (TextView) findViewById(R.id.web);
        f3 = (TextView) findViewById(R.id.mold);
        o1 = (TextView) findViewById(R.id.textView2);

        //table = (TableLayout) findViewById(R.id.flipTable);
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        store = intent.getStringExtra("store");
        byte[] decoded = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodeBit = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        img = (ImageView) findViewById(R.id.storeLarge);
        img.setImageBitmap(decodeBit);
        gb = new getCashBack();
        gb.execute(store);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_secondary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void gotostore(View V){
        String url = "https://www.flippaisa.com/store/manager/?m=APP&sid="+store;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
    public void settingViews(){
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.fliplist_item,rows);
        if(store.equalsIgnoreCase("flipkart")) {
            o1.setVisibility(o1.GONE);
            olist.setVisibility(olist.GONE);
            f1.setVisibility(f1.VISIBLE);
            f2.setVisibility(f2.VISIBLE);
            f3.setVisibility(f3.VISIBLE);
            flist.setVisibility(flist.VISIBLE);
            flist.setAdapter(adapter);
        }else{
            o1.setVisibility(o1.VISIBLE);
            olist.setVisibility(olist.VISIBLE);
            f1.setVisibility(f1.GONE);
            f2.setVisibility(f2.GONE);
            f3.setVisibility(f3.GONE);
            flist.setVisibility(flist.GONE);
            olist.setAdapter(adapter);
        }
    }
    public class getCashBack extends AsyncTask<String,Void,String>{
        HttpClient httpClient;
        HttpGet httpGet;
        HttpResponse httpResponse;
        String res;
        JSONObject json,jsonResponse;
        JSONArray jarray;
        @Override
        protected String doInBackground(String... params) {
            String store = params[0];
            String url = "http://flippaisa.com/iprashant/getcashback.php?store="+store;
            String temp="";
            httpClient = new DefaultHttpClient();
            httpGet = new HttpGet(url);
            try {
                httpResponse = httpClient.execute(httpGet);
                res = EntityUtils.toString(httpResponse.getEntity());
                jsonResponse = new JSONObject(res);
                jarray = jsonResponse.getJSONArray(store);
                if(store.equalsIgnoreCase("flipkart")){
                    rows = new ArrayList<String>();

                    for(int i=0;i<jarray.length();i++){
                        temp="    ";
                        json = jarray.getJSONObject(i);
                        temp+=json.getString("mo")+sp1;
                        temp+=json.getString("mn")+sp2;
                        temp+=json.getString("wo")+sp3;
                        temp+=json.getString("wn")+sp4;
                        temp+=json.getString("details");
                        rows.add(temp);
                    }

                }
                else{

                    rows = new ArrayList<String>();
                    for(int i=0;i<jarray.length();i++){
                        temp="     ";
                        json = jarray.getJSONObject(i);
                        temp += json.getString("c")+"%            ";
                        temp += json.getString("details");
                        rows.add(temp);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            settingViews();
        }
    }
}
