package com.bluebrand.fieldium.view.Booking;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.adapter.ExpandableListAdapter;
import com.bluebrand.network.SuccessCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BookingListActivity
        extends MasterActivity {

    List<Booking> bookings = new ArrayList<>();
    HashMap<String, List<Booking>> bookings_map = new HashMap<>();
    List<Booking> upComingBookings = new ArrayList<>();
    List<Booking> pastBookings = new ArrayList<>();
    TextView noBookings;
    boolean redirectToHomeScreen = false;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_booking_list);
        super.onCreate(savedInstanceState);
        setTitle(getTitle());
        customStyle();
        getData();
    }

    @Override
    protected void getData() {
        redirectToHomeScreen = getIntent().getBooleanExtra("redirectToHomeScreen", false);
        noBookings.setVisibility(View.GONE);
        showProgress(true);
        bookings = new ArrayList<>();
        upComingBookings = new ArrayList<>();
        pastBookings = new ArrayList<>();
        BookingController.getInstance(getmController()).getMyBookings(new SuccessCallback<List<Booking>>() {
            @Override
            public void OnSuccess(List<Booking> result) {

                bookings = result;
                showProgress(false);

                if (bookings.size() == 0) {
                    noBookings.setVisibility(View.VISIBLE);
                } else showData();

            }
        });

    }

    @Override
    protected void showData() {
        if (getIntent().getBooleanExtra("show_no_token_problem", false))
            showNoTokenDialog();
        for (int i = 0; i < bookings.size(); i++) {
            if (checkDate(bookings.get(i).getDate(), bookings.get(i).getStartTime())) {
                upComingBookings.add(bookings.get(i));
            } else pastBookings.add(bookings.get(i));
        }
        ArrayList<String> parent_list = new ArrayList<>();
        parent_list.add(getResources().getString(R.string.upcoming_bookings));
        parent_list.add(getResources().getString(R.string.previous_bookings));
        bookings_map.put(parent_list.get(0), upComingBookings);
        bookings_map.put(parent_list.get(1), pastBookings);

        ExpandableListView list = (ExpandableListView) findViewById(R.id.booking_list);

        list.setAdapter(new ExpandableListAdapter(getmContext(), parent_list, bookings_map));
        list.expandGroup(0);
        list.expandGroup(1);
    }

    @Override
    public void assignUIRefrences() {
        mProgressView = findViewById(R.id.proccess_indicator);
        mFormView = findViewById(R.id.form_container);
        noBookings = (TextView) findViewById(R.id.noBookings);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    protected void assignActions() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                RefreshBookings();

            }
        });
    }

    public void RefreshBookings() {
        bookings = new ArrayList<>();
        upComingBookings = new ArrayList<>();
        pastBookings = new ArrayList<>();

        BookingController.getInstance(getmController()).getMyBookings(new SuccessCallback<List<Booking>>() {
            @Override
            public void OnSuccess(List<Booking> result) {
                bookings = result;
                swipeContainer.setRefreshing(false);
                if (snackbar != null)
                    snackbar.dismiss();

                if (bookings.size() == 0) {
                    noBookings.setVisibility(View.VISIBLE);
                } else showData();
            }
        });
    }

    public void customStyle() {
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onClick(View v) {

    }

    public boolean checkDate(String bookingDate, String bookingStartTime) {
        String bookingTime = bookingDate + " " + bookingStartTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.ENGLISH);
        try {
            Date booking_date = formatter.parse(bookingTime);
            Calendar cal = Calendar.getInstance();
            Date current_date = formatter.parse(formatter.format(cal.getTime()));

            if (booking_date.before(current_date))
                return false;
            else return true;
        } catch (ParseException e) {
            e.printStackTrace();
//            Log.e("ttetetete",e.getMessage());
//            Toast.makeText(BookingListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public void showNoTokenDialog() {
        final Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.no_token_dialog);
        myDialog.setCancelable(false);
        Button Ok = (Button) myDialog.findViewById(R.id.Ok);

        Ok.setOnClickListener(new View.OnClickListener() {
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
    public void onBackPressed() {
        if (redirectToHomeScreen) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
