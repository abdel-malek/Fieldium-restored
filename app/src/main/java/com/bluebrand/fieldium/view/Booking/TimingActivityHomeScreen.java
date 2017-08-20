package com.bluebrand.fieldium.view.Booking;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
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

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TimingActivityHomeScreen extends MasterActivity implements WheelPicker.OnItemSelectedListener {
    List<String> durations;
    List<String> startTimes;
    Spinner durations_spinner;
    Spinner startTime_spinner;
    Booking booking;
    Button cancel_button, ok_button;
    FieldiumApplication fieldiumApplication;
    private int TIME_PICKER_INTERVAL = 1;
    NumberPicker minutePicker;
    List<String> displayedValues;
    //    CalendarView calendarView;
    HashMap<Integer, String> duration_map;
    TimePicker pickStartTime, durationPicker;
    String text = "";

    //    boolean justCalendar = false;
    WheelPicker timeMinutePicker, timeHourPicker, amPmPicker, durationHourPicker, durationMinutePicker;
    List<String> minutesList;
    List<String> amPmList;
    List<String> hoursList;
    List<String> durationHoursList;
    List<String> durationMinutesList;
    List<String> durationMinutesListwithout0;
    Game selected_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_timing_home_screen);
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        fieldiumApplication = ((FieldiumApplication) getApplication());
        booking = ((FieldiumApplication) getApplication()).getBooking();
        selected_game = booking.getGame();


        getData();
    }

    @Override
    protected void getData() {
        durations = new ArrayList<>();
        startTimes = new ArrayList<>();
        //am_pm_wheel
        amPmList = new ArrayList<>();
        amPmList.add("AM");
        amPmList.add("PM");
        amPmPicker.setData(amPmList);

        // minutes wheel
        minutesList = new ArrayList<>();
//
//        minutesList.add("00");
//        minutesList.add("15");
//        minutesList.add("30");
//        minutesList.add("45");
//        timeMinutePicker.setData(minutesList);

        //hours wheel
        hoursList = new ArrayList<>();
        hoursList.add("01");
        hoursList.add("02");
        hoursList.add("03");
        hoursList.add("04");
        hoursList.add("05");
        hoursList.add("06");
        hoursList.add("07");
        hoursList.add("08");
        hoursList.add("09");
        hoursList.add("10");
        hoursList.add("11");
        hoursList.add("12");
        timeHourPicker.setData(hoursList);

        //duration hours wheel
        durationHoursList = new ArrayList<>();
        if (booking.getGame().getIncreament_factor() != 60 && booking.getGame().getIncreament_factor() % 60 != 0)
            durationHoursList.add("00");
        durationHoursList.addAll(hoursList);
        durationHourPicker.setData(durationHoursList);
        durationHourPicker.setSelectedItemPosition(1);

        //duration minutes wheel
        durationMinutesList = new ArrayList<>();

        int increament_factor = selected_game.getIncreament_factor();
        for (int i = 0; i < 60; i += increament_factor) {
            durationMinutesList.add(i < 10 ? "0" + i : i + "");
            minutesList.add(i < 10 ? "0" + i : i + "");
        }

        durationMinutePicker.setData(durationMinutesList);
        timeMinutePicker.setData(minutesList);

        if (booking.getStartTime() != null && booking.getDuration() != null)
            initialWheels();
    }

    public void initialWheels() {
        String startTime = booking.getStartTime();
        int duration = Integer.parseInt(booking.getDuration());
        Date time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);

        try {
            time = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);


        int hPos = 0;
        for (int i = 0; i < hoursList.size(); i++) {
            int h = calendar.get(Calendar.HOUR);
            String selectedhour = h < 10 ? "0" + h : h + "";
            if (hoursList.get(i).equals(selectedhour)) {
                hPos = i;
                break;
            }
        }
        timeHourPicker.setSelectedItemPosition(hPos);


        int mPos = 0;
        for (int i = 0; i < minutesList.size(); i++) {
            int m = calendar.get(Calendar.MINUTE);
            String selectedMinute = m < 10 ? "0" + m : m + "";
            if (minutesList.get(i).equals(selectedMinute)) {
                mPos = i;
                break;
            }
        }
        timeMinutePicker.setSelectedItemPosition(mPos);

        if (calendar.get(Calendar.AM_PM) == 0)
            amPmPicker.setSelectedItemPosition(0);
        else
            amPmPicker.setSelectedItemPosition(1);


        //// initial duration
        int hDurationPos = 0;
        for (int i = 0; i < durationHoursList.size(); i++) {
            int h = duration / 60;
            String selectedhour = h < 10 ? "0" + h : h + "";
            if (durationHoursList.get(i).equals(selectedhour)) {
                hDurationPos = i;
                break;
            }
        }
        durationHourPicker.setSelectedItemPosition(hDurationPos);

        int mDurationPos = 0;
        for (int i = 0; i < durationMinutesList.size(); i++) {
            int h = duration % 60;
            String selectedMinutes = h < 10 ? "0" + h : h + "";
            if (durationMinutesList.get(i).equals(selectedMinutes)) {
                mDurationPos = i;
                break;
            }
        }
        durationMinutePicker.setSelectedItemPosition(mDurationPos);

    }

    @Override
    protected void showData() {


    }

    @Override
    public void assignUIRefrences() {
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
      /*  amPmPicker.setIndicator(true);
        amPmPicker.setIndicatorColor(R.color.dark_gray);
        durationHourPicker.setIndicator(true);
        durationHourPicker.setIndicatorColor(R.color.dark_gray);
        durationMinutePicker.setIndicator(true);
        durationMinutePicker.setIndicatorColor(R.color.dark_gray);
        timeHourPicker.setIndicator(true);
        timeHourPicker.setIndicatorColor(R.color.dark_gray);
        timeMinutePicker.setIndicator(true);
        timeMinutePicker.setIndicatorColor(R.color.dark_gray);*/
        //
        cancel_button = (Button) findViewById(R.id.cancel_button);
        ok_button = (Button) findViewById(R.id.ok_button);

    }

    @Override
    protected void assignActions() {
        timeMinutePicker.setOnItemSelectedListener(this);
        timeHourPicker.setOnItemSelectedListener(this);
        durationHourPicker.setOnItemSelectedListener(this);
        durationMinutePicker.setOnItemSelectedListener(this);
        cancel_button.setOnClickListener(this);
        ok_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:
                ((FieldiumApplication) getApplication()).setBooking(((FieldiumApplication) getApplication()).getBooking());
                finish();
                break;
            case R.id.ok_button:
                try {
                    String startTime = String.valueOf(timeHourPicker.getData().get(timeHourPicker.getCurrentItemPosition())) +
                            ":" + String.valueOf(timeMinutePicker.getData().get(timeMinutePicker.getCurrentItemPosition())) +
                            " " + String.valueOf(amPmPicker.getData().get(amPmPicker.getCurrentItemPosition()));

                    int durationHour = Integer.parseInt(String.valueOf(durationHourPicker.getData().get(durationHourPicker.getCurrentItemPosition())));
                    int durationMinute;
                 /*   if (durationMinutePicker.getData().size() == durationMinutePicker.getCurrentItemPosition())
                        durationMinute = Integer.parseInt(String.valueOf(durationMinutePicker.getData().get(durationMinutePicker.getCurrentItemPosition()-1)));

                    else*/
                    durationMinute = Integer.parseInt(String.valueOf(durationMinutePicker.getData().get(durationMinutePicker.getCurrentItemPosition())));

//                    int durationMinute = Integer.parseInt(String.valueOf(durationMinutePicker.getData().get(durationMinutePicker.getCurrentItemPosition())));
                    int duration = durationHour * 60 + durationMinute;
                    if (duration < selected_game.getMinimum_duration())
                        throw new InvalidInputException("Minimum duration is " + selected_game.getMinimum_duration());

                    booking.setStartTime(startTime);
                    booking.setDuration(String.valueOf(duration));
//                Toast.makeText(fieldiumApplication, /*calendar1.get(Calendar.AM_PM)*/duration + "", Toast.LENGTH_SHORT).show();


                    ////

         /*       booking.setStartTime(startTime_spinner.getSelectedItem().toString());
                for (int i = 1; i <= duration_map.size(); i++) {
                    if (duration_map.get(i).equals(durations_spinner.getSelectedItem().toString())) {
                        booking.setDuration(String.valueOf(i));
                        break;

                    }
                }*/
                    ((FieldiumApplication) getApplication()).setBooking(booking);
                    Intent intent = new Intent();
                    intent.putExtra("selected_date", booking.getStartTime());
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (InvalidInputException e) {
                    Toast.makeText(fieldiumApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    public int calculateTimesDifference(String start, String end) {
        Date time1 = null;
        Date time2 = null;
        try {
            time1 = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH).parse(start);
            time2 = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH).parse(end);
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
        hours = (hours < 0 ? -hours : hours);
        return hours;
    }

    /*public void calculateDurations(String selectedTime) {
        durations = new ArrayList<>();
        duration_map = new HashMap<>();
        for (int i = 0; i < booking.getAvailableTimes().size(); i++) {
            AvailableTime availableTime = booking.getAvailableTimes().get(i);
            if (checkSelectedTimeRange(selectedTime, availableTime.getStart(), availableTime.getEnd())) {
                int h = calculateTimesDifference(selectedTime, availableTime.getEnd());
                for (int i1 = 1; i1 <= h; i1++)
                    duration_map.put(i1, i1 + " Hrs");
                for (int k = 1; k <= duration_map.size(); k++)
                    durations.add(duration_map.get(k));
                ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, durations);
                durations_spinner.setAdapter(timeAdapter);
                if (booking.getDuration() != null) {
                    durations_spinner.setSelection(Integer.parseInt(booking.getDuration()) - 1);
                }
                break;
            }
        }
    }*/

   /* public boolean checkSelectedTimeRange(String selectedTime, String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        try {
            Date time1 = dateFormat.parse(start);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);


            Date time2 = dateFormat.parse(end);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
//            calendar2.add(Calendar.DATE, 1);

//            String someRandomTime = "01:00:00";
            Date d = dateFormat.parse(selectedTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
//            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if ((x.after(calendar1.getTime()) || x.compareTo(calendar1.getTime()) == 0)
                    && x.before(calendar2.getTime())) {
                return true;
            } else return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }*/


    /*private void setTimePickerInterval(TimePicker timePicker) {
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            // Field timePickerField = classForid.getField("pickStartTime");

            Field field = classForid.getField("hour");

            minutePicker = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));
//            minutePicker.setVisibility(View.GONE);

//            minutePicker.setMinValue(0);
//            minutePicker.setMaxValue(11);
            displayedValues = new ArrayList<String>();
            for (int i = 1; i <= 12; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setMinValue(1);
            minutePicker.setMaxValue(displayedValues.size());

            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
            minutePicker.setWrapSelectorWheel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {

        switch (picker.getId()) {
            case R.id.main_wheel_hour:
                picker.setSelectedItemPosition(position);

                break;
            case R.id.main_wheel_minute:
                picker.setSelectedItemPosition(position);

                break;
            case R.id.main_wheel_ampm:
                picker.setSelectedItemPosition(position);

                break;
            case R.id.main_wheel_durationHour:
                if (String.valueOf(data).equals("00")) {
                    durationMinutesList.remove("00");
                    durationMinutePicker.setData(durationMinutesList);

                } else {
                    if (!durationMinutesList.contains("00"))
                        durationMinutesList.add("00");
                    durationMinutePicker.setData(durationMinutesList);
                }
                durationMinutePicker.setSelectedItemPosition(0);
                picker.setSelectedItemPosition(position);

                break;
            case R.id.main_wheel_durationMinute:

                picker.setSelectedItemPosition(position);
                break;
        }

    }
}
