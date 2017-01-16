package com.example.phuong.healthy.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by phuong on 11/01/2017.
 */

public class Constant {
    public static final int TYPE_PROVICE = 1;
    public static final int TYPE_HOSPITAL = 2;
    public static final int TYPE_DRUG = 3;
    public static final String NAME_SHAREPREFERENCES = "remind";
    public static final String HOUR_SHAREPREFERENCES = "hour";
    public static final String MIN_SHAREPREFERENCES = "min";
    public static final String STATUS_SHAREPREFERENCES = "status";
    public static final String STATE_REMIND_HEALTHY = "remind_healthy";
    public static final Integer TURN_ON_GPS = 1;
    private static double mLongitude;
    private static double mLatitude;

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String getLocationAddress(Context context) {
        TrackGPS gps = new TrackGPS(context);
        if (gps.canGetLocation()) {
            mLongitude = gps.getLongitude();
            mLatitude = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String city = "";
        if(addresses!=null){
            city = addresses.get(0).getAddressLine(3);
        }
        return city;
    }
}
