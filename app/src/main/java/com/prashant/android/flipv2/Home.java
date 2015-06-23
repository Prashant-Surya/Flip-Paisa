package com.prashant.android.flipv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment{
    account acc;

    boolean execFinish=false;
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout2,container,false);
        //

        acc = new account();
        acc.execute();
        return v ;
    }

    public class account extends AsyncTask<String,Void,String>{
        HttpClient httpClient;
        HttpPost httpPost;
        HttpResponse httpResponse;
        String result;
        JSONObject json;
        TextView pending,bonus,cfp,paid,welcome;
        List<NameValuePair> data = new ArrayList<NameValuePair>(1);
        Context con = MainActivity.mainContext;
        @Override
        protected String doInBackground(String... params) {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://flippaisa.com/iprashant/send_rewards.php");
            data.add(new BasicNameValuePair("mail",userDetails.mail));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(data));
                httpResponse = httpClient.execute(httpPost);
                result = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(result);
                json = new JSONObject(result);
            } catch (Exception e) {
                System.out.println("At 68 net exception");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);

            try{
                System.out.println("At 91"+json);
                pending = (TextView) getView().findViewById(R.id.pending);
                bonus = (TextView) getView().findViewById(R.id.bonus);
                cfp = (TextView) getView().findViewById(R.id.confirmed);
                paid = (TextView) getView().findViewById(R.id.paid);
                welcome = (TextView) getView().findViewById(R.id.welcome);
                welcome.setText(con.getString(R.string.welcome)+" "+userDetails.name);
                if(MainActivity.preferences.getString("pfp","No").equalsIgnoreCase("No")){
                    System.out.println("pfp no");
                    pending.setText(con.getString(R.string.pending) + " " + json.getString("pfp"));
                    bonus.setText(con.getString(R.string.bonus) + " " + json.getString("bonusfp"));
                    cfp.setText(con.getString(R.string.confirmed) + " " + json.getString("cfp"));
                    paid.setText(con.getString(R.string.paid) + " " + json.getString("paid"));
                    MainActivity.editor.putString("pfp",json.getString("pfp"));
                    MainActivity.editor.putString("bonusfp",json.getString("bonusfp"));
                    MainActivity.editor.putString("cfp",json.getString("cfp"));
                    MainActivity.editor.putString("paid",json.getString("paid"));
                    MainActivity.editor.commit();
                }
                else{
                    System.out.println("pfp yes");
                    pending.setText(con.getString(R.string.pending) + " " + MainActivity.preferences.getString("pfp"," "));
                    bonus.setText(con.getString(R.string.bonus) + " " + MainActivity.preferences.getString("bonusfp"," "));
                    cfp.setText(con.getString(R.string.confirmed) + " " + MainActivity.preferences.getString("cfp"," "));
                    paid.setText(con.getString(R.string.paid) + " " + MainActivity.preferences.getString("paid"," "));
                }

                execFinish = true;
            }catch(NullPointerException ne) {
                System.out.println("Null pointer exception at home");
                execFinish = true;
            }catch(Exception e){
                System.out.println("Error at 1");
                execFinish = true;
            }
        }
    }

}