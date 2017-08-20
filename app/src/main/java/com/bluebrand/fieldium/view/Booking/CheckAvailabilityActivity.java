package com.bluebrand.fieldium.view.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.FieldsController;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.adapter.AvailableTimesAdapter;
import com.bluebrand.fieldium.view.other.DatePickerFragment;
import com.bluebrand.network.SuccessCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CheckAvailabilityActivity extends MasterActivity implements DatePickerFragment.OnDialogClosed {
    FieldiumApplication fieldiumApplication;
    Booking booking;
    ListView listView;
    //    ProgressBar progressBar;
    Button selectDate;
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_check_availability);
        super.onCreate(savedInstanceState);

        fieldiumApplication = (FieldiumApplication) getApplication();
        booking = fieldiumApplication.getBooking();
        setTitle(booking.getField().getName());
        date = booking.getDate();
        getData();

    }

    @Override
    protected void getData() {

        if (date == null || date.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Calendar cal = Calendar.getInstance();
            date = formatter.format(cal.getTime());
        }
        ((Button) findViewById(R.id.button_selectDate)).setText(date);
        showProgress(true);
//        progressBar.setVisibility(View.VISIBLE);
  /*      FieldsController.getInstance(getmController()).CheckAvailability(booking.getField().getId(), date,booking.getGame().getId(), new SuccessCallback<List<AvailableTime>>() {
            @Override
            public void OnSuccess(final List<AvailableTime> result) {
//                progressBar.setVisibility(View.GONE);
                booking.setAvailableTimes(result);
                showProgress(false);
                listView.setAdapter(new AvailableTimesAdapter(getmContext(), R.layout.available_time_item, result));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CheckAvailabilityActivity.this, BookingActivity.class);
                        booking.setDate(date);
                        booking.setStartTime(result.get(position).getStart());
                        booking.setDuration(String.valueOf(1));
                        ((FieldiumApplication) getApplication()).setBooking(booking);
                        intent.putExtra("booking", booking);
                        startActivity*//*ForResult*//*(intent*//*, 44*//*);

                    }
                });
            }
        });*/


    }

    @Override
    protected void showData() {


    }

    @Override
    public void assignUIRefrences() {
        listView = (ListView) findViewById(R.id.availableTimes_listView);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        selectDate = (Button) findViewById(R.id.button_selectDate);
        mProgressView = (View) findViewById(R.id.proccess_indicator);
        mFormView = (View) findViewById(R.id.form_container);
    }

    @Override
    protected void assignActions() {
        selectDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_selectDate) {
            showDatePickerDialog(/*v,*/ getSupportFragmentManager());
//            Intent intent = new Intent(this, TimingActivity.class);
//            intent.putExtra("just_calendar", true);
//            startActivityForResult(intent, 41);
        }

    }


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     *//*   if (requestCode == 41 && resultCode == RESULT_OK) {
            date = data.getExtras().getString("selected_date");
            getData();
        } else*//*
        if (requestCode == 44 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }*/

    @Override
    public void OnDialogClosed(/*int resId,*/ int year, int month, int day) {
        if (year == 0 && month == 0 && day == 0) {
        } else {
            listView.setVisibility(View.VISIBLE);
            date = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
//            date = year + "-" + (month + 1) + "-" + day;
           /* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                date=formatter.format(formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
//            date=(formatter.format(date1));
//            booking.setDate(date);
//            fieldiumApplication.setBooking(booking);
            getData();
        }

    }
    @Override
    public void onBackPressed() {
        ((FieldiumApplication)getApplication()).setBooking(new Booking());
        super.onBackPressed();
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }*/

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
