package com.bluebrand.fieldiumadmin.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldiumadmin.Model.Reservation;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Utils.TimeUtils;
import com.bluebrand.fieldiumadmin.controller.ReservationController;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;


/**
 * Created by r.desouki on 2/20/2017.
 */

public class ApprovedReservationsActivity extends MasterActivity implements  com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{
    private ViewPager viewPager;
    Dialog ReservationDialog;
    PagerTabStrip pager_header;
    private List<Reservation> reservations;
    private static Context mContext;
    static Calendar currentViewDate = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_approved_reservations);
        super.onCreate(savedInstanceState);

        mContext=this;

        viewPager = (ViewPager) findViewById(R.id.vpPager);
        pager_header=(PagerTabStrip)findViewById(R.id.pager_header);
        GestureDetector.OnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ApprovedReservationsActivity.this,
                        currentViewDate.get(Calendar.YEAR),
                        currentViewDate.get(Calendar.MONTH),
                        currentViewDate.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                return true;
            }
        };

        final GestureDetector gestureDetector = new GestureDetector(this, listener);

        pager_header.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        currentViewDate.set(Calendar.YEAR,year);
        currentViewDate.set(Calendar.MONTH,monthOfYear);
        currentViewDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        viewPager.setCurrentItem(TimeUtils.getPositionForDay(currentViewDate)+1);
    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),reservations);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(TimeUtils.getPositionForDay(Calendar.getInstance()));
    }



    @Override
    void getData() {
        int company_id=getIntent().getIntExtra("company_id",0);
        ReservationController.getInstance(new Controller(this, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                if (findViewById(R.id.parentPanel) != null)
                    Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
            }
        })).getReservations(String.valueOf(company_id),new SuccessCallback<List<Reservation>>() {
            @Override
            public void OnSuccess(List<Reservation> result) {
                reservations = result;
                setupViewPager(viewPager);
            }
        });
    }

    @Override
    void showData() {

    }

    @Override
    void assignUIReferences() {

    }

    @Override
    void assignActions() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ReservationDialog!=null){
            if(ReservationDialog.isShowing())
                ReservationDialog.dismiss();

        }
    }
    public void ReservationDialog(final Reservation reservation) {
        // custom dialog
        ReservationDialog = new Dialog(this);
        ReservationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ReservationDialog.setContentView(R.layout.reservation_dialog);
        if (reservation != null) {
            // set the custom dialog components - text, image and button
            TextView reservID_dialogtextView= (TextView) ReservationDialog.findViewById(R.id.reservID_dialogtextView);
            reservID_dialogtextView.setText("#"+String.valueOf(reservation.getBooking_id()));

            TextView playeName_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.playeName_dialogtextView);
            playeName_dialogtextView.setText(reservation.getPlayer_Name());

            TextView playePhone_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.playePhone_dialogtextView);
            playePhone_dialogtextView.setText(reservation.getPlayer_Phone());

            TextView filed_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.filed_dialogtextView);
            filed_dialogtextView.setText(reservation.getField_name());
            TextView game_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.game_dialogtextView);
            game_dialogtextView.setText(reservation.getGameName());

            TextView date_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.date_dialogtextView);
            date_dialogtextView.setText(reservation.getDate_string());

            TextView start_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.start_dialogtextView);
            start_dialogtextView.setText(reservation.getStart_string());

            TextView duration_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.duration_dialogtextView);
            duration_dialogtextView.setText(String.valueOf(reservation.getDuration())+" min");

            TextView note_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.note_dialogtextView);
            note_dialogtextView.setText(reservation.getNotes());

            TextView total_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.total_dialogtextView);
            total_dialogtextView.setText(reservation.getTotal() + " AED");


            Button decline_button = (Button) ReservationDialog.findViewById(R.id.decline_button);
            Button approve_button = (Button) ReservationDialog.findViewById(R.id.approve_button);
            decline_button.setVisibility(View.GONE);
            approve_button.setVisibility(View.GONE);

        }
        ReservationDialog.show();
    }


    public static class MyPagerAdapter extends CachingFragmentStatePagerAdapter {
        private Calendar cal;
        List<Reservation>reservations;
        public MyPagerAdapter(FragmentManager fragmentManager,List<Reservation>reservations) {
            super(fragmentManager);
            this.reservations=reservations;
        }

        @Override
        public int getCount() {
            return TimeUtils.DAYS_OF_TIME;
        }

        @Override
        public Fragment getItem(int position) {
            long timeForPosition = TimeUtils.getDayForPosition(position).getTimeInMillis();
            return FragmentContent.newInstance(timeForPosition,reservations);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            cal = TimeUtils.getDayForPosition(position);
            return TimeUtils.getFormattedDate(mContext, cal.getTimeInMillis());
        }


    }
}
