package com.simpletouch.business.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



/***
 * BroadcastReceiver TO Listen To Network Status Changes
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private static final String NETWORK_RECEIVER = "NETWORK_RECEIVER";
    private NetworkListener networkListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isOnline(context)) {
            Log.d(NETWORK_RECEIVER, "Online");
            // Notify Activity With The Current Status
            networkListener.isNetworkAvailable(true);
        } else {
            Log.d(NETWORK_RECEIVER, "Offline");
            // Notify Activity With The Current Status
            networkListener.isNetworkAvailable(false);
        }
    }

    public void setNetworkListener(NetworkListener networkListener) {
        this.networkListener = networkListener;
    }

}
