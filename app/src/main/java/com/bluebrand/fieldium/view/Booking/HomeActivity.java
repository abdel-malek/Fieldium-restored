package com.bluebrand.fieldium.view.Booking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.HomeScreenModel;
import com.bluebrand.fieldium.core.model.Offer;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.fieldium.view.adapter.GamesAdapter;
import com.bluebrand.fieldium.view.other.AboutActivity;
import com.bluebrand.fieldium.view.other.ContactUsActivity;
import com.bluebrand.fieldium.view.other.DatePickerFragment;
import com.bluebrand.fieldium.view.other.SettingsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.view.other.CustomSpinner;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.Fields.FieldsListActivity;
import com.bluebrand.fieldium.view.MasterFormActivity;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.Player.ProfileActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.adapter.AreaAdapter;
import com.bluebrand.fieldium.view.company.CompaniesListActivity;
import com.bluebrand.fieldium.view.company.NearbyCompaniesActivity;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.InternetManager;
import com.bluebrand.network.SuccessCallback;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends MasterFormActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerFragment.OnDialogClosed, View.OnClickListener {
    ProgressBar progressBar_city, progressBar_soccer;
    CustomSpinner time_spinner, date_spinner, spinner_game;
    Spinner spinner_city;
    String query, token;
    Booking booking, tempBooking;
    int areaId, gameId = 1;
    ArrayList<String> timeList, dateList;
    boolean onTimeSelected = true, onDateSelected = true;
    TextView search_textview;
    TextView upcomingBooking_fieldName, upcomingBooking_date, upcomingBooking_time, textView_noUpcomingBooking;
    ImageView upcomingImageView;
    ImageButton nearby_companies;
    private SwipeRefreshLayout swipeContainer;
    Button register_button;
    FieldiumApplication fieldiumApplication;
    boolean playerLogged = false;// if we register from field details activity we must refresh home screen
//    private CustomProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_home_screen);
        super.onCreate(savedInstanceState);
        initialNavigationBar();
        fieldiumApplication = ((FieldiumApplication) getApplication());
        customStyle();
        getData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_nearby_companies:
                fieldiumApplication.setGame_id(0);
                fieldiumApplication.setBooking(new Booking());
                Intent intent = new Intent(this, NearbyCompaniesActivity.class);
                startActivity(intent);
//                startActivityForResult(intent, 42);
                break;
        }
        super.onClick(v);
    }

    @Override
    public void getData() {
//        if (getCurrentFocus() != null)
//            getCurrentFocus().clearFocus();
        timeList = new ArrayList<>();
        timeList.add(getResources().getString(R.string.any_time));
        timeList.add(getResources().getString(R.string.pick_time));
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, timeList);
        time_spinner.setAdapter(timeAdapter);
        /*time_spinner.setSelection(0);*/

        dateList = new ArrayList<>();
        dateList.add(getResources().getString(R.string.any_date));
        dateList.add(getResources().getString(R.string.pick_date));
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, dateList);
        date_spinner.setAdapter(dateAdapter);
        /*date_spinner.setSelection(0);*/

        if (UserUtils.getInstance(getmContext()).IsLogged()) {
            showProgress(true);
            BookingController.getInstance(getmController()).getHomeScreenModel(new SuccessCallback<HomeScreenModel>() {
                @Override
                public void OnSuccess(HomeScreenModel result) {
                    findViewById(R.id.register_button_no_booking).setVisibility(View.GONE);
                    showProgress(false);
                    viewLastBookings(result.getLastBookings());
                    if (result.getUpcomingBookings().size() != 0) {
                        textView_noUpcomingBooking.setVisibility(View.GONE);
                        viewUpcomingBooking(result.getUpcomingBookings().get(0));
                    } else {
//                        findViewById(R.id.upcomingBooking_panel).setVisibility(View.GONE);
                        textView_noUpcomingBooking.setVisibility(View.VISIBLE);
                        textView_noUpcomingBooking.setText(getResources().getString(R.string.no_upcoming_user));
                    }

                    progressBar_city.setVisibility(View.GONE);
                    ArrayAdapter<City> areasAdapter = new AreaAdapter(getmContext(), R.layout.spinner_item, result.getCities());
                    spinner_city.setAdapter(areasAdapter);
                    City mCity = UserUtils.getInstance(getmContext()).getCity();
                    if (mCity != null)
                        for (int l = 0; l < result.getCities().size(); l++) {
                            if (result.getCities().get(l).equals(mCity)) {
                                spinner_city.setSelection(l);
                                break;
                            }
                        }
                    progressBar_soccer.setVisibility(View.GONE);
                    ArrayAdapter<Game> gameAdapter = new GamesAdapter(getmContext(), R.layout.spinner_item, result.getGames());
                    spinner_game.setAdapter(gameAdapter);
                    viewMyVouchers(result.getVouchers());
                    viewOffers(result.getOfferes());
                }
            });

        } else {
            findViewById(R.id.upcomingBooking_panel).setVisibility(View.GONE);
            findViewById(R.id.lastBookings_panel).setVisibility(View.GONE);
            findViewById(R.id.register_button_no_booking).setVisibility(View.VISIBLE);
            textView_noUpcomingBooking.setVisibility(View.VISIBLE);
            textView_noUpcomingBooking.setText(getResources().getString(R.string.no_upcoming_not_user));
            BookingController.getInstance(getmController()).getHomeScreenModel(new SuccessCallback<HomeScreenModel>() {
                @Override
                public void OnSuccess(HomeScreenModel result) {
                    progressBar_city.setVisibility(View.GONE);
                    ArrayAdapter<City> areasAdapter = new AreaAdapter(getmContext(), R.layout.spinner_item, result.getCities());
                    spinner_city.setAdapter(areasAdapter);
                    City mCity = UserUtils.getInstance(getmContext()).getCity();
                    if (mCity != null)
                        for (int l = 0; l < result.getCities().size(); l++) {
                            if (result.getCities().get(l).equals(mCity)) {
                                spinner_city.setSelection(l);
                                break;
                            }
                        }
                    progressBar_soccer.setVisibility(View.GONE);
                    ArrayAdapter<Game> gameAdapter = new GamesAdapter(getmContext(), R.layout.spinner_item, result.getGames());
                    spinner_game.setAdapter(gameAdapter);
                    viewOffers(result.getOfferes());
                }
            });

        }
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignActions() {
        nearby_companies.setOnClickListener(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.

                refreshHomeScreen();

            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToRegistrationView();
            }
        });

        /////////Time Spinner
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (onTimeSelected/*UserUtils.getInstance(getmContext()).GetBooleanTimeSelected()*/) {
                    if ((fieldiumApplication.getBooking()).getGame() == null) {
                        fieldiumApplication.getBooking().setGame((Game) spinner_game.getSelectedItem());
                    }
                    if (time_spinner.getSelectedItem().equals(getResources().getString(R.string.any_time))) {
                        Booking boking = fieldiumApplication.getBooking();
                        boking.setStartTime(null);
                        boking.setDuration(null);
                        fieldiumApplication.setBooking(boking);
                    } else {
                        if ((fieldiumApplication.getBooking()).getGame() != null) {
                            Intent intent = new Intent(HomeActivity.this, TimingActivityHomeScreen.class);
                            startActivityForResult(intent, 42);
                        } else {
                            Toast.makeText(fieldiumApplication, getResources().getString(R.string.please_select_game), Toast.LENGTH_SHORT).show();
                            time_spinner.setSelection(0);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        time_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTimeSelected = true;
//                UserUtils.getInstance(getmContext()).SetBooleanTimeSelected(true);
                return false;
            }
        });

        /////////////Date Spinner
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (onDateSelected/*UserUtils.getInstance(getmContext()).GetBooleanDateSelected()*/) {
                    if (date_spinner.getSelectedItem().equals(getResources().getString(R.string.any_date))) {
                        //reset booking timing and reuse pick time in list
                        Booking reset_booking = new Booking();
                        reset_booking.setGame(fieldiumApplication.getBooking().getGame());
                        fieldiumApplication.setBooking(reset_booking);
                        findViewById(R.id.time_spinner_panel).setVisibility(View.GONE);
                        timeList = new ArrayList<>();
                        timeList.add(getResources().getString(R.string.any_time));
                        timeList.add(getResources().getString(R.string.pick_time));
                        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, timeList);
                        time_spinner.setAdapter(timeAdapter);
                    } else {
                        showDatePickerDialog(/*view,*/ getSupportFragmentManager());
//                        Intent intent = new Intent(HomeActivity.this, TimingActivity.class);
//                        startActivityForResult(intent, 42);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        date_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onDateSelected = true;
//                UserUtils.getInstance(getmContext()).SetBooleanDateSelected(true);
                return false;
            }
        });

        search_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldiumApplication.setGame_id(0);
                fieldiumApplication.setBooking(new Booking());
                Intent intent = new Intent(HomeActivity.this, CompaniesListActivity.class);
                startActivity(intent);
            }
        });

        //////
        // game spinner
        spinner_game.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (onTimeSelected) {
                Booking booking1 = fieldiumApplication.getBooking();
                booking1.setGame((Game) spinner_game.getSelectedItem());
                fieldiumApplication.setBooking(booking1);

//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        super.assignActions();
    }

    @Override
    public void assignUIRefrences() {
        progressBar_city = (ProgressBar) findViewById(R.id.progressLoading_city);
        progressBar_soccer = (ProgressBar) findViewById(R.id.progressLoading_game);
        time_spinner = (CustomSpinner) findViewById(R.id.time_spinner);
        date_spinner = (CustomSpinner) findViewById(R.id.date_spinner);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_game = (CustomSpinner) findViewById(R.id.spinner_game);
        mProgressView = findViewById(R.id.proccess_indicator);
        mFormView = findViewById(R.id.form_container);
//        searchView = (SearchView) findViewById(R.id.searchView);
        upcomingBooking_fieldName = (TextView) findViewById(R.id.upcomingBooking_fieldName);
        upcomingBooking_date = (TextView) findViewById(R.id.upcomingBooking_date);
        upcomingBooking_time = (TextView) findViewById(R.id.upcomingBooking_time);
        upcomingImageView = (ImageView) findViewById(R.id.upcomingBooking_imageView);
        search_textview = (TextView) findViewById(R.id.search_textView);
//        searchView.onActionViewExpanded();
//        searchView.clearFocus();
//        this.getCurrentFocus().clearFocus();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        nearby_companies = (ImageButton) findViewById(R.id.show_nearby_companies);
        register_button = (Button) findViewById(R.id.register_button_no_booking);
        textView_noUpcomingBooking = (TextView) findViewById(R.id.textView_noUpcomingBooking);

    }

    @Override
    public void submitForm() {
        ((Button) findViewById(R.id.button_submit)).setEnabled(false);
        try {
            if ((Game) spinner_game.getSelectedItem() != null) {
                gameId = ((Game) spinner_game.getSelectedItem()).getId();
                fieldiumApplication.getBooking().setGame((Game) spinner_game.getSelectedItem());
            } else
                throw new InvalidInputException(getResources().getString(R.string.please_select_game));
            if ((City) spinner_city.getSelectedItem() != null) {
                areaId = ((City) spinner_city.getSelectedItem()).getId();
                UserUtils.getInstance(getmContext()).saveCity((City) spinner_city.getSelectedItem());
            } else
                throw new InvalidInputException(getResources().getString(R.string.please_select_city));
            if (date_spinner.getSelectedItem().equals(getResources().getString(R.string.any_date))) {

                Booking reset_booking = new Booking();
                reset_booking.setGame(fieldiumApplication.getBooking().getGame());
                fieldiumApplication.setBooking(reset_booking);
            }
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            token = sharedPreferences.getString("token", "123");
//            final String sentUserToken = sharedPreferences.getString("user_token_sent_to_server", "");
//            final String sentToken = sharedPreferences.getString("unregistered_user_token_sent_to_server", "");

            booking = fieldiumApplication.getBooking();
            sendTokenToServer(token);

        } catch (InvalidInputException e) {
            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            findViewById(R.id.button_submit).setEnabled(true);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 42 && resultCode == RESULT_OK) {
            booking = fieldiumApplication.getBooking();
            tempBooking = booking;
            timeList.remove(1);//remove pick time and add the selected time
            timeList.add(booking.getStartTime());
            ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, timeList);
            time_spinner.setAdapter(timeAdapter);
            time_spinner.setSelection(1);
            onTimeSelected = false;
//            UserUtils.getInstance(getmContext()).SetBooleanTimeSelected(false);

        } else if (requestCode == 42 && resultCode == 0) {
            //user press cancel in timing activity
            if (time_spinner.getSelectedItem().equals(getResources().getString(R.string.pick_time)))
                time_spinner.setSelection(0);//select any time
            else if (!time_spinner.getSelectedItem().equals(getResources().getString(R.string.any_time)))
                fieldiumApplication.setBooking(tempBooking);
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
            /*Intent intent = new Intent(this, BookingListActivity.class);
            startActivity(intent);*/
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
            UserUtils.getInstance(HomeActivity.this).SignOut();
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
        } else if (id == R.id.nav_vouchers) {
            Intent intent = new Intent(this, MyVouchersActivity.class);
//            intent.putExtra("skip", false);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
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

    @Override
    protected void onStart() {

        customenavigationMenu();
        if (UserUtils.getInstance(getmContext()).IsLogged() && playerLogged == false) {
            showProgress(true);
            refreshHomeScreen();
        }
        Booking booking1 = fieldiumApplication.getBooking();
        Booking booking2 = new Booking();

        String booking1String = new Gson().toJson(booking1);
        String booking2String = new Gson().toJson(booking2);
        if (booking1String.equals(booking2String)//compare the two objects(if true then the booking reset from field details act. (on back pressed) )
           /* ((FieldiumApplication)getApplication()).getBooking().getDate()==null
            || ((FieldiumApplication)getApplication()).getBooking().getDuration()==null*/) {
            timeList = new ArrayList<>();
            timeList.add(getResources().getString(R.string.any_time));
            timeList.add(getResources().getString(R.string.pick_time));
            ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, timeList);
            time_spinner.setAdapter(timeAdapter);
            /*time_spinner.setSelection(0);*/
            findViewById(R.id.time_spinner_panel).setVisibility(View.GONE);

            dateList = new ArrayList<>();
            dateList.add(getResources().getString(R.string.any_date));
            dateList.add(getResources().getString(R.string.pick_date));
            ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, dateList);
            date_spinner.setAdapter(dateAdapter);
            /*date_spinner.setSelection(0);*/
        }
        super.onStart();
    }

    @Override
    protected void onPause() {
        if (UserUtils.getInstance(getmContext()).IsLogged())
            playerLogged = true;
        super.onPause();
    }

    void customenavigationMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (UserUtils.getInstance(HomeActivity.this).IsLogged()) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            // navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_bookings).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_notifications).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_vouchers).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);

//            try {
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
//                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
//                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image)).setImageResource(R.drawable.profile_blue);
//            }
            TextView textViewName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_name);
//            TextView textViewEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_email);
            textViewName.setText(player.getName());
//            textViewEmail.setText(player.getEmail());
        } else {
            //  navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_notifications).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_bookings).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_vouchers).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        }
    }

    @Override
    protected void onResume() {
        findViewById(R.id.button_submit).setEnabled(true);
        super.onResume();
    }

    public void viewUpcomingBooking(final Booking booking) {
        findViewById(R.id.upcomingBooking_panel).setVisibility(View.VISIBLE);
        upcomingBooking_fieldName.setText(booking.getField().getName());
        upcomingBooking_time.setText(booking.getStartTime());
        upcomingBooking_date.setText(booking.getDate());
        try {
            ImageLoader mImageLoader = InternetManager.getInstance(getmContext()).getImageLoader();
            mImageLoader.get(booking.getField().getCompany().getImageURL(), ImageLoader.getImageListener(upcomingImageView,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
//            e.getMessage();
            upcomingImageView.setImageResource(R.drawable.default_image);
        }

        upcomingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookingDetailActivity.class);
                intent.putExtra("booking", booking);
                startActivity(intent);
            }
        });
    }

    public String convertTime(String time) {
        String formattedTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        try {
            formattedTime = formatter.format(formatter1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }


    public void viewLastBookings(final List<Booking> lastBookingsList) {
        if (lastBookingsList.size() == 0)
            findViewById(R.id.lastBookings_panel).setVisibility(View.GONE);
        else {
            findViewById(R.id.lastBookings_panel).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.lastBookings_layout)).removeAllViews();

            if (lastBookingsList.size() == 1)
                ((LinearLayout) findViewById(R.id.lastBookings_panel)).setGravity(Gravity.CENTER_HORIZONTAL);
            for (int i = 0; i < lastBookingsList.size(); i++) {
                //create imageview here and setbg
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
                layoutParams.setMargins(10, 10, 10, 10);
//            if (lastBookingsList.size() == 1)
//                layoutParams.gravity=Gravity.CENTER;
                imageView.setLayoutParams(layoutParams);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                try {
                    Picasso.with(getmContext()).load(lastBookingsList.get(i).getField().getCompany().getLogoURL())
                            .error(R.drawable.default_image)
//                        .fitCenter()
                            .placeholder(R.drawable.default_image)
                            .into(imageView);
                } catch (Exception e) {

                }
                ((LinearLayout) findViewById(R.id.lastBookings_layout)).addView(
                        imageView, i);

                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, FieldsListActivity.class);
                        fieldiumApplication.setBooking(new Booking());
                        fieldiumApplication.setGame_id(0);
                        intent.putExtra("company_id", lastBookingsList.get(finalI).getField().getCompany().getId());
                        startActivity(intent);
                    }
                });
            }
        }

    }

    public void sendTokenToServer(final String mToken) {
        showProgress(true);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();

        final String sentUserToken = sharedPreferences.getString("user_token_sent_to_server", "");
        final String sentToken = sharedPreferences.getString("unregistered_user_token_sent_to_server", "");
        if (UserUtils.getInstance(getmContext()).IsLogged()) {
            if (!mToken.equals("123") && !mToken.equals(sentUserToken)) {
                PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                    @Override
                    public void OnFaild(Code errorCode, String Message) {
                        sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                        search();
                    }
                })).refreshToken(mToken, new SuccessCallback<Player>() {
                    @Override
                    public void OnSuccess(Player result) {
                        final Player player = UserUtils.getInstance(getApplicationContext()).Get();
                        player.setToken(mToken);
                        UserUtils.getInstance(getApplicationContext()).Save(player);
                        sharedPreferences.edit().putString("user_token_sent_to_server", mToken).apply();
                        search();
                    }
                });
            } else {
                token = UserUtils.getInstance(getmContext()).Get().getToken();
                search();
            }
        } else {
            if (!mToken.equals("123") && !mToken.equals(sentToken)) {
                PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                    @Override
                    public void OnFaild(Code errorCode, String Message) {
                        sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                        search();
                    }
                })).unRegisteredUseRefreshToken(token, new SuccessCallback<String>() {
                    @Override
                    public void OnSuccess(String result) {
                        sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", token).apply();
                        search();
                    }
                });
            } else {
                search();
            }
        }
    }

    public void search() {

//        if (booking.getDate() != null) {
        fieldiumApplication.setGame_id(gameId);

        BookingController.getInstance(getmController()).Search(areaId, "", gameId, booking,
                /*UserUtils.getInstance(getmContext()).getCountry().getId(),*/ token, new SuccessCallback<ArrayList<Company>>() {
                    @Override
                    public void OnSuccess(ArrayList<Company> result) {
                        showProgress(false);
                        Intent intent = new Intent(HomeActivity.this, CompaniesListActivity.class);
                        intent.putExtra("companies", result);
                        startActivity(intent);
                    }
                });
        /*} else {
            BookingController.getInstance(getmController()).Search(areaId, query, gameId, token, new SuccessCallback<ArrayList<Company>>() {
                @Override
                public void OnSuccess(ArrayList<Company> result) {
                    showProgress(false);
                    Intent intent = new Intent(HomeActivity.this, CompaniesListActivity.class);
                    intent.putExtra("companies", result);
                    startActivity(intent);
                }
            });
        }*/
    }

    @Override
    public void OnDialogClosed(/*int resId,*/ int year, int month, int day) {
        if (year == 0 && month == 0 && day == 0) {
            //user pressed cancel in date picker
            if (date_spinner.getSelectedItem().equals(getResources().getString(R.string.pick_date)))
                date_spinner.setSelection(0);
        } else if (year == -1 && month == -1 && day == -1) {
            date_spinner.setSelection(0);
        } else {
            booking = fieldiumApplication.getBooking();

            String bookingDate = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
            booking.setDate(bookingDate);
//          tempBooking = booking;
//            Booking booking = new Booking();
            dateList.remove(1);//remove pick date and add the selected date
            dateList.add(booking.getDate());
            fieldiumApplication.setBooking(booking);
            ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, dateList);
            date_spinner.setAdapter(dateAdapter);
            date_spinner.setSelection(1);
            findViewById(R.id.time_spinner_panel).setVisibility(View.VISIBLE);
            onDateSelected = false;
//            UserUtils.getInstance(getmContext()).SetBooleanDateSelected(false);

        }
    }

    public void customStyle() {
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    void refreshHomeScreen() {
        timeList = new ArrayList<>();
        timeList.add(getResources().getString(R.string.any_time));
        timeList.add(getResources().getString(R.string.pick_time));
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, timeList);
        time_spinner.setAdapter(timeAdapter);
        findViewById(R.id.time_spinner_panel).setVisibility(View.GONE);

        dateList = new ArrayList<>();
        dateList.add(getResources().getString(R.string.any_date));
        dateList.add(getResources().getString(R.string.pick_date));
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, dateList);
        date_spinner.setAdapter(dateAdapter);
        fieldiumApplication.setBooking(new Booking());
        booking = new Booking();
        if (UserUtils.getInstance(getmContext()).IsLogged()) {
//            showProgress(true);
            BookingController.getInstance(getmController()).getHomeScreenModel(new SuccessCallback<HomeScreenModel>() {
                @Override
                public void OnSuccess(HomeScreenModel result) {
                    findViewById(R.id.register_button_no_booking).setVisibility(View.GONE);
                    showProgress(false);
                    swipeContainer.setRefreshing(false);
//                    snackbar.dismiss();
                    viewLastBookings(result.getLastBookings());
                    if (result.getUpcomingBookings().size() != 0) {
                        textView_noUpcomingBooking.setVisibility(View.GONE);
                        viewUpcomingBooking(result.getUpcomingBookings().get(0));
                    } else {
                        findViewById(R.id.upcomingBooking_panel).setVisibility(View.GONE);
                        textView_noUpcomingBooking.setVisibility(View.VISIBLE);
                        textView_noUpcomingBooking.setText(getResources().getString(R.string.no_upcoming_user));
                    }
                    progressBar_city.setVisibility(View.GONE);
                    ArrayAdapter<City> areasAdapter = new AreaAdapter(getmContext(), R.layout.spinner_item, result.getCities());
                    spinner_city.setAdapter(areasAdapter);
                    City mCity = UserUtils.getInstance(getmContext()).getCity();
                    if (mCity != null)
                        for (int l = 0; l < result.getCities().size(); l++) {
                            if (result.getCities().get(l).equals(mCity)) {
                                spinner_city.setSelection(l);
                                break;
                            }
                        }
                    progressBar_soccer.setVisibility(View.GONE);
                    ArrayAdapter<Game> gameAdapter = new GamesAdapter(getmContext(), R.layout.spinner_item, result.getGames());
                    spinner_game.setAdapter(gameAdapter);
                    viewMyVouchers(result.getVouchers());
                    viewOffers(result.getOfferes());
                }
            });
        } else {
            findViewById(R.id.upcomingBooking_panel).setVisibility(View.GONE);
            findViewById(R.id.lastBookings_panel).setVisibility(View.GONE);
            findViewById(R.id.register_button_no_booking).setVisibility(View.VISIBLE);
            textView_noUpcomingBooking.setVisibility(View.VISIBLE);
            textView_noUpcomingBooking.setText(getResources().getString(R.string.no_upcoming_not_user));
            BookingController.getInstance(getmController()).getHomeScreenModel(new SuccessCallback<HomeScreenModel>() {
                @Override
                public void OnSuccess(HomeScreenModel result) {
                    swipeContainer.setRefreshing(false);
                    progressBar_city.setVisibility(View.GONE);
                    ArrayAdapter<City> areasAdapter = new AreaAdapter(getmContext(), R.layout.spinner_item, result.getCities());
                    spinner_city.setAdapter(areasAdapter);
                    City mCity = UserUtils.getInstance(getmContext()).getCity();
                    if (mCity != null)
                        for (int l = 0; l < result.getCities().size(); l++) {
                            if (result.getCities().get(l).equals(mCity)) {
                                spinner_city.setSelection(l);
                                break;
                            }
                        }
                    progressBar_soccer.setVisibility(View.GONE);
                    ArrayAdapter<Game> gameAdapter = new GamesAdapter(getmContext(), R.layout.spinner_item, result.getGames());
                    spinner_game.setAdapter(gameAdapter);
                    viewOffers(result.getOfferes());
                }
            });
            /*  BookingController.getInstance(getmController()).getAreas(new SuccessCallback<List<City>>() {
                @Override
                public void OnSuccess(List<City> cities) {
                    swipeContainer.setRefreshing(false);
                    progressBar_city.setVisibility(View.GONE);
                    ArrayAdapter<City> areasAdapter = new AreaAdapter(getmContext(), R.layout.spinner_item, cities);
                    spinner_city.setAdapter(areasAdapter);
                    City mCity = UserUtils.getInstance(getmContext()).getCity();
                    if (mCity != null)
                        for (int l = 0; l < cities.size(); l++) {
                            if (cities.get(l).equals(mCity)) {
                                spinner_city.setSelection(l);
                                break;
                            }
                        }
                }
            });
            BookingController.getInstance(getmController()).getgames(new SuccessCallback<List<Game>>() {
                @Override
                public void OnSuccess(List<Game> result) {
                    swipeContainer.setRefreshing(false);
                    progressBar_soccer.setVisibility(View.GONE);
                    ArrayAdapter<Game> gameAdapter = new GamesAdapter(getmContext(), R.layout.spinner_item, result);
                    spinner_game.setAdapter(gameAdapter);
                }
            });*/
        }

    }

    public void viewMyVouchers(final List<Voucher> vouchers) {
        findViewById(R.id.nearest_vouchers_panel).setVisibility(View.GONE);
        if (vouchers.size() != 0) {
            findViewById(R.id.nearest_vouchers_panel).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.nearest_vouchers_layout)).removeAllViews();
            for (int i = 0; i < vouchers.size(); i++) {
                TextView imageView = new TextView(this);
                TextView textView = new TextView(this);
//
                imageView.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
                layoutParams.gravity = Gravity.CENTER;
                imageView.setLayoutParams(layoutParams);
                imageView.setGravity(Gravity.CENTER);

                imageView.setBackground(getResources().getDrawable(R.drawable.circular_textview));
                if (vouchers.get(i).getType() == 2) {
                    String hDuration = (vouchers.get(i).getValue() / 60) < 10 ? "0" + vouchers.get(i).getValue() / 60 : vouchers.get(i).getValue() / 60 + "";
                    String mDuration = (vouchers.get(i).getValue() % 60) < 10 ? "0" + vouchers.get(i).getValue() % 60 : vouchers.get(i).getValue() % 60 + "";
                    imageView.setText(hDuration + ":" + mDuration + "\n" + getResources().getString(R.string.free));
                } else {
                    imageView.setText(vouchers.get(i).getValue() + "%");
                }
                textView.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams1);
                textView.setPadding(10, 10, 10, 10);
                textView.setGravity(Gravity.CENTER);
                textView.setText(daysLeft(vouchers.get(i).getExpiry_date()) + " " + getResources().getString(R.string.days_left)
                        + "\n" + vouchers.get(i).getCode());
                LinearLayout linearLayout = new LinearLayout(this);

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.setMargins(5, 5, 5, 5);
                linearLayout.setLayoutParams(layoutParams2);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
//            int t=linearLayout.getChildCount();
                linearLayout.addView(textView, 0);
                linearLayout.addView(imageView, 0);

                final int finalI = i;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, MyVouchersActivity.class);
                        intent.putExtra("voucher_id", vouchers.get(finalI).getId());
                        startActivity(intent);
                    }
                });

                ((LinearLayout) findViewById(R.id.nearest_vouchers_layout)).addView(
                        linearLayout, i);
            }
        }


    }

    public void viewOffers(final List<Offer> offers) {
        findViewById(R.id.offers_panel).setVisibility(View.GONE);
        LinearLayout offers_layout = ((LinearLayout) findViewById(R.id.offers_layout));
        offers_layout.removeAllViews();
        if (offers.size() != 0) {
            findViewById(R.id.offers_panel).setVisibility(View.VISIBLE);
//            String hDuration = (offers.get(0).getSet_of_minutes()/ 60) < 10 ? "0" + offers.get(0).getSet_of_minutes() / 60 : offers.get(0).getSet_of_minutes() / 60 + "";
//            String mDuration = (offers.get(0).getSet_of_minutes() % 60) < 10 ? "0" + offers.get(0).getSet_of_minutes() % 60 : offers.get(0).getSet_of_minutes() % 60 + "";
            Offer offer = offers.get(0);
            ((TextView) findViewById(R.id.offers_desc)).setText(offer.getDescription());
            int hoursCount = offer.getSet_of_minutes() / 60;
            int bookedHoursCount = offer.getBooked_hours() / 60;
            offers_layout.setWeightSum(hoursCount + 2);

            for (int i = 1; i <= hoursCount; i++) {
                TextView imageView = new TextView(this);
//                TextView textView = new TextView(this);
                imageView.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
                layoutParams.gravity = Gravity.CENTER;
                imageView.setPadding(5, 5, 5, 5);
                imageView.setLayoutParams(layoutParams);
                imageView.setGravity(Gravity.CENTER);
                if (bookedHoursCount >= i)
                    imageView.setBackground(getResources().getDrawable(R.drawable.booked_hours_textview_boarder));
                else
                    imageView.setBackground(getResources().getDrawable(R.drawable.offer_textview_boarder));
                imageView.setText(i /*+ 1*/ + "");
                LinearLayout line = new LinearLayout(this);

                LinearLayout.LayoutParams lineLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
//                lineLayoutParams.setMargins(5, 5, 5, 5);
//                lineLayoutParams.weight = 1;
                line.setLayoutParams(lineLayoutParams);
                line.setOrientation(LinearLayout.HORIZONTAL);
                line.setBackgroundColor(getResources().getColor(R.color.color_primary));
//                line.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(this);

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams2.weight = 1;
                linearLayout.setLayoutParams(layoutParams2);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.addView(imageView, 0);
                linearLayout.addView(line, 1);

               /* final int finalI = i;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, MyVouchersActivity.class);
                        intent.putExtra("voucher_id", offers.get(finalI).getId());
                        startActivity(intent);
                    }
                });*/

                offers_layout.addView(linearLayout);
            }
            //offer value
            TextView imageView = new TextView(this);
//            TextView textView = new TextView(this);
//
            imageView.setTextColor(getResources().getColor(R.color.color_primary));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
            layoutParams.gravity = Gravity.CENTER;
            imageView.setLayoutParams(layoutParams);
            imageView.setGravity(Gravity.CENTER);
            imageView.setBackground(getResources().getDrawable(R.drawable.offer_textview_boarder));
            if (offer.getType() == 2) {
                String hDuration = (offer.getValue() / 60) < 10 ? "0" + offer.getValue() / 60 : offer.getValue() / 60 + "";
                String mDuration = (offer.getValue() % 60) < 10 ? "0" + offer.getValue() % 60 : offer.getValue() % 60 + "";
                imageView.setText(hDuration + ":" + mDuration + "\n" + getResources().getString(R.string.free));
            } else {
                imageView.setText(offer.getValue() + "%");
            }
            LinearLayout linearLayout = new LinearLayout(this);

            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams2.setMargins(5, 5, 5, 5);
            layoutParams2.weight = 1;
            linearLayout.setLayoutParams(layoutParams2);
//            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
//            int t=linearLayout.getChildCount();
//            linearLayout.addView(textView, 0);
            linearLayout.addView(imageView, 0);
            offers_layout.addView(linearLayout);
         /*   LinearLayout layout = (LinearLayout) findViewById(R.id.line);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
            params.width = offers_layout.getWidth();
            layout.setLayoutParams(params);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int[] loc = new int[2];
            offers_layout.getLocationOnScreen(loc);
            int distance = dm.widthPixels - loc[0];
            distance = distance + 1;*/
           /* LinearLayout.LayoutParams layoutParamsline = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            findViewById(R.id.line).ma*/
        }
//        ((TextView) findViewById(R.id.offers_desc)).setText();
    }


}
