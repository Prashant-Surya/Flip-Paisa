package com.prashant.android.flipv2;

/**
 * Created by Prashant on 24-05-2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;
    public ViewPagerAdapter(FragmentManager fm,CharSequence titles[],int num) {
        super(fm);
        this.Titles = titles;
        this.NumbOfTabs = num;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Home home = new Home();
            return home;
        }
        else if(position == 1)       // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Store store = new Store();
            return store;
        }
        else if(position == 2){
            Coupon_tab cp = new Coupon_tab();
            return cp;

        }
        else {
            apps_tab ap = new apps_tab();
            return ap;
        }
    }
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

}