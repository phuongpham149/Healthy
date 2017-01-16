package com.example.phuong.healthy.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.activities.HomeActivity_;
import com.example.phuong.healthy.activities.MainActivity_;
import com.example.phuong.healthy.activities.NotifycationActivity_;
import com.example.phuong.healthy.utils.Constant;
import com.example.phuong.healthy.utils.TrackGPS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;

/**
 * Created by phuong on 16/01/2017.
 */

public class RemindHealthyReceiver extends BroadcastReceiver {
    private String mProvince;
    private String[] mMessage;
    private String mMessageWeather;
    private double mLongitude;
    private double mLatitude;
    private double mTemp;
    private Context mContext;
    private StringBuilder mUrl = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?q=");

    @Override
    public void onReceive(Context context, Intent intent) {
        mProvince = Constant.getLocationAddress(context);
        mContext = context;
        mMessage = context.getResources().getStringArray(R.array.message_remind_healthy);
        getMessageRemind(mProvince,mMessage);

    }

    public String getMessageRemind(String location,String[] messages) {
        String[] nameLocation = location.split(" ");
        for (int i = 0; i < nameLocation.length; i++) {
            mUrl.append(nameLocation[i]);
        }
        mUrl.append("&appid=2259e31b3d4a20d6616691a559e39eb3");
        new Weather().execute(mUrl.toString());
        String messageWeather = "";


        return messageWeather;
    }

    class Weather extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            BufferedReader inputStream = null;
            URL jsonUrl = null;
            try {
                jsonUrl = new URL(strings[0]);
                URLConnection dc = jsonUrl.openConnection();

                dc.setConnectTimeout(5000);
                dc.setReadTimeout(5000);

                inputStream = new BufferedReader(new InputStreamReader(
                        dc.getInputStream()));
                String jsonResult = inputStream.readLine();
                return jsonResult;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                String resultWeather = jsonObjectMain.getString("temp");
                mTemp = Float.parseFloat(resultWeather)-273.15;
                if(mTemp<21){
                    mMessageWeather = mMessage[0];
                }
                if(mTemp<26 && mTemp>21){
                    mMessageWeather = mMessage[1];
                }
                if(mTemp>26){
                    mMessageWeather = mMessage[2];
                }
                pushNotification(mContext,mMessageWeather);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void pushNotification(Context context,String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_hospital)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)//co the cancel
                .setOngoing(false);//khong the keo qua de tat
        builder.setVibrate(new long[]{1000, 1000});
        Intent notifyIntent = new Intent(context, HomeActivity_.class);
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
    }
}
