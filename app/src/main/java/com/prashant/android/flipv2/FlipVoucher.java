package com.prashant.android.flipv2;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class FlipVoucher extends ActionBarActivity {
    String mail,amount;
    EditText amo;
    Button btn;
    toFlip tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_voucher);
        amo = (EditText) findViewById(R.id.amount);
        btn = (Button) findViewById(R.id.flipSubmit);
        tp = new toFlip();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amo.getText().toString();
                SharedPreferences prefs = getSharedPreferences("MyApp",MODE_PRIVATE);
                mail = prefs.getString("mail","no");
                tp.execute(mail,amount);
            }
        });
    }
    public class toFlip extends AsyncTask<String,Void,String>{
        String m,a,url;
        HttpClient httpClient;
        HttpResponse response;
        HttpGet httpGet;
        String resp;
        JSONObject json;
        @Override
        protected String doInBackground(String... params) {
            m = params[0];
            a = params[1];
            url = "http://www.flippaisa.com/iprashant/toflip.php?mail="+m+"&amount="+a;
            httpClient = new DefaultHttpClient();
            httpGet = new HttpGet(url);
            try {
                response = httpClient.execute(httpGet);
                resp = EntityUtils.toString(response.getEntity());
                json = new JSONObject(resp);
                if(json.getString("success").equalsIgnoreCase("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Requested forwared", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Error Occured try again later",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flip_voucher, menu);
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
}
