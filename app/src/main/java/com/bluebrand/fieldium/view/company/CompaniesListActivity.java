package com.bluebrand.fieldium.view.company;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.Booking.HomeActivity;
import com.bluebrand.fieldium.view.Booking.MyVouchersActivity;
import com.bluebrand.fieldium.view.Booking.NotificationActivity;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.Booking.BookingListActivity;
import com.bluebrand.fieldium.view.Player.ProfileActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.adapter.CompanyAdapter;
import com.bluebrand.fieldium.view.other.AboutActivity;
import com.bluebrand.fieldium.view.other.ContactUsActivity;
import com.bluebrand.fieldium.view.other.SettingsActivity;
import com.bluebrand.network.SuccessCallback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 4/22/16.
 */
public class CompaniesListActivity
        extends MasterActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SearchView searchView;
    GridView mGridView;
    ListView mListView;
    TextView textView_noData;
    View cart_panel;
    Company company;
    int companyId;
    ProgressBar progressBar;
    List<Company> companiesList;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_field_list);
        super.onCreate(savedInstanceState);
        setTitle("Stadiums");
        getData();
//        initialNavigationBar();
//        initialSearchView();
    }


    @Override
    protected void getData() {
        companiesList = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            companiesList = (List<Company>) getIntent().getExtras().get("companies");
            showData();
        } else {
            searchView.setVisibility(View.VISIBLE);
            searchView.requestFocus();
            searchView.onActionViewExpanded();
            findViewById(R.id.toolbar_title).setVisibility(View.GONE);
        }


    }

    @Override
    protected void showData() {

        if (companiesList.size() == 0) {
            textView_noData.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            textView_noData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListView.setAdapter(new CompanyAdapter(getmContext(), R.layout.company_item, companiesList));
        }

    }

    @Override
    protected void onStart() {
        customenavigationMenu();
        super.onStart();
    }


    @Override
    public void assignUIRefrences() {

        mListView = (ListView) findViewById(R.id.listView);
        mProgressView = findViewById(R.id.proccess_indicator);
        mFormView = findViewById(R.id.parent_panel);
        searchView = (SearchView) findViewById(R.id.searchView);
        textView_noData = (TextView) findViewById(R.id.textView_nodata);
        textView_noData.setVisibility(View.GONE);
    }


    @Override
    public void assignActions() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                query = queryString;
                showProgress(true);
                BookingController.getInstance(getmController()).Search(0, query, 0, new Booking()/*, UserUtils.getInstance(getmContext()).getCountry().getId()*/, "", new SuccessCallback<ArrayList<Company>>() {
                    @Override
                    public void OnSuccess(ArrayList<Company> result) {
                        showProgress(false);
                        companiesList = new ArrayList<Company>();
                        companiesList.addAll(result);
                        showData();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        if (UserUtils.getInstance(CompaniesListActivity.this).IsLogged()) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            // navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_bookings).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_notifications).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_vouchers).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
        /*    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getmContext());
            String profileImage = sharedPreferences.getString("file_path", "");
            if (!profileImage.equals("")) {
                Picasso.with(getmContext()).load(Uri.parse(profileImage))
                        .into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image));
            }*/
          /*  try {

                File f = new File(getFilesDir(), "image.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageResource(R.drawable.profile_blue);
            }*/
            Player player = UserUtils.getInstance(this).Get();
            if (!player.getProfileImage().getName().equals("deleted") && !player.getProfileImage().getName().equals("") && !player.getProfileImage().getName().equals("null")) {

                File f = new File(getFilesDir(), "image.jpg");
                try {
                    Picasso.with(getmContext()).load(f).memoryPolicy(MemoryPolicy.NO_STORE)
                            .error(R.drawable.profile_blue)
                            .placeholder(R.drawable.profile_blue).into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image));
                } catch (Exception e) {
                    ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageResource(R.drawable.profile_blue);
                }
            } else {
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageResource(R.drawable.profile_blue);
            }
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
            UserUtils.getInstance(CompaniesListActivity.this).SignOut();
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
            sharedPreferences.edit().putString("file_path", "").apply();
        } else if (id == R.id.nav_login) {
            redirectToRegistrationView();
      /*      Intent intent = new Intent(this, RegisterActivity.class);
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

    public void noTokenDialog() {
        final Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.no_token_dialog);
        myDialog.setCancelable(false);
        Button yes = (Button) myDialog.findViewById(R.id.Ok);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do your code here
                myDialog.dismiss();
            }
        });

        myDialog.show();
        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
