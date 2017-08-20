package com.bluebrand.fieldium.view.Booking;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.other.RangeTimePickerDialog;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TimingActivity extends MasterActivity {
    List<String> durations;
    List<String> startTimes;
    Spinner durations_spinner;
    Spinner startTime_spinner;
    Booking booking;
    Button cancel_button, ok_button;
    FieldiumApplication fieldiumApplication;
    private int TIME_PICKER_INTERVAL = 5;
    NumberPicker minutePicker;
    List<String> displayedValues;
    //    CalendarView calendarView;
    HashMap<Integer, String> duration_map;
    TimePicker pickStartTime;

//    boolean justCalendar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      setLanguage();
        setContentView(R.layout.activity_timing);
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        pickStartTime = (TimePicker) findViewById(R.id.tp);
//        pickStartTime.setIs24HourView(true);
//
//        setTimePickerInterval(pickStartTime);
   /*     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        fieldiumApplication = ((FieldiumApplication) getApplication());
        booking = ((FieldiumApplication) getApplication()).getBooking();
        getData();
    }

    @Override
    protected void getData() {
//        justCalendar = getIntent().getBooleanExtra("just_calendar", false);
//        if (!justCalendar) {
        durations = new ArrayList<>();
        duration_map = new HashMap<>();
        startTimes = new ArrayList<>();
        if (booking.getAvailableTimes().size() == 0) {
            duration_map.put(1, "1 Hrs");
            duration_map.put(2, "2 Hrs");
            duration_map.put(3, "3 Hrs");
            duration_map.put(4, "4 Hrs");
            duration_map.put(5, "5 Hrs");
            duration_map.put(6, "6 Hrs");
            duration_map.put(7, "7 Hrs");
            duration_map.put(8, "8 Hrs");
            duration_map.put(9, "9 Hrs");
            duration_map.put(10, "10 Hrs");
            duration_map.put(11, "11 Hrs");
            duration_map.put(12, "12 Hrs");

            durations.add(duration_map.get(1));
            durations.add(duration_map.get(2));
            durations.add(duration_map.get(3));
            durations.add(duration_map.get(4));
            durations.add(duration_map.get(5));
            durations.add(duration_map.get(6));
            durations.add(duration_map.get(7));
            durations.add(duration_map.get(8));
            durations.add(duration_map.get(9));
            durations.add(duration_map.get(10));
            durations.add(duration_map.get(11));
            durations.add(duration_map.get(12));

            startTimes.add("01:00 AM");
            startTimes.add("02:00 AM");
            startTimes.add("03:00 AM");
            startTimes.add("04:00 AM");
            startTimes.add("05:00 AM");
            startTimes.add("06:00 AM");
            startTimes.add("07:00 AM");
            startTimes.add("08:00 AM");
            startTimes.add("09:00 AM");
            startTimes.add("10:00 AM");
            startTimes.add("11:00 AM");
            startTimes.add("12:00 PM");
            startTimes.add("01:00 PM");
            startTimes.add("02:00 PM");
            startTimes.add("03:00 PM");
            startTimes.add("04:00 PM");
            startTimes.add("05:00 PM");
            startTimes.add("06:00 PM");
            startTimes.add("07:00 PM");
            startTimes.add("08:00 PM");
            startTimes.add("09:00 PM");
            startTimes.add("10:00 PM");
            startTimes.add("11:00 PM");
            startTimes.add("12:00 AM");
        } else {
            booking.getAvailableTimes().size();
            for (int i = 0; i < booking.getAvailableTimes().size(); i++) {
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
//                SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
                try {
                    Date open_time = formatter.parse(booking.getAvailableTimes().get(i).getStart());
                    Date close_time = formatter.parse(booking.getAvailableTimes().get(i).getEnd());
                    Date close_time1 = formatter.parse(booking.getAvailableTimes().get(i).getEnd());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(open_time);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(close_time1);
                    cal1.add(Calendar.MINUTE, 1);
                    close_time1 = formatter.parse(formatter.format(cal1.getTime()));
                    while (open_time.compareTo(close_time) != 0) {
                        startTimes.add(formatter.format(open_time));
                        cal.add(Calendar.HOUR, 1);
                        open_time = formatter.parse(formatter.format(cal.getTime()));
//                        if (open_time.after(formatter.parse(formatter.format(new Date()))) && open_time.before(close_time))
                        if (open_time.compareTo(close_time1) == 0)
                            break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            int h = calculateTimesDifference(booking.getAvailableTimes().get(0).getStart(), booking.getAvailableTimes().get(0).getEnd());
            for (int i1 = 1; i1 <= h; i1++)
                duration_map.put(i1, i1 + " Hrs");
            for (int k = 1; k <= duration_map.size(); k++)
                durations.add(duration_map.get(k));


//        }
        }
        showData();
    }

    @Override
    protected void showData() {
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, durations);
        durations_spinner.setAdapter(timeAdapter);
        if (booking.getDuration() != null) {
           /* int index = 0;
            for (int i = 0; i < durations.size(); i++) {
                if (durations.get(i) .equals( booking.getDuration())) {
                    index = i;
                    break;
                }
            }
            durations_spinner.setSelection(index);*/
            durations_spinner.setSelection(Integer.parseInt(booking.getDuration()) - 1);
        }
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, startTimes);
        startTime_spinner.setAdapter(startTimeAdapter);
        if (booking.getAvailableTimes().size() != 0) {
            startTime_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//               if(startTime_spinner.getSelectedItem().toString())
                    calculateDurations((String) (startTime_spinner.getSelectedItem()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        if (booking.getStartTime() != null) {
            int index = 0;
            for (int i = 0; i < startTimes.size(); i++) {
                if (startTimes.get(i).equals(booking.getStartTime())) {
                    index = i;
                    break;
                }
            }
            startTime_spinner.setSelection(index);
        }

    }

    @Override
    public void assignUIRefrences() {
        durations_spinner = (Spinner) findViewById(R.id.durations_spinner);
        startTime_spinner = (Spinner) findViewById(R.id.startTime_spinner);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        ok_button = (Button) findViewById(R.id.ok_button);

    }

    @Override
    protected void assignActions() {
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
                Calendar mcurrentTime = Calendar.getInstance();
                int hour =1/* mcurrentTime.get(Calendar.HOUR_OF_DAY)*/;
                int minute = /*mcurrentTime.get(Calendar.MINUTE)*/9;
                RangeTimePickerDialog timePickerDialog=new RangeTimePickerDialog(TimingActivity.this,
                        TimePickerDialog.THEME_HOLO_LIGHT ,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        time.setText(selectedHour + ":" + selectedMinute);
                    }},hour,minute,true);
                timePickerDialog.setMax(8,30);
                timePickerDialog.setMin(1,15);
                timePickerDialog.show();
        /*        booking.setStartTime(startTime_spinner.getSelectedItem().toString());
                for (int i = 1; i <= duration_map.size(); i++) {
                    if (duration_map.get(i).equals(durations_spinner.getSelectedItem().toString())) {
                        booking.setDuration(String.valueOf(i));
                        break;

                    }
                }
                ((FieldiumApplication) getApplication()).setBooking(booking);
                Intent intent = new Intent();
                intent.putExtra("selected_date", booking.getStartTime());
                setResult(RESULT_OK, intent);
                finish();
                break;*/

        }

    }

    public static ArrayList getPeriods() {
        ArrayList<String> times = new ArrayList<>();
        times.add("01:00 AM");
        times.add("02:00 AM");
        times.add("03:00 AM");
        times.add("04:00 AM");
        times.add("05:00 AM");
        times.add("06:00 AM");
        times.add("07:00 AM");
        times.add("08:00 AM");
        times.add("09:00 AM");
        times.add("10:00 AM");
        times.add("11:00 AM");
        times.add("12:00 PM");
        times.add("01:00 PM");
        times.add("02:00 PM");
        times.add("03:00 PM");
        times.add("04:00 PM");
        times.add("05:00 PM");
        times.add("06:00 PM");
        times.add("07:00 PM");
        times.add("08:00 PM");
        times.add("09:00 PM");
        times.add("10:00 PM");
        times.add("11:00 PM");
        times.add("12:00 AM");
        return times;
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

    public void calculateDurations(String selectedTime) {
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
    }

    public boolean checkSelectedTimeRange(String selectedTime, String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
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
            if ((x.after(calendar1.getTime()) || x.compareTo(calendar1.getTime()) == 0) && x.before(calendar2.getTime())) {
                return true;
            } else return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            // Field timePickerField = classForid.getField("pickStartTime");

            Field field = classForid.getField("minute");
            minutePicker = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));

            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(11);
            displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            //  for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
            //      displayedValues.add(String.format("%02d", i));
            //  }
            minutePicker.setDisplayedValues(displayedValues
                    .toArray(new String[0]));
            minutePicker.setWrapSelectorWheel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
