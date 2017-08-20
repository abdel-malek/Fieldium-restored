package com.bluebrand.fieldium.view.Booking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.bluebrand.fieldium.view.MasterActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TimingActivityHomeScreentest extends MasterActivity implements WheelPicker.OnItemSelectedListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       setLanguage();
        setContentView(R.layout.activity_timing_home_screen);
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        fieldiumApplication = ((FieldiumApplication) getApplication());
        booking = ((FieldiumApplication) getApplication()).getBooking();


        getStartTimes();
//        getData();

    }

    @Override
    protected void getData() {
//        durations = new ArrayList<>();
//        startTimes = new ArrayList<>();
        //am_pm_wheel
        amPmList = new ArrayList<>();
        amPmList.add("AM");
        amPmList.add("PM");
        int am = selectedTime.get(Calendar.AM_PM);

        amPmPicker.setData(amPmList);
        amPmPicker.setSelectedItemPosition(am);


        // minutes wheel
        minutesList = new ArrayList<>();
        minutesList.add("00");
        minutesList.add("15");
        minutesList.add("30");
        minutesList.add("45");
        timeMinutePicker.setData(minutesList);

        //hours wheel
//        hoursList = new ArrayList<>();
//        hoursList.add("01");
//        hoursList.add("02");
//        hoursList.add("03");
//        hoursList.add("04");
//        hoursList.add("05");
//        hoursList.add("06");
//        hoursList.add("07");
//        hoursList.add("08");
//        hoursList.add("09");
//        hoursList.add("10");
//        hoursList.add("11");
//        hoursList.add("12");
/*
        int pos = 0;
        for (int i = 0; i < hoursList.size(); i++) {
//            Toast.makeText(fieldiumApplication,selectedTime.get(Calendar.HOUR)+"" , Toast.LENGTH_SHORT).show();
            int h = selectedTime.get(Calendar.HOUR);
            String selectedhour = h < 10 ? "0" + h : h + "";
            if (hoursList.get(i).equals(selectedhour)) {
                pos = i;
                break;
            }

        }*/
        timeHourPicker.setData(hoursList);
//        timeHourPicker.setSelectedItemPosition(pos);

        //duration hours wheel
        durationHoursList = new ArrayList<>();
//        durationHoursList.addAll();
        durationHoursList.addAll(calculateDurations(selectedRange.getStart(), selectedRange.getEnd()));
        durationHourPicker.setData(durationHoursList);
//        durationHourPicker.setSelectedItemPosition(1);

        //duration minutes wheel
        durationMinutesList = new ArrayList<>();
        durationMinutesList.add("00");
        durationMinutesList.add("15");
        durationMinutesList.add("30");
        durationMinutesList.add("45");
        durationMinutePicker.setData(durationMinutesList);

        //list without "00"
        durationMinutesListwithout0 = new ArrayList<>();
        durationMinutesListwithout0.add("15");
        durationMinutesListwithout0.add("30");
        durationMinutesListwithout0.add("45");


    }

    public List<AvailableTime> getAvailableTimes() {
        List<AvailableTime> availableTimeList = new ArrayList<>();
        AvailableTime av = new AvailableTime();
        av.setStart("12:00 AM");
        av.setEnd("03:00 AM");
        availableTimeList.add(av);
        av = new AvailableTime();
        av.setStart("04:00 AM");
        av.setEnd("05:30 AM");
        availableTimeList.add(av);
        av = new AvailableTime();
        av.setStart("07:00 AM");
        av.setEnd("11:59 PM");
        availableTimeList.add(av);
//    av=new AvailableTime();
//    av.setStart("07:30 AM");
//    av.setEnd("11:00 PM");
//    availableTimeList.add(av);
        return availableTimeList;
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
        amPmPicker.setIndicator(false);
        amPmPicker.setIndicatorColor(R.color.dark_gray);
        durationHourPicker.setIndicator(false);
        durationHourPicker.setIndicatorColor(R.color.dark_gray);
        durationMinutePicker.setIndicator(false);
        durationMinutePicker.setIndicatorColor(R.color.dark_gray);
        timeHourPicker.setIndicator(false);
        timeHourPicker.setIndicatorColor(R.color.dark_gray);
        timeMinutePicker.setIndicator(false);
        timeMinutePicker.setIndicatorColor(R.color.dark_gray);
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
                String startTime = String.valueOf(timeHourPicker.getData().get(timeHourPicker.getCurrentItemPosition())) +
                        ":" + String.valueOf(timeMinutePicker.getData().get(timeMinutePicker.getCurrentItemPosition())) +
                        " " + String.valueOf(amPmPicker.getData().get(amPmPicker.getCurrentItemPosition()));

                int durationHour = Integer.parseInt(String.valueOf(durationHourPicker.getData().get(durationHourPicker.getCurrentItemPosition())));
                int durationMinute = Integer.parseInt(String.valueOf(durationMinutePicker.getData().get(durationMinutePicker.getCurrentItemPosition())));
                int duration = durationHour * 60 + durationMinute;
                String tets = calculateRangeEnd(startTime, duration);

//                Toast.makeText(fieldiumApplication,duration+"\n"+ durationHour+"\n"+durationMinute+"\n"+startTime, Toast.LENGTH_SHORT).show();

                if (checkSelectedTimeRange(startTime, selectedRange.getStart(), selectedRange.getEnd()) && checkSelectedTimeRange(/*calculateRangeEnd(startTime, duration)*/tets, selectedRange.getStart(), selectedRange.getEnd()))
                    Toast.makeText(fieldiumApplication, "yes", Toast.LENGTH_SHORT).show();
                else Toast.makeText(fieldiumApplication, "No", Toast.LENGTH_SHORT).show();
//                booking.setStartTime(startTime);
//                booking.setDuration(String.valueOf(durationHour));
//                checkSelectedTimeRange()
//                Toast.makeText(TimingActivityHomeScreentest.this,calculateRangeEnd(startTime,duration) , Toast.LENGTH_SHORT).show();
//                Toast.makeText(fieldiumApplication, /*calendar1.get(Calendar.AM_PM)*/duration + "", Toast.LENGTH_SHORT).show();


                ////

         /*       booking.setStartTime(startTime_spinner.getSelectedItem().toString());
                for (int i = 1; i <= duration_map.size(); i++) {
                    if (duration_map.get(i).equals(durations_spinner.getSelectedItem().toString())) {
                        booking.setDuration(String.valueOf(i));
                        break;

                    }
                }*/
//                ((FieldiumApplication) getApplication()).setBooking(booking);
//                Intent intent = new Intent();
//                intent.putExtra("selected_date", booking.getStartTime());
//                setResult(RESULT_OK, intent);
//                finish();

                break;

        }

    }

    public List<String> calculateDurations(String start, String end) {
        Date time1 = null;
        Date time2 = null;
        List<String> durations = new ArrayList<>();
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

        for (int i = 0; i <= hours; i++)
            durations.add(i < 10 ? "0" + i : String.valueOf(i));
        return durations;
    }

    public int calculateTimesDifferance(String start, String end) {
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

    public String calculateRangeEnd(String start, int duration) {
        Date time1 = null;
        Date time2 = null;
        try {
            time1 = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH).parse(start);
//            time2 = new SimpleDateFormat("hh:mm aa").parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);

        calendar1.add(Calendar.MINUTE, duration);
        return (new SimpleDateFormat("hh:mm aa",Locale.ENGLISH).format(calendar1.getTime()));
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.setTime(time2);
//
//        if (time2.before(time1)) {
//            calendar2.add(Calendar.DATE, 1);
//        }
//        if (calendar2.get(Calendar.MINUTE) == 59)
//            calendar2.add(Calendar.MINUTE, 1);
//        long difference = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
//        int days = (int) (difference / (1000 * 60 * 60 * 24));
//        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
//        hours = (hours < 0 ? -hours : hours);


//        return "";
    }

    /*public void calculateTimesDifferance(String selectedTime) {
//        durations = new ArrayList<>();
//        duration_map = new HashMap<>();
        for (int i = 0; i < booking.getAvailableTimes().size(); i++) {
            AvailableTime availableTime = booking.getAvailableTimes().get(i);
            if (checkSelectedTimeRange(selectedTime, availableTime.getStart(), availableTime.getEnd())) {

//                int h = calculateTimesDifference(selectedTime, availableTime.getEnd());
//                for (int i1 = 1; i1 <= h; i1++)
//                    duration_map.put(i1, i1 + " Hrs");
//                for (int k = 1; k <= duration_map.size(); k++)
//                    durations.add(duration_map.get(k));
//                ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, durations);
//                durations_spinner.setAdapter(timeAdapter);
//                if (booking.getDuration() != null) {
//                    durations_spinner.setSelection(Integer.parseInt(booking.getDuration()) - 1);
//                }
                break;
            }
        }
    }
*/
    public AvailableTime checkTimeinWhichRange(String selectedTime) {

        for (int i = 0; i < getAvailableTimes().size(); i++) {
            AvailableTime availableTime = getAvailableTimes().get(i);
            if (checkSelectedTimeRange(selectedTime, availableTime.getStart(), availableTime.getEnd())) {

//                int h = calculateTimesDifference(selectedTime, availableTime.getEnd());
//                for (int i1 = 1; i1 <= h; i1++)
//                    duration_map.put(i1, i1 + " Hrs");
//                for (int k = 1; k <= duration_map.size(); k++)
//                    durations.add(duration_map.get(k));
//                ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, durations);
//                durations_spinner.setAdapter(timeAdapter);
//                if (booking.getDuration() != null) {
//                    durations_spinner.setSelection(Integer.parseInt(booking.getDuration()) - 1);
//                }
                return availableTime;

            }
        }
        return null;
    }
   /* public boolean checkTimeinRange(String selectedTime,AvailableTime availableTime) {

            if (checkSelectedTimeRange(selectedTime, availableTime.getStart(), availableTime.getEnd())) {

//                int h = calculateTimesDifference(selectedTime, availableTime.getEnd());
//                for (int i1 = 1; i1 <= h; i1++)
//                    duration_map.put(i1, i1 + " Hrs");
//                for (int k = 1; k <= duration_map.size(); k++)
//                    durations.add(duration_map.get(k));
//                ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getmContext(), R.layout.spinner_item, durations);
//                durations_spinner.setAdapter(timeAdapter);
//                if (booking.getDuration() != null) {
//                    durations_spinner.setSelection(Integer.parseInt(booking.getDuration()) - 1);
//                }
                return true;

            }
        return false;
    }*/

    public boolean checkSelectedTimeRange(String selectedTime, String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm",Locale.ENGLISH);
//        String selectedTime = "12:00 AM";
//        String start = "07:00 AM";
//        String end = "11:59 PM";
        try {
            Date time1 = dateFormat.parse(start);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = dateFormat.parse(end);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            if (calendar2.get(Calendar.MINUTE) == 59) {
                calendar2.add(Calendar.MINUTE, 1);

//                time2 = dateFormat.parse(dateFormat.format(calendar2.getTime()));
//                calendar2.setTime(time2);
            }

            Date d = dateFormat.parse(selectedTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
//            calendar3.add(Calendar.DATE, 1);
//            calendar2.setTime(dateFormat.parse(dateFormat.format(calendar2.getTime())));
//            calendar1.setTime(dateFormat.parse(dateFormat.format(calendar1.getTime())));
//            calendar3.setTime(dateFormat.parse(dateFormat.format(calendar3.getTime())));
//            if(time2.before(time1))
//                calendar2.add(Calendar.DATE,1);
            Date selected = calendar3.getTime();
            Date _start = calendar1.getTime();
            Date _end = calendar2.getTime();

//            Toast.makeText(TimingActivityHomeScreentest.this, "selected" + calendar3.getTime(), Toast.LENGTH_SHORT).show();
//            Log.e("end","end"+calendar2.getTime());
//            Log.e("selected","selected"+calendar3.getTime());

//        Processing -----> selected.compareTo(_end) == 0) by checkCompare0(calendar3,calendar2))


            if ((selected.after(_start) || selected.compareTo(_start) == 0) &&
                    (selected.before(_end) || /*checkCompare0(calendar3,calendar2)*/selected.compareTo(_end) == 0)) {
                return true;
            } else
                return false;
        } catch (ParseException e) {
            Toast.makeText(fieldiumApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.main_wheel_minute:
                break;
            case R.id.main_wheel_ampm:
                break;
            case R.id.main_wheel_durationHour:
//                int index = durationMinutePicker.getSelectedItemPosition();
                if (String.valueOf(data).equals("00"))
                    durationMinutePicker.setData(durationMinutesListwithout0);

                else durationMinutePicker.setData(durationMinutesList);


                break;
            case R.id.main_wheel_durationMinute:
                break;
        }

    }

    /*public String convertDateToString(Date selectedTime){


        return "";
    }*/
    public void getStartTimes() {
        //need to check getExtras==null?
        selectedTime = (Calendar) getIntent().getExtras().get("selectedTime");
        selectedTime.set(Calendar.MINUTE, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        AvailableTime av = checkTimeinWhichRange(formatter.format(selectedTime.getTime()));
        if (av != null) {
            selectedRange = av;
            startTimes = new ArrayList<>();
            hoursList = new ArrayList<>();
//        booking.setAvailableTimes(getAvailableTimes());
//        for (int i = 0; i < booking.getAvailableTimes().size(); i++) {

//                SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
            try {
                Date open_time = formatter.parse(av.getStart());
                Date close_time = formatter.parse(av.getEnd());
                Date close_time1 = formatter.parse(av.getEnd());
                Calendar cal = Calendar.getInstance();
                cal.setTime(open_time);
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(close_time1);
                if (cal1.get(Calendar.MINUTE) == 59)
                    cal1.add(Calendar.MINUTE, 1);
                close_time1 = formatter.parse(formatter.format(cal1.getTime()));
                while (open_time.compareTo(close_time) != 0) {
                    startTimes.add(formatter.format(open_time));
                    if (!hoursList.contains(cal.get(Calendar.HOUR) < 10 ? "0" + String.valueOf(cal.get(Calendar.HOUR)) : String.valueOf(cal.get(Calendar.HOUR))))
                        hoursList.add(cal.get(Calendar.HOUR) < 10 ? "0" + String.valueOf(cal.get(Calendar.HOUR)) : String.valueOf(cal.get(Calendar.HOUR)));

                    cal.add(Calendar.MINUTE, 15);
                    open_time = formatter.parse(formatter.format(cal.getTime()));
//                        if (open_time.after(formatter.parse(formatter.format(new Date()))) && open_time.before(close_time))
                    if (open_time.compareTo(close_time1) == 0)
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(fieldiumApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            getData();
//        }
        }

//        Collections.sort(hoursList);
       /* hourintList=new ArrayList<>();
        hourintList.add(02);
        hourintList.add(04);
        hourintList.add(03);
        hourintList.add(01);
        hourintList.add(11);
        hourintList.add(00);
        Collections.sort(hourintList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });*/


    }
}
