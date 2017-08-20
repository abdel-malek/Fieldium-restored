package com.bluebrand.fieldium.view.Fields;

/**
 * Created by Player on 6/21/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.bluebrand.fieldium.core.model.Address;
import com.bluebrand.fieldium.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Address address ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setLanguage();
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        address = (Address) getIntent().getExtras().getSerializable("address") ;
        TextView textView_address = (TextView) findViewById(R.id.textView_address) ;
        textView_address.setText(address.getName());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {


            // Add a marker in Sydney, Australia, and move the camera.
            LatLng marker = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(marker).title(getIntent().getExtras().getString("label")));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    marker, 15);
            mMap.moveCamera(cameraUpdate);
        }catch (Exception e){}
//        map.animateCamera(cameraUpdate);
    }
    protected void setLanguage() {
        SharedPreferences lang = this.getSharedPreferences("lang", Context.MODE_PRIVATE);
        Locale locale;
        String localLang = lang.getString("value", "");
        if (localLang.equals("en")) {
            locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);/*locale = locale;*/
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        } else if (localLang.equals("ar")) {
            locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);/*locale = locale;*/
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
    }
}