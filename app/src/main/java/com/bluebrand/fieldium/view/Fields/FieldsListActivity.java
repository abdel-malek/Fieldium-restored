package com.bluebrand.fieldium.view.Fields;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.FieldsController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.Booking.HomeActivity;
import com.bluebrand.fieldium.view.Booking.MyVouchersActivity;
import com.bluebrand.fieldium.view.Booking.NotificationActivity;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.Booking.BookingListActivity;
import com.bluebrand.fieldium.view.Player.ProfileActivity;
import com.bluebrand.fieldium.view.adapter.FieldsAdapter;
import com.bluebrand.fieldium.view.other.AboutActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.other.ContactUsActivity;
import com.bluebrand.fieldium.view.other.SettingsActivity;
import com.bluebrand.network.SuccessCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public class FieldsListActivity
        extends MasterActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    GridView mGridView;
    ListView mListView;
    TextView textView_cart_counter;
    View cart_panel;
    Company company;
    int companyId;
    int voucherId;
    String companyName;
    ProgressBar progressBar;
    List<Field> fieldsList;
    Booking booking;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_field_list);
        super.onCreate(savedInstanceState);
        booking = ((FieldiumApplication) getApplication()).getBooking();

        setTitle(companyName == null ? "" : companyName);
        if (checkPlayServices())
            EnableLocation();
        getData();
//        initialNavigationBar();
//        initialSearchView();
    }


    @Override
    protected void getData() {
        companyId = getIntent().getIntExtra("company_id", 0);
        voucherId = getIntent().getIntExtra("voucher_id", 0);
        companyName = getIntent().getStringExtra("company_name");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

//        setTitle(companyName == null ? "Fields" : companyName);
        if (companyId == 0) {
            fieldsList = (List<Field>) getIntent().getExtras().get("fields");
            showData();

        } else {
            showProgress(true);
            if (booking.getDate() != null) {
                FieldsController.getInstance(getmController()).LoadFieldsWithTiming(companyId,
                        mLastLocation != null ? mLastLocation.getLongitude() : 0.0, mLastLocation != null ? mLastLocation.getLatitude() : 0.0,
                        ((FieldiumApplication) getApplication()).getGame_id(), booking, new SuccessCallback<List<Field>>() {
                            @Override
                            public void OnSuccess(List<Field> result) {
                                showProgress(false);
                                fieldsList = result;
                                showData();
                            }
                        });
            } else {
                if (((FieldiumApplication) getApplication()).getGame_id() == 0) {
                    FieldsController.getInstance(getmController()).LoadFields(companyId, voucherId,
                            mLastLocation != null ? mLastLocation.getLongitude() : 0.0, mLastLocation != null ? mLastLocation.getLatitude() : 0.0
                            , new SuccessCallback<List<Field>>() {
                                @Override
                                public void OnSuccess(List<Field> result) {
                                    showProgress(false);
                                    fieldsList = result;
                                    showData();
                                }
                            });
                } else {
                    FieldsController.getInstance(getmController()).LoadFieldsWithTiming(companyId,
                            mLastLocation != null ? mLastLocation.getLongitude() : 0.0, mLastLocation != null ? mLastLocation.getLatitude() : 0.0
                            , ((FieldiumApplication) getApplication()).getGame_id(), booking, new SuccessCallback<List<Field>>() {
                                @Override
                                public void OnSuccess(List<Field> result) {
                                    showProgress(false);
                                    fieldsList = result;
                                    showData();
                                }
                            });
                }
            }
        }
    }

    @Override
    protected void showData() {
        if (fieldsList.size() == 0)
            findViewById(R.id.textView_nodata).setVisibility(View.VISIBLE);
        else {
            setTitle(fieldsList.get(0).getCompany().getName());
            mListView.setAdapter(new FieldsAdapter(getmContext(), R.layout.field_item, fieldsList));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(FieldsListActivity.this, FieldDetailsActivity.class);
                    intent.putExtra("field_id", fieldsList.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }


    @Override
    protected void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        customenavigationMenu();
        super.onStart();

    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected())) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void assignUIRefrences() {
//        mGridView=(GridView)findViewById(R.id.parentGridView);
        mListView = (ListView) findViewById(R.id.listView);
//        textView_cart_counter = (TextView) findViewById(R.id.cart_counter);
//        cart_panel = findViewById(R.id.cart_panel);
        mProgressView = findViewById(R.id.proccess_indicator);
        mFormView = findViewById(R.id.parent_panel);

    }


    @Override
    public void assignActions() {

    }

    public void initialNavigationBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    void customenavigationMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (UserUtils.getInstance(FieldsListActivity.this).IsLogged()) {
            navigationView.getHeaderView(R.id.profile_image);
        /*    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getmContext());
            String profileImage = sharedPreferences.getString("file_path", "");
            if (!profileImage.equals("")) {
                Picasso.with(getmContext()).load(Uri.parse(profileImage))
                        .error(R.drawable.profile_blue)
                        .placeholder(R.drawable.profile_blue)
                        .into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image));
            }*/
           /* try {

                File f = new File(getFilesDir(), "image.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageResource(R.drawable.profile_blue);
            }
*/
            Player player = UserUtils.getInstance(this).Get();
            if (!player.getProfileImage().getName().equals("deleted") && !player.getProfileImage().getName().equals("") && !player.getProfileImage().getName().equals("null")) {
                File f = new File(getFilesDir(), "image.jpg");
                Picasso.with(getmContext()).load(f).memoryPolicy(MemoryPolicy.NO_STORE).into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image));
            } else {
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageResource(R.drawable.profile_blue);
            }

            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            // navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_bookings).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_notifications).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_vouchers).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);

            TextView textViewName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_name);
//            TextView textViewEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_email);

            textViewName.setText(player.getName());
//            textViewEmail.setText(player.getEmail());

        } else {

            //  navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_bookings).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_notifications).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_vouchers).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bookings) {
            Intent intent = new Intent(this, BookingListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.side_menu_my_contact_us) {
            Intent intent = new Intent(this, ContactUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            UserUtils.getInstance(FieldsListActivity.this).SignOut();
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
            sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
            sharedPreferences.edit().putString("file_path", "").apply();
        } else if (id == R.id.nav_login) {
            redirectToRegistrationView();
           /* Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("go_back", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*/
        } else if (id == R.id.nav_notifications) {
            Intent intent = new Intent(this, NotificationActivity.class);
//            intent.putExtra("skip", false);
            startActivity(intent);
        } else if (id == R.id.nav_vouchers) {
            Intent intent = new Intent(this, MyVouchersActivity.class);
//            intent.putExtra("skip", false);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onClick(View v) {

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
