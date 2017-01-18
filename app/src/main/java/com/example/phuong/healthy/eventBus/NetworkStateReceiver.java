package com.example.phuong.healthy.eventBus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by phuong on 18/01/2017.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        Log.d("tag11", "Mobile connected: " + isMobileConn);
        if (isMobileConn) {
            Log.d("tag11", "Mobile connected: ");
            BusProvider.getInstance().register(context);
            MessageNetworkEvent messageNetworkEvent = new MessageNetworkEvent(true);
            BusProvider.getInstance().post(messageNetworkEvent);
        }
    }


}
