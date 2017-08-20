package com.bluebrand.fieldiumadmin.View;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldiumadmin.Model.Reservation;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Adapter.ReservationAdapter;
import com.bluebrand.fieldiumadmin.View.Utils.CircleTransform;
import com.bluebrand.fieldiumadmin.View.Utils.TimeUtils;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.ReservationController;
import com.bluebrand.fieldiumadmin.controller.UserController;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends MasterActivity implements NavigationView.OnNavigationItemSelectedListener , com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{

    //UI vars
    NavigationView navigationView;
    Dialog ReservationDialog;
    ListView reservations_listView;
    ReservationAdapter reservationAdapter;
    SwipeRefreshLayout swipeContainer;
    TextView reservations_textView;
    TextView currentDate_textView;
    Calendar currentViewDate = Calendar.getInstance();
    ViewPager vpPager;
    DrawerLayout drawer;
    //Data vars
    List<Reservation> reservations;

    final int ABOUT = 113;

    private static final String RESERVATION_RECEIVE_ACTION="RESERVATION_RECEIVE_ACTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        receiversInit();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = ""+dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        currentViewDate.set(Calendar.YEAR,year);
        currentViewDate.set(Calendar.MONTH,monthOfYear);
        currentViewDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        currentDate_textView.setText(date);
        vpPager.setCurrentItem(TimeUtils.getPositionForDay(currentViewDate)+1);
        adapterViewPager.notifyDataSetChanged();
    }


    private static Context mContext;

    private CachingFragmentStatePagerAdapter adapterViewPager;

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_pending_reservations){
            findViewById(R.id.form_container).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.title_textView)).setText("Pending Reservations");

            drawer.closeDrawers();

        }else
        if(id==R.id.nav_approved_reservations){
            Intent intent=new Intent(this,CompaniesActivity.class);
            startActivity(intent);
            finish();

        }else
       if (id == R.id.nav_logout) {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("Do you really want to logout?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ShowProgressDialog();
                            SharedPreferences preferences = getSharedPreferences("UserToken", Context.MODE_PRIVATE);
                            String deviceToken=preferences.getString("token","");
                            UserController.getInstance(mController).Logout(deviceToken, new SuccessCallback<String>() {
                                @Override
                                public void OnSuccess(String result) {
                                    CurrentAndroidUser user=new CurrentAndroidUser(MainActivity.this);
                                    user.SignOut();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
        else if(id == R.id.nav_about){
            Intent intent = new Intent(this,
                    AboutActivity.class);
            startActivityForResult(intent, ABOUT);
        }


        return true;
    }

    @Override
    void getData() {
        ReservationController.getInstance(new Controller(this, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                swipeContainer.setRefreshing(false);
                reservations_textView.setText("An error occurred, pull to refresh please!.");
                if (findViewById(R.id.parentPanel) != null)
                    Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();

            }
        })).getPendingReservations(new SuccessCallback<List<Reservation>>() {
            @Override
            public void OnSuccess(List<Reservation> result) {
                swipeContainer.setRefreshing(false);
                if(result.size()==0)
                    reservations_textView.setText("No incoming reservations");
                else
                    reservations_textView.setText("");

                reservations = result;
                reservations_listViewInit();

            }
        });
    }

    @Override
    protected void onPostResume() {
        //showData();
        super.onPostResume();
    }

    @Override
    void showData() {


    }

    @Override
    void assignUIReferences() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);



        reservations_listView = (ListView) findViewById(R.id.reservations_listView);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.form_container);
        reservations_textView = (TextView) findViewById(R.id.reservations_textView);
        View navHeaderView= navigationView.getHeaderView(0);
        TextView tvHeaderName= (TextView) navHeaderView.findViewById(R.id.nav_comp_textView);
        tvHeaderName.setText("Support Admin");

        ImageView imageView=(ImageView)navHeaderView.findViewById(R.id.nav_comp_imageView);
        Picasso.with(this)
                .load(R.drawable.fieldium_logo).memoryPolicy(MemoryPolicy.NO_STORE)
                .transform(new CircleTransform())
                .error(R.mipmap.default_image)
                .placeholder(R.mipmap.default_image)
                .into(imageView);
    }



    @Override
    void assignActions() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                getData();
            }
        });
    }


    private void reservations_listViewInit() {
        if (reservations.size() == 0) {
            reservations_textView.setText("No incoming reservations");
        }
            reservationAdapter = new ReservationAdapter(this, R.layout.reservation_item, reservations, reservations_listView);
            reservations_listView.setAdapter(reservationAdapter);


       checkIfcomingFromNotif();
    }

    private void checkIfcomingFromNotif(){
        if(getIntent().getExtras()!=null)
        {
            Reservation reservation = (Reservation) getIntent().getExtras().getSerializable("reservation");
            if (reservation.isAutoConfirm()) {
                ReservationDialog(reservation,-1);
            } else {
                ReservationDialog(reservation, getReservationPos(reservation));
            }
            getIntent().removeExtra("reservation");
        }
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ReservationDialog!=null){
            if(ReservationDialog.isShowing())
                ReservationDialog.dismiss();

        }
    }

    public void ReservationDialog(final Reservation reservation, final int pos) {
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

            filed_dialogtextView.setText(reservation.getCompany_name()+", "+reservation.getField_name());

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

            ((ImageButton)ReservationDialog.findViewById(R.id.close_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReservationDialog.dismiss();
                }
            });

            Button decline_button = (Button) ReservationDialog.findViewById(R.id.decline_button);
            Button approve_button = (Button) ReservationDialog.findViewById(R.id.approve_button);

            if(reservation.isAutoConfirm()){
                decline_button.setVisibility(View.GONE);
                approve_button.setVisibility(View.GONE);
            }else {
                decline_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowProgressDialog();
                        ReservationController.getInstance(new Controller(MainActivity.this, new FaildCallback() {
                            @Override
                            public void OnFaild(Code errorCode, String Message) {
                                HideProgressDialog();
                                if (Message.equals("already declined")) {
                                    ReservationDialog.dismiss();
                                    reservationAdapter.getReservations().remove(pos);
                                    reservationAdapter.notifyDataSetChanged();
                                    showMessageInToast("This reservation already approved from another dashboard!.");
                                } else {
                                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
                                }
                            }
                        })).declineReservation(reservation.getBooking_id(), new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                HideProgressDialog();
                                ReservationDialog.dismiss();
                                reservationAdapter.getReservations().remove(pos);
                                reservationAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });

                approve_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowProgressDialog();
                        ReservationController.getInstance(mController).approveReservation(reservation.getBooking_id(), new SuccessCallback<Reservation>() {
                            @Override
                            public void OnSuccess(final Reservation result) {
                                HideProgressDialog();
                                ReservationDialog.dismiss();
                                reservationAdapter.getReservations().remove(pos);
                                reservationAdapter.notifyDataSetChanged();
                                reservations.add(result);
                            }
                        });
                    }
                });

            }
        }
        ReservationDialog.show();
    }

    private int getReservationPos(Reservation reservation)
    {
        List<Reservation>reservations=reservationAdapter.getReservations();
        for(int i=0;i<reservations.size();i++){
            if(reservations.get(i).getBooking_id()==reservation.getBooking_id()){
                return  i;
            }
        }
        return -1;
    }


    private void receiversInit()
    {
        reservationReceiver reservationReceiver =new reservationReceiver();
        IntentFilter intFilt = new IntentFilter(RESERVATION_RECEIVE_ACTION);
        registerReceiver(reservationReceiver, intFilt);
    }

    public class reservationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Reservation reservation=(Reservation)intent.getSerializableExtra("reservation");
            if(!reservation.isAutoConfirm()){
                reservations_textView.setText("");
                reservationAdapter.getReservations().add(reservation);
                reservationAdapter.notifyDataSetChanged();
            }

        }
    }




}
