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
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MobileRecharge extends ActionBarActivity {
    String number,operator,amount,type,mail;
    EditText num,op,amo;
    Spinner sp;
    Button btn;
    toRecharge tr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);
        sp = (Spinner) findViewById(R.id.type);
        num = (EditText) findViewById(R.id.phn);
        op = (EditText) findViewById(R.id.operator);
        amo = (EditText) findViewById(R.id.amount);
        btn = (Button) findViewById(R.id.recharge);
        tr = new toRecharge();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = parent.getItemAtPosition(0).toString();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("MyApp",MODE_PRIVATE);
                mail = prefs.getString("mail","no");
                number = num.getText().toString();
                operator = op.getText().toString();
                amount = amo.getText().toString();
                tr.execute(mail,amount,number,type,operator);
            }
        });

    }
    public class toRecharge extends AsyncTask<String,Void,String> {
        HttpClient httpClient;
        HttpResponse response;
        HttpGet httpGet;
        String resp;
        JSONObject json;
        @Override
        protected String doInBackground(String... params) {
            String m,a,n,met,url,op;
            m = params[0];
            a = params[1];
            n = params[2];
            met = params[3];
            op = params[4];
            url = "http://www.flippaisa.com/iprashant/torecharge.php?mail="+m+"&amount="+a+"&number="+n+"&type="+met+"&operator="+op;
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
        getMenuInflater().inflate(R.menu.menu_mobile_recharge, menu);
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
