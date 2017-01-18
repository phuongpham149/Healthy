package com.example.phuong.healthy.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.phuong.healthy.eventBus.BusProvider;
import com.example.phuong.healthy.eventBus.MessageRemindHealthy;
import com.example.phuong.healthy.receivers.RemindHealthyReceiver;
import com.example.phuong.healthy.utils.Constant;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

/**
 * Created by phuong on 30/12/2016.
 */

public class RemindHealthyService extends Service {
    private boolean mState;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BusProvider.getInstance().register(this);

        mSharedPreferences = getSharedPreferences(Constant.NAME_SHAREPREFERENCES, 0);
        mState = mSharedPreferences.getBoolean(Constant.STATE_REMIND_HEALTHY, false);

    }

    @Subscribe
    public void getMessage(MessageRemindHealthy message) {
        if (message.isState()) {
            mState = true;
            accessRemind();
        } else {
            mState = false;
            accessRemind();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        accessRemind();
        return START_STICKY;
    }

    public void accessRemind() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int minsNow = minute + hour * 60;
        int minsAlarm = 0;
        if (mState) {
            minsAlarm = 60 * 10 + 14;
            if (minsAlarm > minsNow) {
                calendar.set(Calendar.HOUR_OF_DAY, 10);
                calendar.set(Calendar.MINUTE, 14);
                Intent myIntent = new Intent(RemindHealthyService.this, RemindHealthyReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
                alarmManager = (AlarmManager) this.getSystemService(getBaseContext().ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }
}


