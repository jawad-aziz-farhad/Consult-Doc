package com.example.shanig.doctorapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ShaniG on 8/1/2017.
 */

public class Connection {

    private static Context context;

    public Connection(Context context){

        this.context = context;
    }

    public static boolean isNetwork(){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
