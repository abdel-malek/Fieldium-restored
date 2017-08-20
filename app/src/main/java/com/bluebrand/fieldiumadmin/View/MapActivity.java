package com.bluebrand.fieldiumadmin.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.controller.FieldController;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import com.bluebrand.fieldiumadmin.R;
import com.tradinos.core.network.SuccessCallback;

/**
 * Created by r.desouki on 1/11/2017.
 */
public class MapActivity extends MasterActivity implements GoogleMap.OnMapClickListener, OnMapReadyCallback {
    private double lon;
    private double lat;
    private double old_lon;
    private double old_lat;
    private Company company;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maps);
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        company=(Company) getIntent().getExtras().getSerializable("company");
        lon=company.getLongitude();
        lat=company.getLatitude();
        old_lon=lon;
        old_lat=lat;
        Button save_button=(Button)findViewById(R.id.parent).findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowProgressDialog();
                DetectAddress(getBaseContext(), lat, lon);
                progressDialog.show();
            }
        });

        findViewById(R.id.back_imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lon == old_lon && lat == old_lat) {
                    onBackPressed();
                } else {
                    new android.support.v7.app.AlertDialog.Builder(MapActivity.this)
                            .setMessage("Do you really want to discard the changes?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    onBackPressed();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);

        if ((lat != 0) && (lon != 0)) {
            LatLng latLng = new LatLng(lat, lon);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    latLng, 14);
            googleMap.animateCamera(cameraUpdate);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat,  lon)));
        } else {
            Location location=DetectLocation();
            if(location!=null){
                LatLng latLng = new LatLng(location.getLatitude(), location
                        .getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory
                        .newLatLngZoom(latLng, 14);
                googleMap.animateCamera(cameraUpdate);
            }
            else{
                LatLng latLng = new LatLng(0,0);
                CameraUpdate cameraUpdate = CameraUpdateFactory
                        .newLatLngZoom(latLng, 14);
                googleMap.animateCamera(cameraUpdate);
            }
        }
        showMessage();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        lat=latLng.latitude;
        lon=latLng.longitude;
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude,  latLng.longitude)));
    }

    public  void showMessage (){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent),"Please select your company location.", Snackbar.LENGTH_INDEFINITE);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();

    }

    @Override
    void getData() {

    }

    @Override
    void showData() {

    }

    @Override
    void assignUIReferences() {

    }

    @Override
    void assignActions() {

    }

    @Override
    public void onClick(View view) {

    }

    Location loc;
    public Location DetectLocation() {
        // Acquire a reference to the system Location Manager
        final LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location
                // provider.
                loc=location;
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.d("Location","location Provider Status Changed");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Location","location Provider Enabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Location","location Provider Disabled");
            }
        };

        // Register the listener with the Location Manager to receive location
        // updates

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        return loc;
    }

    public void DetectAddress(final Context context, final double latitude,
                              final double longitude) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Geocoder geo = new Geocoder(context, Locale.ENGLISH);
                    List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);

                    Intent intent = new Intent();

                    if (!addresses.isEmpty()) {
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            intent.putExtra("Country", address.getCountryName());
                            intent.putExtra("State", address.getAdminArea());
                            intent.putExtra("City", address.getLocality());
                            String addressLine = "";
							/*for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
								addressLine += address.getAddressLine(i) + " , ";
							}
							addressLine += address.getAddressLine(address.getMaxAddressLineIndex());*/
                            addressLine += address.getAddressLine(0);
                            intent.putExtra("AddressDetails", addressLine);
                            intent.putExtra("PostalCode", address.getPostalCode());
                            intent.putExtra("Latitude", latitude); // double
                            intent.putExtra("Longitude", longitude); // double
                        }
                    }

                    company.setLongitude(longitude);
                    company.setLatitude(latitude);
                    Back(intent);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    HideProgressDialog();
                }
            }
        }).run();


    }

    public void Back(final Intent intent) {
        if (intent != null) {
            FieldController.getInstance(mController).updateCompanyLocation(company.getID(), company.getLongitude(), company.getLatitude(), new SuccessCallback<Company>() {
                @Override
                public void OnSuccess(Company result) {
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }
    }
}
