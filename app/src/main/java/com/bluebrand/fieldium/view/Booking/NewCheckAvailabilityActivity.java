package com.bluebrand.fieldium.view.Booking;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alamkanak.weekview.*;
import com.alamkanak.weekview.WeekView;
import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.FieldsController;
import com.bluebrand.fieldium.core.model.Availability;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.adapter.AvailableTimesAdapter;
import com.bluebrand.fieldium.view.other.DatePickerFragment;
import com.bluebrand.network.SuccessCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewCheckAvailabilityActivity extends MasterActivity implements DatePickerFragment.OnDialogClosed, MonthLoader.MonthChangeListener, com.alamkanak.weekview.WeekView.EmptyViewClickListener
        , WeekView.EventClickListener {
    FieldiumApplication fieldiumApplication;
    Booking booking;
    //    ListView listView;
    //    ProgressBar progressBar;
    Button selectDate;
    String date = "";
    WeekView weekView;
    Availability availability;
    boolean callMonthChanged = false;
    List<WeekViewEvent> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       setLanguage();
        setContentView(R.layout.activity_check_availabilitytest);
        super.onCreate(savedInstanceState);

        fieldiumApplication = (FieldiumApplication) getApplication();
        booking = fieldiumApplication.getBooking();
        setTitle(getResources().getString(R.string.available_times));
        date = booking.getDate();
        mEvents = new ArrayList<>();
        getData();

    }

    @Override
    protected void getData() {
        final Calendar cal = Calendar.getInstance();
        if (date == null || date.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date = formatter.format(cal.getTime());
        }
        ((Button) findViewById(R.id.button_selectDate)).setText(date);
        showProgress(true);
        FieldsController.getInstance(getmController()).CheckAvailability(booking.getField().getId(), date, booking.getGame().getId(), new SuccessCallback<Availability>() {
            @Override
            public void OnSuccess(final Availability result) {
                availability = result;
                booking.setAvailableTimes(availability.getAvailableTimes());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    cal.setTime(formatter.parse(date));
//                    date = formatter.format(formatter.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                callMonthChanged = true;
                setEventsList();
                weekView.goToDate(cal);

                gotoHour();
//                weekView.notifyDatasetChanged();
//                booking.setAvailableTimes(result.getAvailableTimes());
                showProgress(false);

            }
        });


    }

    public void gotoHour() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat format = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        Date currentTime = c.getTime();
        Date currentDate = c.getTime();
        Date openTime = null;
        Date selectedDate = null;
        try {
            currentDate = dateFormat.parse(dateFormat.format(c.getTime()));
            selectedDate = dateFormat.parse(date);
            currentTime = format.parse(format.format(c.getTime()));
            openTime = format.parse(/*format.format(format1.parse(*/booking.getField().getOpenTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//                c.setTime(currentTime);
        if (currentDate.compareTo(selectedDate) == 0)
            weekView.goToHour(currentTime.getHours());
        else {
            weekView.goToHour(openTime.getHours());
        }
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


    @Override
    public void OnDialogClosed(/*int resId,*/ int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        if ((year == 0 && month == 0 && day == 0) || (year == -1 && month == -1 && day == -1)) {
        } else {
//            listView.setVisibility(View.VISIBLE);
            date = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
//            date = year + "-" + (month + 1) + "-" + day;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                c.setTime(formatter.parse(date));
                date = formatter.format(formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            weekView.goToDate(c);
            getData();
        }

    }

    @Override
    public void onBackPressed() {
        Booking booking = new Booking();
        int gameId = fieldiumApplication.getGame_id();
        if (gameId != 0) {
            Game game = fieldiumApplication.getBooking().getGame();
            booking.setGame(game);
        }

        fieldiumApplication.setBooking(booking);
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

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        if (callMonthChanged) {
            callMonthChanged = false;
            return mEvents;
        } else
            return new ArrayList<>();
    }

    protected String getEventTitle(Calendar time) {
        return getResources().getString(R.string.busy);
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
//        time.set(Calendar.MINUTE, 0);
        int unroundedMinutes = time.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 15;
        time.add(Calendar.MINUTE, mod < 8 ? -mod : (15 - mod));
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        AvailableTime av = checkTimeinWhichRange(formatter.format(time.getTime()));
        if (av != null) {
            booking.setDate(date);
            fieldiumApplication.setBooking(booking);
            Intent intent = new Intent(NewCheckAvailabilityActivity.this, NewTimingActivity.class);
            intent.putExtra("selectedTime", time);
            intent.putExtra("selected_range", av);
            startActivity(intent);

        } else {
            Toast.makeText(fieldiumApplication, getResources().getString(R.string.not_allowed), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.ENGLISH);

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

    public void setEventsList() {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        for (int i = 0; i < availability.getBusyTimes().size(); i++) {
            AvailableTime busy = availability.getBusyTimes().get(i);
            String startBusy = busy.getStart();
            String endBusy = busy.getEnd();
            Calendar eventStartTime = Calendar.getInstance();
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            Calendar dateCal = Calendar.getInstance();
            try {
                startCal.setTime(formatter.parse(startBusy));
                endCal.setTime(formatter.parse(endBusy));
                dateCal.setTime(formatter1.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            eventStartTime.set(Calendar.HOUR_OF_DAY, startCal.get(Calendar.HOUR_OF_DAY));
            eventStartTime.set(Calendar.MINUTE, startCal.get(Calendar.MINUTE));
            eventStartTime.set(Calendar.DAY_OF_MONTH, dateCal.get(Calendar.DAY_OF_MONTH));
            eventStartTime.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
            eventStartTime.set(Calendar.YEAR, dateCal.get(Calendar.YEAR));

            Calendar eventEndTime = (Calendar) eventStartTime.clone();
            eventEndTime.add(Calendar.MINUTE, calculateTimesDifference(startBusy, endBusy));
            eventEndTime.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
            WeekViewEvent event = new WeekViewEvent(i, getResources().getString(R.string.busy), eventStartTime, eventEndTime);
            event.setColor(getResources().getColor(R.color.colorAccent_transparent));
            events.add(event);

        }
//        weekView.notifyDatasetChanged();
        mEvents = events;
        mEvents.addAll(addClosedEvent());
        weekView.notifyDatasetChanged();

    }

    public List<WeekViewEvent> addClosedEvent() {
        List<WeekViewEvent> closedEvents = new ArrayList<WeekViewEvent>();

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String midnight = "12:00 AM";

        try {
            Calendar startC = Calendar.getInstance();

            Calendar endC = Calendar.getInstance();
            Date openDate = formatter1.parse(booking.getField().getOpenTime());


            Date closeDate = formatter1.parse(booking.getField().getCloseTime());
            String openTime = formatter.format(openDate);
            String closeTime = formatter.format(closeDate);
            startC.setTime(openDate);
            endC.setTime(closeDate);
            if (booking.getField().getOpenTime().equals("00:00:00"))
                startC.add(Calendar.DATE,1);
            if (endC.before(startC)) {
                Calendar eventStartTime = Calendar.getInstance();
                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                Calendar dateCal = Calendar.getInstance();

                startCal.setTime(formatter.parse(closeTime));
                endCal.setTime(formatter.parse(openTime));
                dateCal.setTime(formatter2.parse(date));
                eventStartTime.set(Calendar.HOUR_OF_DAY, startCal.get(Calendar.HOUR_OF_DAY));
                eventStartTime.set(Calendar.MINUTE, startCal.get(Calendar.MINUTE) + 1);
                eventStartTime.set(Calendar.DAY_OF_MONTH, dateCal.get(Calendar.DAY_OF_MONTH));
                eventStartTime.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
                eventStartTime.set(Calendar.YEAR, dateCal.get(Calendar.YEAR));

                Calendar eventEndTime = (Calendar) eventStartTime.clone();
                eventEndTime.add(Calendar.MINUTE, (calculateTimesDifference(closeTime, openTime) - 2));
                eventEndTime.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
                WeekViewEvent event = new WeekViewEvent(0, getResources().getString(R.string.closed), eventStartTime, eventEndTime);
                event.setColor(getResources().getColor(R.color.dialog_background));
                closedEvents.add(event);
            }
        /*   */
            else {
                Calendar eventStartTime = Calendar.getInstance();
                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                Calendar dateCal = Calendar.getInstance();

                startCal.setTime(formatter.parse(midnight));
                endCal.setTime(formatter.parse(openTime));
                dateCal.setTime(formatter2.parse(date));
                eventStartTime.set(Calendar.HOUR_OF_DAY, startCal.get(Calendar.HOUR_OF_DAY));
                eventStartTime.set(Calendar.MINUTE, startCal.get(Calendar.MINUTE));
                eventStartTime.set(Calendar.DAY_OF_MONTH, dateCal.get(Calendar.DAY_OF_MONTH));
                eventStartTime.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
                eventStartTime.set(Calendar.YEAR, dateCal.get(Calendar.YEAR));

                Calendar eventEndTime = (Calendar) eventStartTime.clone();
                eventEndTime.add(Calendar.MINUTE, calculateTimesDifference(midnight, openTime) - 1);
                eventEndTime.set(Calendar.DAY_OF_MONTH, dateCal.get(Calendar.DAY_OF_MONTH));
                eventEndTime.set(Calendar.MONTH, dateCal.get(Calendar.MONTH));
                eventEndTime.set(Calendar.YEAR, dateCal.get(Calendar.YEAR));
                WeekViewEvent event = new WeekViewEvent(0, getResources().getString(R.string.closed), eventStartTime, eventEndTime);
                event.setColor(getResources().getColor(R.color.dialog_background));
                closedEvents.add(event);


                //
                Calendar eventStartTime1 = Calendar.getInstance();
                Calendar startCal1 = Calendar.getInstance();
                Calendar endCal1 = Calendar.getInstance();
                Calendar dateCal1 = Calendar.getInstance();

                startCal1.setTime(formatter.parse(closeTime));
                endCal1.setTime(formatter.parse(midnight));
                dateCal1.setTime(formatter2.parse(date));
                eventStartTime1.set(Calendar.HOUR_OF_DAY, startCal1.get(Calendar.HOUR_OF_DAY));
                eventStartTime1.set(Calendar.MINUTE, startCal1.get(Calendar.MINUTE));
                eventStartTime1.set(Calendar.DAY_OF_MONTH, dateCal1.get(Calendar.DAY_OF_MONTH));
                eventStartTime1.set(Calendar.MONTH, dateCal1.get(Calendar.MONTH));
                eventStartTime1.set(Calendar.YEAR, dateCal1.get(Calendar.YEAR));

                Calendar eventEndTime1 = (Calendar) eventStartTime1.clone();
                eventEndTime1.add(Calendar.MINUTE, calculateTimesDifference(closeTime, midnight) - 1);
                eventEndTime1.set(Calendar.DAY_OF_MONTH, dateCal1.get(Calendar.DAY_OF_MONTH));
                eventEndTime1.set(Calendar.MONTH, dateCal1.get(Calendar.MONTH));
                eventEndTime1.set(Calendar.YEAR, dateCal1.get(Calendar.YEAR));
                WeekViewEvent event1 = new WeekViewEvent(0, getResources().getString(R.string.closed), eventStartTime1, eventEndTime1);
                event1.setColor(getResources().getColor(R.color.dialog_background));
                closedEvents.add(event1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return closedEvents;
    }

    public int calculateTimesDifference(String start, String end) {
        Date time1 = null;
        Date time2 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        try {
            time2 = sdf.parse(end);
//            time1 = sdf.parse(sdf.format(sdf.parse(start)));
            time1 = sdf.parse(start);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);

        if (time2.before(time1)) {
            calendar2.add(Calendar.DATE, 1);
        }
        if (calendar2.get(Calendar.MINUTE) == 59)
            calendar2.add(Calendar.MINUTE, 1);
        long difference = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        min = (min < 0 ? -min : min);
        return hours * 60 + min;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }


    public AvailableTime checkTimeinWhichRange(String selectedTime) {
        for (int i = 0; i < booking.getAvailableTimes().size(); i++) {
            AvailableTime availableTime = booking.getAvailableTimes().get(i);
            if (checkSelectedTimeRange(selectedTime, availableTime.getStart(), availableTime.getEnd())) {
                return availableTime;
            }
        }
        return null;
    }
}
