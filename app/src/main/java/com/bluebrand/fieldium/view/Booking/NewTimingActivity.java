package com.bluebrand.fieldium.view.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.MasterActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.bluebrand.fieldium.R.string.to;

public class NewTimingActivity extends MasterActivity implements WheelPicker.OnItemSelectedListener {
    List<String> durations;
    List<String> startTimes;
    Spinner durations_spinner;
    Spinner startTime_spinner;
    Booking booking;
    //    Button cancel_button, ok_button;
    FieldiumApplication fieldiumApplication;
    private int TIME_PICKER_INTERVAL = 1;
    NumberPicker minutePicker;
    List<String> displayedValues;
    //    CalendarView calendarView;
    HashMap<Integer, String> duration_map;
    TimePicker pickStartTime, durationPicker;
    String text = "";
    Calendar selectedTime;
    //    boolean justCalendar = false;
    WheelPicker timeMinutePicker, timeHourPicker, amPmPicker, durationHourPicker, durationMinutePicker;
    List<String> minutesList;
    List<String> amPmList;
    List<String> hoursList;
    List<Integer> hourintList;
    List<String> durationHoursList;
    List<String> durationMinutesList;
    List<String> durationMinutesListwithout0;
    AvailableTime selectedRange;
    Game selected_game;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       setLanguage();
        setContentView(R.layout.activity_timing_new);
        super.onCreate(savedInstanceState);

        setTitle(getResources().getString(R.string.timing));
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        fieldiumApplication = ((FieldiumApplication) getApplication());
        booking = ((FieldiumApplication) getApplication()).getBooking();
        selected_game = booking.getGame();


        getStartTimes();


    }

    @Override
    protected void getData() {
        //am_pm_wheel
        amPmList = new ArrayList<>();
        amPmList.add("AM");
        amPmList.add("PM");
        amPmPicker.setData(amPmList);


        timeHourPicker.setData(hoursList);

        //duration hours wheel
        durationHoursList = new ArrayList<>();
        durationHoursList.addAll(calculateDurations(selectedRange.getStart(), selectedRange.getEnd()));
        durationHourPicker.setData(durationHoursList);

        //duration minute wheel
        durationMinutesList = new ArrayList<>();
        // minutes wheel
        minutesList = new ArrayList<>();
        int increament_factor = selected_game.getIncreament_factor();
        for (int i = 0; i < 60; i += increament_factor) {
            durationMinutesList.add(i < 10 ? "0" + i : i + "");
            minutesList.add(i < 10 ? "0" + i : i + "");
        }

        durationMinutePicker.setData(durationMinutesList);
        timeMinutePicker.setData(minutesList);

        initialWheels();
    }


    @Override
    protected void showData() {}

    @Override
    public void assignUIRefrences() {
        submit_button = (Button) findViewById(R.id.button_submit);
        timeMinutePicker = (WheelPicker) findViewById(R.id.main_wheel_minute);
        amPmPicker = (WheelPicker) findViewById(R.id.main_wheel_ampm);
        timeHourPicker = (WheelPicker) findViewById(R.id.main_wheel_hour);
        durationHourPicker = (WheelPicker) findViewById(R.id.main_wheel_durationHour);
        durationMinutePicker = (WheelPicker) findViewById(R.id.main_wheel_durationMinute);
        //wheels settings
        durationHourPicker.setVisibleItemCount(3);
        durationMinutePicker.setVisibleItemCount(3);
        timeHourPicker.setVisibleItemCount(3);
        timeMinutePicker.setVisibleItemCount(3);
        amPmPicker.setVisibleItemCount(2);
//        amPmPicker.setIndicator(false);
//        amPmPicker.setIndicatorColor(R.color.dark_gray);
//        durationHourPicker.setIndicator(false);
//        durationHourPicker.setIndicatorColor(R.color.dark_gray);
//        durationMinutePicker.setIndicator(false);
//        durationMinutePicker.setIndicatorColor(R.color.dark_gray);
//        timeHourPicker.setIndicator(false);
//        timeHourPicker.setIndicatorColor(R.color.dark_gray);
//        timeMinutePicker.setIndicator(false);
//        timeMinutePicker.setIndicatorColor(R.color.dark_gray);
        //
//        cancel_button = (Button) findViewById(R.id.cancel_button);
//        ok_button = (Button) findViewById(R.id.ok_button);

    }

    @Override
    protected void assignActions() {
        timeMinutePicker.setOnItemSelectedListener(this);
        timeHourPicker.setOnItemSelectedListener(this);
        durationHourPicker.setOnItemSelectedListener(this);
        durationMinutePicker.setOnItemSelectedListener(this);
        amPmPicker.setOnItemSelectedListener(this);
        submit_button.setOnClickListener(this);
//        cancel_button.setOnClickListener(this);
//        ok_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                try {
                    String startTime = String.valueOf(timeHourPicker.getData().get(timeHourPicker.getCurrentItemPosition())) +
                            ":" + String.valueOf(timeMinutePicker.getData().get(timeMinutePicker.getCurrentItemPosition())) +
                            " " + String.valueOf(amPmPicker.getData().get(amPmPicker.getCurrentItemPosition()));

                    int durationHour = Integer.parseInt(String.valueOf(durationHourPicker.getData().get(durationHourPicker.getCurrentItemPosition())));
                    int durationMinute = Integer.parseInt(String.valueOf(durationMinutePicker.getData().get(durationMinutePicker.getCurrentItemPosition())));
                    int duration = durationHour * 60 + durationMinute;
                    Date rangeEnd = calculateRangeEnd(startTime, duration);
                    if (duration < selected_game.getMinimum_duration())
                        throw new InvalidInputException(getResources().getString(R.string.minimum_duration)+" " + selected_game.getMinimum_duration() + " "+getResources().getString(R.string.min));

                    if (checkSelectedTimeRange(startTime, selectedRange.getStart(), selectedRange.getEnd())
                            && checkSelectedEndTimeRange(rangeEnd, selectedRange.getStart(), selectedRange.getEnd())) {

                        booking.setStartTime(startTime);
                        booking.setDuration(String.valueOf(duration));
                        fieldiumApplication.setBooking(booking);
                        Intent intent = new Intent(NewTimingActivity.this, BookingActivity.class);
                        startActivity(intent);
                    }

                } catch (InvalidInputException e) {
                    Toast.makeText(fieldiumApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    public List<String> calculateDurations(String start, String end) {
        Date time1 = null;
        Date time2 = null;
        List<String> durations = new ArrayList<>();
        try {
            time1 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).parse(start);
            time2 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);

//        if (time2.before(time1)) {
//            calendar2.add(Calendar.DATE, 1);
//        }
        if (calendar2.get(Calendar.MINUTE) == 59)
            calendar2.add(Calendar.MINUTE, 1);


        long difference = calendar2.getTimeInMillis()-calendar1.getTimeInMillis() ;
        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        if(calendar1.get(Calendar.AM_PM)==calendar2.get(Calendar.AM_PM)
                &&calendar1.get(Calendar.HOUR)==calendar2.get(Calendar.HOUR)
                &&calendar1.get(Calendar.MINUTE)==calendar2.get(Calendar.MINUTE)
                &&hours==0)
            hours=24;
        hours = (hours < 0 ? -hours : hours);
        int j = 0;
        if (booking.getGame().getIncreament_factor() == 60 || booking.getGame().getIncreament_factor() % 60 == 0)
            j = 1;
        for (int i = j; i <= hours; i++)
            durations.add(i < 10 ? "0" + i : String.valueOf(i));
        return durations;
    }



    public void initialWheels() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);

//        String startTime = sdf.format(selectedTime.getTime());
        int duration = booking.getGame().getMinimum_duration();
//        String endTime = calculateRangeEnd(startTime, duration);
        Calendar selectedTime_calendar = selectedTime;
//initial hours
        int hPos = 0;
        for (int i = 0; i < hoursList.size(); i++) {
            int h = selectedTime_calendar.get(Calendar.HOUR);
            int amPm = selectedTime_calendar.get(Calendar.AM_PM);
            if (h == 0 && amPm == 1)
                h = 12;
            String selectedHour = h < 10 ? "0" + h : h + "";
            if (hoursList.get(i).equals(selectedHour)) {
                hPos = i;
                break;
            }
        }
        timeHourPicker.setSelectedItemPosition(hPos);

//initial minutes
        int mPos = 0;
        for (int i = 0; i < minutesList.size(); i++) {
            int m = selectedTime_calendar.get(Calendar.MINUTE);
            String selectedMinute = m < 10 ? "0" + m : m + "";
            if (minutesList.get(i).equals(selectedMinute)) {
                mPos = i;
                break;
            }
        }
        timeMinutePicker.setSelectedItemPosition(mPos);
//initial amPm
        amPmPicker.setSelectedItemPosition(selectedTime_calendar.get(Calendar.AM_PM));

        // initial duration
        int hDurationPos = 0;
        int h = duration / 60;
        for (int i = 0; i < durationHoursList.size(); i++) {

            String selectedHour = h < 10 ? "0" + h : h + "";
            if (durationHoursList.get(i).equals(selectedHour)) {
                hDurationPos = i;
                if (durationHoursList.get(i).equals("00")) {
                    if (durationMinutesList.contains("00")) {
                        durationMinutesList.remove("00");
                        durationMinutePicker.setData(durationMinutesList);
                    }
                }
                break;
            }
        }
        durationHourPicker.setSelectedItemPosition(hDurationPos);

        int mDurationPos = 0;
        int m = duration % 60;
        for (int i = 0; i < durationMinutesList.size(); i++) {

            String selectedMinutes = m < 10 ? "0" + m : m + "";
            if (durationMinutesList.get(i).equals(selectedMinutes)) {
                mDurationPos = i;
                break;
            }
        }
        durationMinutePicker.setSelectedItemPosition(mDurationPos);
        runTimeCheck();

    }


    public Date calculateRangeEnd(String start, int duration) {
        Date startTime = null;
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        try {
            startTime = dateFormat.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);

        startCalendar.add(Calendar.MINUTE, duration);
        String end = dateFormat.format(startCalendar.getTime());
        if (end.equals("12:00 AM")) {
            startCalendar.setTime(startTime);
            startCalendar.add(Calendar.MINUTE, duration - 1);
        }
        return startCalendar.getTime();
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

    public boolean checkSelectedTimeRange(String selectedTime, String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);

        try {
            Date time1 = dateFormat.parse(dateFormat.format(dateFormat1.parse(start)));
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = dateFormat.parse(dateFormat.format(dateFormat1.parse(end)));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            Date d = dateFormat.parse(dateFormat.format(dateFormat1.parse(selectedTime)));
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
//            if (calendar2.get(Calendar.MINUTE) == 59) {
//                calendar3.set(Calendar.MINUTE, calendar3.get(Calendar.MINUTE)-1);
//
////                time2 = dateFormat.parse(dateFormat.format(calendar2.getTime()));
////                calendar2.setTime(time2);
//            }


            Date selected = calendar3.getTime();
            Date _start = calendar1.getTime();
            Date _end = calendar2.getTime();

//        Processing -----> selected.compareTo(_end) == 0) by checkCompare0(calendar3,calendar2))


            if ((selected.after(_start) || selected.compareTo(_start) == 0) &&
                    (selected.before(_end) || /*checkCompare0(calendar3,calendar2)*/selected.compareTo(_end) == 0)) {
                return true;
            } else
                return false;
        } catch (ParseException e) {
//            Toast.makeText(fieldiumApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkSelectedEndTimeRange(Date selectedTime, String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);

        try {
            Date _start = dateFormat.parse(dateFormat.format(dateFormat1.parse(start)));
          /*  Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);*/

            Date _end = dateFormat.parse(dateFormat.format(dateFormat1.parse(end)));
           /* Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);*/
            Date selected =selectedTime; /*dateFormat.parse(dateFormat.format(dateFormat1.parse(selectedTime)));*/
           /* Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
*/
        /*    Date selected = calendar3.getTime();
            Date _start = calendar1.getTime();
            Date _end = calendar2.getTime();*/

            if ((selected.after(_start) || selected.compareTo(_start) == 0) &&
                    (selected.before(_end) || selected.compareTo(_end) == 0)) {
                return true;
            } else
                return false;
        } catch (ParseException e) {
//            Toast.makeText(fieldiumApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    boolean checkCompare0(Calendar selected, Calendar end) {
        if (selected.get(Calendar.MINUTE) == end.get(Calendar.MINUTE) &&
                selected.get(Calendar.HOUR) == end.get(Calendar.HOUR) &&
                selected.get(Calendar.AM_PM) == end.get(Calendar.AM_PM))
            return true;
        else return false;
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {

        switch (picker.getId()) {
            case R.id.main_wheel_hour:
                picker.setSelectedItemPosition(position);
                runTimeCheck();
                break;
            case R.id.main_wheel_minute:
                picker.setSelectedItemPosition(position);

                runTimeCheck();
                break;
            case R.id.main_wheel_ampm:
                picker.setSelectedItemPosition(position);
                runTimeCheck();
                break;
            case R.id.main_wheel_durationHour:
                if (String.valueOf(data).equals("00")) {
                    if (durationMinutesList.contains("00"))
                        durationMinutesList.remove("00");
                } else {
                    if (!durationMinutesList.contains("00"))
                        durationMinutesList.add("00");
                }
                durationMinutePicker.setData(durationMinutesList);
                durationMinutePicker.setSelectedItemPosition(0);
                picker.setSelectedItemPosition(position);
                runTimeCheck();
                break;
            case R.id.main_wheel_durationMinute:
                picker.setSelectedItemPosition(position);
                runTimeCheck();
                break;
        }

    }

    public void runTimeCheck() {

        String startTime = String.valueOf(timeHourPicker.getData().get(timeHourPicker.getCurrentItemPosition())) +
                ":" + String.valueOf(timeMinutePicker.getData().get(timeMinutePicker.getCurrentItemPosition())) +
                " " + String.valueOf(amPmPicker.getData().get(amPmPicker.getCurrentItemPosition()));

        int durationHour = Integer.parseInt(String.valueOf(durationHourPicker.getData().get(durationHourPicker.getCurrentItemPosition())));
//        Log.e("sizzzzzzzzzzze", String.valueOf(durationMinutePicker.getData().size()));
//        Log.e("indeeeeeeeeeeex", String.valueOf(durationMinutePicker.getCurrentItemPosition()));
        int durationMinute = Integer.parseInt(String.valueOf(durationMinutePicker.getData().get(durationMinutePicker.getCurrentItemPosition())));
        int duration = durationHour * 60 + durationMinute;
        Date rangeEnd = calculateRangeEnd(startTime, duration);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
       /* Date range_end = null;
        try {
            range_end = formatter.parse(rangeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Calendar rangeEnd_calendar = Calendar.getInstance();
        rangeEnd_calendar.setTime(rangeEnd);
        if (rangeEnd_calendar.get(Calendar.MINUTE) == 59)
            rangeEnd_calendar.add(Calendar.MINUTE, 1);
        ((TextView) findViewById(R.id.your_booking_range_title)).setText(getResources().getString(R.string.your_booking_on)+" " + booking.getDate() + "\n"+getResources().getString(R.string.from)+" " + startTime + " "+getResources().getString(R.string.to)+" " + formatter.format(rangeEnd_calendar.getTime()));
        if (checkSelectedTimeRange(startTime, selectedRange.getStart(), selectedRange.getEnd()) &&
                checkSelectedEndTimeRange(rangeEnd, selectedRange.getStart(), selectedRange.getEnd())) {

            submit_button.setBackgroundColor(getResources().getColor(R.color.color_primary));
            submit_button.setText(getResources().getString(R.string.continue_));
            submit_button.setEnabled(true);

        } else {
            submit_button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            submit_button.setText(getResources().getString(R.string.out_of_range));
            submit_button.setEnabled(false);

        }

    }

    public void getStartTimes() {
        //need to check getExtras==null?
        selectedTime = (Calendar) getIntent().getExtras().get("selectedTime");
        selectedRange = (AvailableTime) getIntent().getExtras().get("selected_range");
//        selectedTime.set(Calendar.MINUTE, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
//        startTimes = new ArrayList<>();
        hoursList = new ArrayList<>();

        try {
            Date start_time = formatter.parse(selectedRange.getStart());
            Date end_time = formatter.parse(selectedRange.getEnd());
            Date end_time1 = formatter.parse(selectedRange.getEnd());
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(start_time);
//                cal.set(Calendar.MINUTE, 0);
            Calendar endCal1 = Calendar.getInstance();
            endCal1.setTime(end_time1);

            if (endCal1.get(Calendar.MINUTE) == 59) {
                endCal1.add(Calendar.MINUTE, 1);
                end_time1 = formatter.parse(formatter.format(endCal1.getTime()));
            }
            ((TextView) findViewById(R.id.time_range_title)).setText(getResources().getString(R.string.available_range_is_from)+" " + selectedRange.getStart() + "  "+
                    getResources().getString(to)+" " + /*av.getEnd()*/formatter.format(endCal1.getTime()) + "\n\n" + getResources().getString(R.string.minimum_duration_of)+" " +
                    booking.getGame().getName() + " "+getResources().getString(R.string.is)+" " + booking.getGame().getMinimum_duration() + " "+getResources().getString(R.string.min));


            while (start_time.compareTo(end_time) != 0) {
                String hour = startCal.get(Calendar.HOUR) < 10 ? "0" + String.valueOf(startCal.get(Calendar.HOUR)) : String.valueOf(startCal.get(Calendar.HOUR));
                if (hour.equals("00"))
                    hour = "12";
                if (!hoursList.contains(hour)) {
                    hoursList.add(hour);
                }

                startCal.add(Calendar.MINUTE, 15);
                start_time = formatter.parse(formatter.format(startCal.getTime()));
                if (start_time.compareTo(end_time1) == 0)
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        getData();
    }


}
