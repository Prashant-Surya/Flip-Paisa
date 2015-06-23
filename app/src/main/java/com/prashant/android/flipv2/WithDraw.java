package com.prashant.android.flipv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class WithDraw extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_draw, menu);
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
    public void diverting(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.toBank: intent = new Intent(this,DirectToBank.class);
                startActivity(intent);
                break;
            case R.id.toRecharge: intent = new Intent(this, MobileRecharge.class);
                startActivity(intent); break;
            case R.id.flipVoucher: intent = new Intent(this,FlipVoucher.class);
                startActivity(intent); break;
            default:

        }
    }

}
