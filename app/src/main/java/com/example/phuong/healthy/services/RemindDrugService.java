package com.example.phuong.healthy.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.eventBus.BusProvider;
import com.example.phuong.healthy.models.RemindDrug;
import com.example.phuong.healthy.receivers.RemindDrugReceiver;
import com.example.phuong.healthy.utils.Constant;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

/**
 * Created by phuong on 30/12/2016.
 */

public class RemindDrugService extends Service {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private RemindDrug mRemind;
    private SharedPreferences mSharedPreferences;
    private MediaPlayer mMedia;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMedia = MediaPlayer.create(getApplicationContext(), R.raw.ringtone);

        BusProvider.getInstance().register(this);

        mSharedPreferences = getSharedPreferences(Constant.NAME_SHAREPREFERENCES, 0);
        mRemind = new RemindDrug();
        mRemind.setHour(mSharedPreferences.getString(Constant.HOUR_SHAREPREFERENCES, ""));
        mRemind.setMin(mSharedPreferences.getString(Constant.MIN_SHAREPREFERENCES, ""));
        mRemind.setStatus(mSharedPreferences.getBoolean(Constant.STATUS_SHAREPREFERENCES, false));

    }

    @Subscribe
    public void getMessage(RemindDrug remind) {
        mRemind.setHour(remind.getHour());
        mRemind.setMin(remind.getMin());
        accessRemind(mRemind);
    }

    @Subscribe
    public void getMessage(Boolean isOffRingtone) {
        if (isOffRingtone) {
            controlRingTone(false);
        } else {
            controlRingTone(true);
        }
    }

    @Subscribe
    public void getMessage(String status) {
        if (status.equals("true")) {
            mRemind.setStatus(true);
        } else {
            mRemind.setStatus(false);
        }
        accessRemind(mRemind);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        accessRemind(mRemind);
        return START_STICKY;
    }

    public void accessRemind(RemindDrug remind) {
        //time hien tai
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int minsNow = minute + hour * 60;
        int minsAlarm = 0;
        if (("true").equals(String.valueOf(remind.isStatus()))) {
            minsAlarm = Integer.parseInt(remind.getMin()) + 60 * (Integer.parseInt(remind.getHour()));
            if (minsAlarm > minsNow) {
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(remind.getHour()));
                calendar.set(Calendar.MINUTE, Integer.parseInt(remind.getMin()));
                Intent myIntent = new Intent(RemindDrugService.this, RemindDrugReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
                alarmManager = (AlarmManager) this.getSystemService(getBaseContext().ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        }
    }

    public void controlRingTone(boolean isOn) {
        if (isOn) {
            mMedia.start();
        } else {
            mMedia.release();
        }
    }
}
