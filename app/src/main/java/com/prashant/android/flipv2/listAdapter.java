package com.prashant.android.flipv2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 26-05-2015.
 */
public class listAdapter extends ArrayAdapter<rowHolder>{
    Context context;
    ArrayList<rowHolder> posts;
    public listAdapter(Context context, int resource, ArrayList<rowHolder> objects) {
        super(context, resource, objects);
         this.context=context;
        this.posts=objects;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //RowItem rowItem = getItem(position);
        rowHolder rowItem = posts.get(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.store_primary, null);
            holder = new ViewHolder();
            /*holder.coup = (TextView) convertView.findViewById(R.id.listCoupons);
            holder.cash = (TextView) convertView.findViewById(R.id.listCashback);
            holder.img = (ImageView) convertView.findViewById(R.id.listImage);*/
            holder.store = (TextView) convertView.findViewById(R.id.storeName);
            holder.img = (ImageView)convertView.findViewById(R.id.storeSquare);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        /*holder.coup.setText(rowItem.getCoupons());
        holder.cash.setText(rowItem.getCashback());


        Picasso.with(context).load(rowItem.getImg()).placeholder(R.drawable.placeholder).into(holder.img);*/
        holder.store.setText(rowItem.getStore());
        holder.img.setImageBitmap(rowItem.getBitmap());
        return convertView;
    }
    private class ViewHolder{
        ImageView img;
        TextView store;
    }
}
