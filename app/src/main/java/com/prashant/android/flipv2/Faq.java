package com.prashant.android.flipv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Faq extends Fragment{
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.faq,container,false);
        /*//TextView text = (TextView) v.findViewById(R.id.text);
        String value = MainActivity.mainContext.getString(R.string.fb);
        text.setText(Html.fromHtml(value));
        text.setMovementMethod(LinkMovementMethod.getInstance());*/
        return v;
    }
}