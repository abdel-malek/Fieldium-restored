package com.bluebrand.fieldium.core.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.bluebrand.network.SuccessCallback;

/**
 * Created by malek on 5/12/16.
 */
public class Utils {

  /*  public static Typeface getFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "Zahra.Arabic.otf");

    }

    public static ArrayList getPeriods(String open, String close, int duration) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        ArrayList<String> times = new ArrayList<>();
        try {

            Date open_time = formatter.parse(open);
            Date close_time = formatter.parse(close);
            Calendar cal = Calendar.getInstance();
            cal.setTime(open_time);


            while (open_time.before(close_time)) {
                cal.add(Calendar.MINUTE, duration);
                open_time = formatter.parse(formatter.format(cal.getTime()));
                if (open_time.after(formatter.parse(formatter.format(new Date()))) && open_time.before(close_time))
                    times.add(formatter.format(open_time));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;

    }*/

    public static void DetectLocation(final Context context,
                                      final SuccessCallback<Location> SuccessCallback) {

        // Acquire a reference to the system Location Manager
        final LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location
                // provider.
                Log.d("Location", "location Changed");
                SuccessCallback.OnSuccess(location);
                if (locationManager != null) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.removeUpdates(this);
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.d("Location", "location Provider Status Changed");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Location", "location Provider Enabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Location", "location Provider Disabled");
            }
        };
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }
}
