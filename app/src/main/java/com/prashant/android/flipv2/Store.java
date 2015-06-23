package com.prashant.android.flipv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class Store extends Fragment{
    ArrayList<rowHolder> rh,rnew;
    ListView list;
    rowHolder r;
    storePopulate sp;
    JSONObject js;
    JSONObject jsonResp;
    Spinner spinner;
    @Override
    public void onResume() {
     //   super.onResume();
//        sp.onPostExecute("Test");
        listAdapter ls = new listAdapter(getActivity().getApplicationContext(),R.layout.store_primary,rh);
        list.setAdapter(ls);
        super.onResume();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.store,container,false);
        list = (ListView) v.findViewById(R.id.listView);
        r = new rowHolder();
        rh = new ArrayList<rowHolder>();
        rnew = new ArrayList<rowHolder>();


        //Spinner start
        spinner = (Spinner) v.findViewById(R.id.catSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.mainContext,
                R.array.categories, R.layout.spinner_custom);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        sp = new storePopulate();
        sp.execute("");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rnew = new ArrayList<rowHolder>();
                String categ = (String) parent.getItemAtPosition(position);
                if(!categ.equalsIgnoreCase("All")){
                    //rowHolder temp = new rowHolder();
                    for(rowHolder temp: rh){
                        String catFrom = temp.getCategory();
                        if(catFrom.contains(categ.toLowerCase())){
                            rnew.add(temp);
                        }
                    }
                    listAdapter ls = new listAdapter(getActivity().getApplicationContext(),R.layout.store_primary,rnew);
                    list.setAdapter(ls);
                }
                else{
                    listAdapter ls = new listAdapter(getActivity().getApplicationContext(),R.layout.store_primary,rh);
                    list.setAdapter(ls);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storePopulate ssp = new storePopulate();
                ssp.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        //Spinner end
      /*  ArrayList<rowHolder> rh = new ArrayList<rowHolder>();
        rowHolder r1,r2;
        r1 = new rowHolder("Coupons 10","Cashback 20","http://flippaisa.com/static/images/merchants/flipkart.jpg","http://www.flippaisa.com/store/flipkart/");
        r2 = new rowHolder("Coupons 100","Cashback 200","http://flippaisa.com/static/images/merchants/snapdeal.jpg","http://www.flippaisa.com/store/flipkart/");
        rh.add(r1);
        rh.add(r2);
        temp = rh;
        listAdapter ls = new listAdapter(getActivity().getApplicationContext(),R.layout.list_item,rh);
        list.setAdapter(ls);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowHolder temp = (rowHolder) parent.getItemAtPosition(position);
                System.out.println("Test Store" + position + temp.getCashback() + " " + temp.getImg());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(temp.getUrl()));
                startActivity(browserIntent);
            }
        });*/

        return v;
    }
    /*
        For decoding from base 64 image
         byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
     */
    public class storePopulate extends AsyncTask<String,Void,String>{
        HttpClient httpClient;
        HttpGet httpGet;
        HttpResponse httpResponse;
        ArrayList<rowHolder> rows;
        JSONObject json;
        JSONArray array;
        String res;
        String bitmapTemp;
        @Override
        protected String doInBackground(String... params) {
            System.out.println("Entered processing at 94,store");
            String cat = params[0];
            httpClient = new DefaultHttpClient();
            String url = "http://flippaisa.com/iprashant/stores.php?cat="+cat;
            httpGet = new HttpGet(url);
            try {
                httpResponse = httpClient.execute(httpGet);
                res = EntityUtils.toString(httpResponse.getEntity());
                System.out.println("At 99 "+res);
                jsonResp = new JSONObject(res);
                js = jsonResp;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();

            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            try {
                array = jsonResp.getJSONArray("stores");
                System.out.println(array.length()+"At 114");
                for(int i=0;i<array.length();i++){
                    json = array.getJSONObject(i);
                    r = new rowHolder();
                    r.setStore(json.getString("store"));
                    r.setLargeBit(json.getString("large"));
                    r.setCategory(json.getString("cat"));
                    bitmapTemp = json.getString("img");
                    byte[] decoded = Base64.decode(bitmapTemp,Base64.DEFAULT);
                    Bitmap decodeBit = BitmapFactory.decodeByteArray(decoded,0,decoded.length);
                    r.setBitmap(decodeBit);
                    rh.add(r);

                }
                listAdapter ls = new listAdapter(getActivity().getApplicationContext(),R.layout.store_primary,rh);
                list.setAdapter(ls);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        rowHolder rtemp = (rowHolder) parent.getItemAtPosition(position);
                        Intent intent = new Intent(MainActivity.mainContext,Store_secondary.class);
                        intent.putExtra("store",rtemp.getStore());
                        intent.putExtra("image",rtemp.getLargeBit());
                        startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch(Exception e)
            {

            }
        }
    }
}