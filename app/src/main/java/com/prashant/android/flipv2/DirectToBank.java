package com.prashant.android.flipv2;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class DirectToBank extends ActionBarActivity {
    Spinner spinner;
    String mail,amount,number,method;
    toBankPage tbp;
    Button btn;
    EditText tamount,tnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_to_bank);
        spinner = (Spinner) findViewById(R.id.spinner);
        tamount = (EditText) findViewById(R.id.Amount);
        tnumber = (EditText) findViewById(R.id.phone);
        btn = (Button) findViewById(R.id.bankSubmit);
        SharedPreferences prefs = getSharedPreferences("MyApp",MODE_PRIVATE);
        mail = prefs.getString("mail","no");
        tbp = new toBankPage();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = tamount.getText().toString();
                number = tnumber.getText().toString();
                tbp = new toBankPage();
                tbp.execute(mail,amount,number,method);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                method = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //method = "Standard";
            }
        });
    }
    public class toBankPage extends AsyncTask<String,Void,String>{
        HttpClient httpClient;
        HttpResponse response;
        HttpGet httpGet;
        String resp;
        JSONObject json;
        @Override
        protected String doInBackground(String... params) {
            String m,a,n,met,url;
            m = params[0];
            a = params[1];
            n = params[2];
            met = params[3];
            url = "http://www.flippaisa.com/iprashant/tobank.php?mail="+m+"&amount="+a+"&number="+n+"&method="+met;
            System.out.println(url);
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
                            Toast.makeText(getApplicationContext(),"Requested forwared",Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.menu_direct_to_bank, menu);
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
