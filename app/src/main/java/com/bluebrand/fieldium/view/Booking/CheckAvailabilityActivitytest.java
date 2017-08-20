package com.bluebrand.fieldium.view.Booking;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.FieldsController;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.adapter.AvailableTimesAdapter;
import com.bluebrand.fieldium.view.other.DatePickerFragment;
import com.bluebrand.network.SuccessCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CheckAvailabilityActivitytest extends MasterActivity implements DatePickerFragment.OnDialogClosed, MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener
        , WeekView.EventClickListener {
    FieldiumApplication fieldiumApplication;
    Booking booking;
    ListView listView;
    //    ProgressBar progressBar;
    Button selectDate;
    String date = "";
    WeekView weekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       setLanguage();
        setContentView(R.layout.activity_check_availabilitytest);
        super.onCreate(savedInstanceState);

//        fieldiumApplication = (FieldiumApplication) getApplication();
//        booking = fieldiumApplication.getBooking();
        setTitle("check availability");

//        date = booking.getDate();
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
 /*       showProgress(true);
//        progressBar.setVisibility(View.VISIBLE);
        FieldsController.getInstance(getmController()).CheckAvailability(booking.getField().getId(), date, new SuccessCallback<List<AvailableTime>>() {
            @Override
            public void OnSuccess(final List<AvailableTime> result) {
//                progressBar.setVisibility(View.GONE);
                booking.setAvailableTimes(result);
                showProgress(false);
                listView.setAdapter(new AvailableTimesAdapter(getmContext(), R.layout.available_time_item, result));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CheckAvailabilityActivitytest.this, BookingActivity.class);
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
//        listView = (ListView) findViewById(R.id.availableTimes_listView);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        selectDate = (Button) findViewById(R.id.button_selectDate);
        mProgressView = (View) findViewById(R.id.proccess_indicator);
        mFormView = (View) findViewById(R.id.form_container);
        weekView = (WeekView) findViewById(R.id.weekView);
        weekView.setNumberOfVisibleDays(1);
        setupDateTimeInterpreter(false);
    }

    @Override
    protected void assignActions() {
        selectDate.setOnClickListener(this);
        weekView.setMonthChangeListener(this);
        weekView.setEmptyViewClickListener(this);
        weekView.setOnEventClickListener(this);
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

    private void setupDateTimeInterpreter(final boolean shortDate) {
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return /*weekday.toUpperCase() + format.format(date.getTime())*/"";
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? ((hour - 12) == 0 ? "12" : (hour - 12)) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
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
        Calendar c = Calendar.getInstance();
        if (year == 0 && month == 0 && day == 0) {
        } else {
//            listView.setVisibility(View.VISIBLE);
            date = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
//            onMonthChange()
//            date = year + "-" + (month + 1) + "-" + day;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
//                c = Calendar.getInstance();
                c.setTime(formatter.parse(date));
                date = formatter.format(formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            date=(formatter.format(date1));
//            booking.setDate(date);
//            fieldiumApplication.setBooking(booking);
//            getData();
//            onMonthChange(year,month);
            getData();
            weekView.goToDate(c);

        }

    }

    @Override
    public void onBackPressed() {
        ((FieldiumApplication) getApplication()).setBooking(new Booking());
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

    @Override
    public void onEmptyViewClicked(Calendar time) {


//        Toast.makeText(CheckAvailabilityActivitytest.this, "empty" + getEventTitle1(time), Toast.LENGTH_SHORT).show();

        Intent inte = new Intent(CheckAvailabilityActivitytest.this, TimingActivityHomeScreentest.class);
        inte.putExtra("selectedTime",time);
        startActivity(inte);
    }

    protected String getEventTitle(Calendar time) {
        return "Busy" /*String.format(*//*"Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH))*/;
    }
    protected String getEventTitle1(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.colorAccent_transparent));
        events.add(event);


        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);

        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 2);
        endTime.set(Calendar.MONTH, newMonth - 1);
        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.colorAccent_transparent));
        events.add(event);
        return events;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
//        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();

    }

}
