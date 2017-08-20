package com.bluebrand.fieldium.view.company;

/**
 * Created on 9/6/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.CompaniesController;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.view.Fields.FieldsListActivity;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class NearbyCompaniesActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LocationListener {

    LatLng Curr;
    Projection projection;
    private GoogleMap googleMap;
    final int MY_PERMISSIONS_REQUEST_LOCATION = 200;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MainActivity";
    Location mLastLocation;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String addressLine;
    //    boolean flage = true;
    public Marker marker;
    ProgressDialog progress;
    List<Company> nearbyCompanies;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    int nearbyCompanyIndex = -1;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_nearby_company);
        nearbyCompanies = new ArrayList<>();

        progress = ProgressDialog.show(this, "",
                "Please wait...", true);
        progress.dismiss();

        if (checkPlayServices())
            EnableLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleMap = mapFragment.getMap();
        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

    }

    protected void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
//        check();
        settingsRequest();
        super.onStart();
    }

    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        googleMap.setMyLocationEnabled(true);
                        Curr = new LatLng(25.2048, 55.2708);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                Curr, 15);
                        googleMap.animateCamera(cameraUpdate);
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        toDo();
                    }
            }
        }
    }

    private void getCompanieslist() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        CompaniesController.getInstance(new Controller(this, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
            }
        })).GetNearbyCompany(mLastLocation != null ? mLastLocation.getLongitude() : 0.0, mLastLocation != null ? mLastLocation.getLatitude() : 0.0,
                new SuccessCallback<List<Company>>() {
                    @Override
                    public void OnSuccess(List<Company> data) {
                        nearbyCompanies.addAll(data);
                        progress.dismiss();
                        if (data.size() != 0) {
                            nearbyCompanyIndex = 0;
//                            flage = false;
                            for (int i = 0; i < data.size(); i++)
                                setMarker(data.get(i));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(data.get(0).getAddress().getLatitude(), data.get(0).getAddress().getLongitude()), 15);
                            googleMap.animateCamera(cameraUpdate);
                        }
                    }
                }
        );
    }

    private void getLatLng() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels - 80;
        int width = displaymetrics.widthPixels - 10;
//        LT = googleMap.getProjection().fromScreenLocation(new Point(10, 45));
//        RB = googleMap.getProjection().fromScreenLocation(
//                new Point(width, height));
    }

    public void setMarker(Company company) {
        LatLng pos = new LatLng(company.getAddress().getLatitude(), company.getAddress().getLongitude());

        int markerRes = R.mipmap.location;

        googleMap.addMarker(new MarkerOptions().position(pos)
                .title(company.getName()).icon(BitmapDescriptorFactory.fromResource(markerRes))
                .snippet(String.valueOf(company.getId())))
        ;
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;
      /*  Utils.DetectLocation(this, new SuccessCallback<Location>() {
            @Override
            public void OnSuccess(Location location) {
                Curr = new LatLng(location.getLatitude(), location
                        .getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        Curr, 15);
                map.animateCamera(cameraUpdate);
                // setUpMap();
            }
        });*/
    }

    public void Back(Intent intent, double latitude, double longitude) {
        if (intent != null) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(this, "Need permission!", Toast.LENGTH_SHORT).show();


                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return;

        }

//        googleMap.setMyLocationEnabled(true);
        LatLng marker = new LatLng(25.2048, 55.2708);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                marker, 17);
        googleMap.animateCamera(cameraUpdate);

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        toDo();
//        getMarkerLocation(mLastLocation);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    void EnableLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void settingsRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(NearbyCompaniesActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK: {

                        googleMap.setMyLocationEnabled(true);
                        LatLng marker = new LatLng(25.2048, 55.2708);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                marker, 17);
                        googleMap.animateCamera(cameraUpdate);
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        toDo();
                    }
                    break;
                    case Activity.RESULT_CANCELED:
                        settingsRequest();//keep asking if imp or do whatever
                        break;
                }
                break;
        }
    }

    public void toDo() {
        googleMap.setMyLocationEnabled(true);

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                String test=arg0.getId();
                Intent intent = new Intent(getBaseContext(), FieldsListActivity.class);
                int s = Integer.valueOf(arg0.getSnippet());
                intent.putExtra("company_id", s);
                startActivity(intent);
            }
        });
//        googleMap.clear();
        Button btn_nearby_companies = (Button) findViewById(R.id.button_showCompanies);
        btn_nearby_companies.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                flage = true;
//                getLatLng();
//                if (googleMap.getCameraPosition().zoom >= 10) {

                Curr = googleMap.getCameraPosition().target;
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (nearbyCompanyIndex != -1) {
                    if (nearbyCompanyIndex == nearbyCompanies.size()-1)
                        nearbyCompanyIndex = -1;
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(nearbyCompanies.get(nearbyCompanyIndex + 1).getAddress().getLatitude(), nearbyCompanies.get(nearbyCompanyIndex + 1).getAddress().getLongitude()), 15);
                    googleMap.animateCamera(cameraUpdate);
                    nearbyCompanyIndex += 1;
                } else {
                    progress.show();
                    getCompanieslist();
                }
            }

        });
    }

    private class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.company_location_pop_up,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            if (NearbyCompaniesActivity.this.marker != null
                    && NearbyCompaniesActivity.this.marker.isInfoWindowShown()) {
                NearbyCompaniesActivity.this.marker.hideInfoWindow();
                NearbyCompaniesActivity.this.marker.showInfoWindow();
            }
            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {
            NearbyCompaniesActivity.this.marker = marker;

            final String companyName = marker.getTitle();
            final TextView titleUi = ((TextView) view.findViewById(R.id.company_name));
            if (companyName != null) {
                titleUi.setText(companyName);
            } else {
                titleUi.setText("");
            }
/*            final String snippet = marker.getSnippet();*/

            return view;
        }
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