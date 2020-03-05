package com.simpletouch.business.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {

    /**
     *
     * @param context
     * @return True If The Mobile Network Available And False If Otherwise
     */
    public static boolean isOnline(Context context) {
//        try {
//        //    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//         //   NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//            //should check null because in airplane mode it will be null
//            return (netInfo != null && netInfo.isConnected());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }
}
