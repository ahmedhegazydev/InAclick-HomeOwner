package com.example.hp.in_a_click.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hp.in_a_click.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by hp on 11/7/2017.
 */

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {


    View myView;
    Context context = null;

    public CustomInfoWindow(View myView, Context context) {
        this.myView = myView;
        this.context = context;
        this.myView = LayoutInflater.from(context).inflate(R.layout.about_driver, null);

    }

    public CustomInfoWindow(View myView) {
        this.myView = myView;
    }

    @Override
    public View getInfoWindow(Marker marker) {


        //Here we can access myView view


        return myView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
