package com.example.phuong.healthy.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.eventBus.BusProvider;
import com.example.phuong.healthy.eventBus.MessageEvent;
import com.example.phuong.healthy.eventBus.MessageNetworkEvent;
import com.example.phuong.healthy.models.Hospital;
import com.example.phuong.healthy.utils.Constant;
import com.example.phuong.healthy.utils.TrackGPS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;

/**
 * Created by phuong on 09/01/2017.
 */
@EFragment(R.layout.fragment_hospital_detail)
public class HospitalDetailFragment extends BaseFragment implements OnMapReadyCallback {
    @FragmentArg
    public int idHospital;

    @ViewById(R.id.tvNameHospital)
    TextView mNameHospital;
    @ViewById(R.id.tvPhoneHospital)
    TextView mPhoneHospital;
    @ViewById(R.id.imgDirection)
    ImageView mImgDirection;

    double longitude;
    double latitude;
    double longitudeDis;
    double latitudeDis;
    private GoogleMap map;
    private Marker marker;
    private String mAddress;
    private LatLng myLatLng;
    private Hospital mHospital;
    private TrackGPS gps;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    @Click(R.id.imgDirection)
    void directionAction() {
        if (isGoogleMapsInstalled()) {
            String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + latitude + "," + longitude + "&daddr=" + latitudeDis + "," + longitudeDis;
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage(getActivity().getString(R.string.package_map));
            startActivity(intent);
        } else {
            String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + latitude + "," + longitude + "&daddr=" + latitudeDis + "," + longitudeDis;
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }
    }

    public boolean isGoogleMapsInstalled() {
        try {
            ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo(getActivity().getString(R.string.package_map), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.animateCamera(CameraUpdateFactory.zoomIn());
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {
            drawDirection();
        }
    }

    @Override
    void inits() {
        BusProvider.getInstance().register(getActivity());
        BusProvider.getInstance().register(getActivity());

        mSqlLiteDbHelper = new SqlLiteDbHelper(getActivity());
        mSqlLiteDbHelper.openDataBase();
        initData();
        SupportMapFragment mSupportMapFragment = SupportMapFragment.newInstance(null);
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.map1, mSupportMapFragment).commit();
        mSupportMapFragment.getMapAsync(this);

        //kiem tra da bat gps chưa
        gps = new TrackGPS(getActivity());
        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }

        if (Constant.isNetWork(getContext())) {
            LatLng latlng = getRoomLocation(mAddress);
            longitudeDis = latlng.longitude;
            latitudeDis = latlng.latitude;
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.message_turn_on_network), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void getMessage(MessageEvent message) {
        Log.d("tag11", " 1 789" + message.toString());
        if (message.isCheck()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
            Log.d("tag11", longitude + " 1 " + latitude);
        }
    }

    @Subscribe
    public void getMessage(MessageNetworkEvent message) {
        Log.d("tag11", " 1 78978 " + message.toString());
        if (message.isTurnOn()) {
            Log.d("tag111", " 1 2 3 ");
        }
    }

    public void initData() {
        //Hospital hospital = new Hospital();
        Hospital hospital = mSqlLiteDbHelper.getHospitalDetail(idHospital);
        mNameHospital.setText(hospital.getName());
        mPhoneHospital.setText(hospital.getPhone());
        mAddress = hospital.getAddress();
    }

    public void drawDirection() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {
            if (Constant.isNetWork(getContext())) {
                LatLng mLatlng = getRoomLocation(mAddress);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatlng, 13));
                CameraPosition mPosition = new CameraPosition.Builder()
                        .target(mLatlng).zoom(15).tilt(30).build();
                if (marker != null) {
                    marker.remove();
                }
                marker = map
                        .addMarker(new MarkerOptions()
                                .position(mLatlng)
                                .title(getResources().getString(R.string.message_you_are_here))
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(mPosition));
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.message_turn_on_network), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public LatLng getRoomLocation(String address) {
        if (address != null) {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(address, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            } else {
                Toast.makeText(getActivity().getApplication(), getResources().getString(R.string.message_not_found_address), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    drawDirection();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                }
                return;
            }
        }
    }
}
