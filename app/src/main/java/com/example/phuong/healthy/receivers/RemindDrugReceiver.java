package com.example.phuong.healthy.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.activities.NotifycationActivity_;
import com.example.phuong.healthy.eventBus.BusProvider;

/**
 * Created by phuong on 12/01/2017.
 */

public class RemindDrugReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_hospital)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getString(R.string.content_notification_remind_drug))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)//co the cancel
                .setOngoing(false);//khong the keo qua de tat
        builder.setVibrate(new long[]{1000, 1000});
        Intent notifyIntent = new Intent(context, NotifycationActivity_.class);
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());

        //bat nhac
        BusProvider.getInstance().register(context);
        BusProvider.getInstance().post(false);
    }
}
