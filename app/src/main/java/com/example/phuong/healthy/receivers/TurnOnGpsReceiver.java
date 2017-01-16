package com.example.phuong.healthy.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.eventBus.BusProvider;
import com.example.phuong.healthy.utils.Constant;

/**
 * Created by phuong on 12/01/2017.
 */

public class TurnOnGpsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(context.getString(R.string.turn_on_gps))){
            Log.d("tag112","broadcast nhan gps");
            BusProvider.getInstance().register(context);
            BusProvider.getInstance().post(Constant.TURN_ON_GPS);
        }
    }
}
