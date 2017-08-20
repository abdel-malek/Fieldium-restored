package com.bluebrand.fieldiumadmin.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.bluebrand.fieldiumadmin.Model.Game;
import com.bluebrand.fieldiumadmin.Model.RefusedMsg;
import com.bluebrand.fieldiumadmin.View.Adapter.RefusedMesgAdapter;
import com.bluebrand.fieldiumadmin.View.Utils.AndroidBmpUtil;
import com.bluebrand.fieldiumadmin.controller.UserController;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.Model.Reservation;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.Adapter.ReservationAdapter;
import com.bluebrand.fieldiumadmin.View.Utils.CircleTransform;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.ReservationController;

public class MainActivity extends MasterActivity implements NavigationView.OnNavigationItemSelectedListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    //UI vars
    NavigationView navigationView;
    DrawerLayout drawer;
    FloatingActionButton fab;
    Dialog ReservationDialog;
    WeekView mWeekView;
    ListView reservations_listView;
    ReservationAdapter reservationAdapter;
    SwipeRefreshLayout swipeContainer;
    TextView reservations_textView;
    LinearLayout Fields_linearLayout;
    ListView messages_listView;

    TextView currentDate_textView;

    Calendar currentViewDate = Calendar.getInstance();

    //Data vars
    CurrentAndroidUser user;
    List<RefusedMsg> refusedMsgs;
    List<Reservation> reservations;
    List<WeekViewEvent> events = new ArrayList();
    final int MY_FIELDS = 3;
    final int REPORT = 4;
    final int PASS=114;
    final int Fields_REPORT = 5;
    final int ABOUT = 113;


    final String FIRST_FIELD_COLOR = "#F26539";
    final String SEC_FIELD_COLOR = "#09BED2";
    final String THR_FIELD_COLOR = "#4BB85D";
    final String FORTH_FIELD_COLOR = "#F89833";
    final String FIFTH_FIELD_COLOR = "#089299";
    final String STH_FIELD_COLOR = "#5BC2A6";
    private static final int RESERVATION_INTENT = 110;
    private static final String RESERVATION_RECEIVE_ACTION="RESERVATION_RECEIVE_ACTION";

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        receiversInit();
        Picasso.with(this)
                .load(((MyApplication)getApplication()).getMyCompanyInfo().getLogo().getUrl())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        companyBitmap =bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    @Override
    protected void onPause() {
        if(ReservationDialog!=null){
            if(ReservationDialog.isShowing())
                ReservationDialog.dismiss();
        }
        super.onPause();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.nav_booking){
            drawer.closeDrawers();
        }
        else if (id == R.id.nav_field_profile) {
            Intent intent = new Intent(this,
                    companyActivity.class);
            startActivityForResult(intent, 0);

        } else if (id == R.id.nav_my_fields) {
            Intent intent = new Intent(this,
                    MyFieldsActivity.class);
            startActivityForResult(intent, MY_FIELDS);
        } else if (id == R.id.nav_fields_report) {
            Intent intent = new Intent(this,
                    FieldsReportActivity.class);
            startActivityForResult(intent, Fields_REPORT);
        }
        else if (id == R.id.nav_reservations_report) {

            Intent intent = new Intent(this,
                    ReportActivity.class);
            startActivityForResult(intent, REPORT);
        }else if(id==R.id.nav_dec_reservations_report){
            Intent intent = new Intent(this,
                    DeclinedReportActivity .class);
            startActivityForResult(intent, REPORT);
        }
        else if(id==R.id.nav_pass){
            Intent intent = new Intent(this,
                    PasswordActivity.class);
            startActivityForResult(intent, PASS);
        }
        else if (id == R.id.nav_logout) {
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
                if(Message.equals("Not authorized")){
                    new CurrentAndroidUser(mContext).SignOut();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        })).getReservations(new SuccessCallback<List<Reservation>>() {
            @Override
            public void OnSuccess(List<Reservation> result) {
                swipeContainer.setRefreshing(false);
                if(result.size()==0)
                    reservations_textView.setText("No incoming reservations");
                else
                    reservations_textView.setText("");

                reservations = result;
                reservations_listViewInit();
                addedApprovedReservationsToCalander();

            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    void showData() {
        Fields_linearLayout.removeAllViews();

        if (((MyApplication) getApplication()).getMyFields().size() != 0) {
            int i = 0;
            for (Field field : ((MyApplication) getApplication()).getMyFields()) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                textView.setText(field.getName());
                textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER);
                if (i%6 == 0) {
                    textView.setTextColor(Color.parseColor(FIRST_FIELD_COLOR));

                }
                if (i%6 == 1) {
                    textView.setTextColor(Color.parseColor(SEC_FIELD_COLOR));

                }
                if (i%6 == 2) {
                    textView.setTextColor(Color.parseColor(THR_FIELD_COLOR));
                }
                if (i%6 == 3) {
                    textView.setTextColor(Color.parseColor(FORTH_FIELD_COLOR));
                }
                if (i%6 == 4) {
                    textView.setTextColor(Color.parseColor(FIFTH_FIELD_COLOR));
                }
                if (i%6 == 5) {
                    textView.setTextColor(Color.parseColor(STH_FIELD_COLOR));
                }
                Fields_linearLayout.addView(textView);

              i++;
            }
        }

    }

    @Override
    void assignUIReferences() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setFieldsSize(((MyApplication) getApplication()).getMyFields().size());
        mWeekView.setGamesBitmaps(((MyApplication) getApplication()).getMyGamesBitmap());
        reservations_listView = (ListView) findViewById(R.id.reservations_listView);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.form_container);
        reservations_textView = (TextView) findViewById(R.id.reservations_textView);
        Fields_linearLayout = (LinearLayout) findViewById(R.id.Fields_linearLayout);
        currentDate_textView = (TextView) findViewById(R.id.currentDate_textView);
        String date = ""+currentViewDate.get(Calendar.DAY_OF_MONTH)+"-"+( currentViewDate.get(Calendar.MONTH)+1)+"-"+currentViewDate.get(Calendar.YEAR);
        currentDate_textView.setText(date);
        currentDate_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MainActivity.this,
                        currentViewDate.get(Calendar.YEAR),
                        currentViewDate.get(Calendar.MONTH),
                        currentViewDate.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        View navHeaderView= navigationView.getHeaderView(0);
        TextView tvHeaderName= (TextView) navHeaderView.findViewById(R.id.nav_comp_textView);
        tvHeaderName.setText(((MyApplication)getApplication()).getMyCompanyInfo().getName());
        hideDrawerSupportSections();
        ImageView imageView=(ImageView)navHeaderView.findViewById(R.id.nav_comp_imageView);
        Picasso.with(this)
                .load(((MyApplication)getApplication()).getMyCompanyInfo().getLogo().getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                .transform(new CircleTransform())
                .error(R.mipmap.add_photo)
                .placeholder(R.mipmap.add_photo)
                .into(imageView);
    }

    private void hideDrawerSupportSections(){
        user=new CurrentAndroidUser(this);
        if(user.Get().isCasher()){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_field_profile).setVisible(false);
            nav_Menu.findItem(R.id.nav_my_fields).setVisible(false);
        }
    }

    Bitmap companyBitmap;
    @Override
    protected void onResume() {
        super.onResume();
        View navHeaderView= navigationView.getHeaderView(0);
        TextView tvHeaderName= (TextView) navHeaderView.findViewById(R.id.nav_comp_textView);
        tvHeaderName.setText(((MyApplication)getApplication()).getMyCompanyInfo().getName());
        ImageView imageView=(ImageView)navHeaderView.findViewById(R.id.nav_comp_imageView);
        Picasso.with(this)
                .load(((MyApplication)getApplication()).getMyCompanyInfo().getLogo().getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                .transform(new CircleTransform())
                .error(R.mipmap.add_photo)
                .placeholder(R.mipmap.add_photo)
                .into(imageView);
        List<Field> fields = ((MyApplication) getApplication()).getMyFields();
        if(mWeekView.getFieldsSize()!=fields.size()){
            mWeekView.setFieldsSize(fields.size());
            mWeekView.notifyDatasetChanged();
            showData();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = ""+dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        currentViewDate.set(Calendar.YEAR,year);
        currentViewDate.set(Calendar.MONTH,monthOfYear);
        currentViewDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        addedNewEvent=true;
        mWeekView.goToDate(currentViewDate);
        currentDate_textView.setText(date);
    }

    @Override
    void assignActions() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReservationDialog();
            }
        });

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                ReservationDialog(addedApprovedReservationsToCalander((int)event.getId()),-1,event);
            }
        });
        mWeekView.setScrollListener(new WeekView.ScrollListener() {
                                        @Override
                                        public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                                            String date = ""+newFirstVisibleDay.get(Calendar.DAY_OF_MONTH)+"-"+( newFirstVisibleDay.get(Calendar.MONTH)+1)+"-"+newFirstVisibleDay.get(Calendar.YEAR);
                                            currentViewDate=newFirstVisibleDay;
                                            currentDate_textView.setText(date);
                                        }
                                    });
                // The week view has infinite scrolling horizontally. We have to provide the events of a
                // month every time the month changes on the week view.
                mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
                    @Override
                    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                        if (addedNewEvent) {
                            addedNewEvent = false;
                            return events;
                        } else
                            return new ArrayList<WeekViewEvent>();
                    }
                });

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                getData();
            }
        });
    }


    private void reservations_listViewInit() {
        List<Reservation> PendingReservations = getPendingReservations();
        if (PendingReservations.size() == 0) {
            reservations_textView.setText("No incoming reservations");
        }
            reservationAdapter = new ReservationAdapter(this, R.layout.reservation_item, PendingReservations, reservations_listView);
            reservations_listView.setAdapter(reservationAdapter);


       checkIfcomingFromNotif();
    }

    private void checkIfcomingFromNotif(){
        if(getIntent().getExtras()!=null)
        {
            Reservation reservation = (Reservation) getIntent().getExtras().getSerializable("reservation");
            if (((MyApplication) getApplication()).getFieldByID(reservation.getField_id()).isAutoConfirm()) {
                ReservationDialog(reservation,-1,null);
                mWeekView.goToDate(reservation.getDate());
            } else {
                ReservationDialog(reservation, getReservationPos(reservation),null);
            }
            getIntent().removeExtra("reservation");
        }
    }

    private List<Reservation> getPendingReservations() {
        List<Reservation> PendingReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.isPending()) {
                PendingReservations.add(reservation);
            }
        }
        return PendingReservations;
    }

    int oldReservationSize = 0;

    private void addedApprovedReservationsToCalander() {
        if (oldReservationSize < reservations.size()) {
            for (int i = oldReservationSize; i < reservations.size(); i++) {
                if (reservations.get(i).isApproved()) {
                    addEvent(reservations.get(i));
                }
            }
            oldReservationSize = reservations.size();
        }
    }

    private Reservation addedApprovedReservationsToCalander(int id) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getBooking_id()==id) {
                return reservations.get(i);
            }
        }
        return null;
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


    public void ReservationDialog(final Reservation reservation, final int pos,final WeekViewEvent event) {
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
            for (Field field : ((MyApplication) getApplication()).getMyFields()) {
                if (field.getField_id() == reservation.getField_id()) {
                    filed_dialogtextView.setText(field.getName());
                }
            }

            TextView game_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.game_dialogtextView);
            for (Game game : ((MyApplication) getApplication()).getMyGames()) {
                if (game.getGame_type_id() == reservation.getGame_type_id()) {
                    game_dialogtextView.setText(game.getName());
                }
            }

            TextView date_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.date_dialogtextView);
            date_dialogtextView.setText(reservation.getDate_string());

            TextView start_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.start_dialogtextView);
            start_dialogtextView.setText(reservation.getStart_string());

            TextView finish_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.duration_textview_dialog);
            finish_dialogtextView.setText(String.valueOf(reservation.getDuration())+" min");

            TextView note_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.note_dialogtextView);
            note_dialogtextView.setText(reservation.getNotes());

            TextView total_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.total_dialogtextView);
            total_dialogtextView.setText(reservation.getTotal() +" "+(new CurrentAndroidUser(getApplicationContext())).Get().getCurrency());


            Button decline_button = (Button) ReservationDialog.findViewById(R.id.decline_button);
            Button approve_button = (Button) ReservationDialog.findViewById(R.id.approve_button);
            ((ImageButton)ReservationDialog.findViewById(R.id.close_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReservationDialog.dismiss();
                }
            });
            if(pos==-1)
            {
                decline_button.setVisibility(View.GONE);
                approve_button.setTextColor(Color.parseColor("#F26539"));
                approve_button.setText("Cancel Reservation");
                approve_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCancelDialog(event,reservation);
                }
            });
            }
            else {
                decline_button.setText("Decline");
                decline_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowProgressDialog();
                        ReservationController.getInstance(new Controller(MainActivity.this, new FaildCallback() {
                            @Override
                            public void OnFaild(Code errorCode, String Message) {
                                HideProgressDialog();
                                if(Message.equals("already declined")){
                                    ReservationDialog.dismiss();
                                    reservationAdapter.getReservations().remove(pos);
                                    reservationAdapter.notifyDataSetChanged();
                                    showMessageInToast("This reservation already approved from another fieldium dashboard!.");
                                }else{
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
                                addEvent(result);
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

    Dialog popup;
    private void showCancelDialog(final WeekViewEvent event,final Reservation reservation) {
        popup= new Dialog(this);
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popup.setContentView(R.layout.popup_decline);

        refusedMsgs = ((MyApplication)getApplication()).getRefusedMsgs();

        messages_listView = (ListView)popup.findViewById(R.id.messages_listView);
        messages_listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        RefusedMesgAdapter refusedMesgAdapter=new RefusedMesgAdapter(this,R.layout.refuse_message,refusedMsgs);
        messages_listView.setAdapter(refusedMesgAdapter);


        Button decline_Msg_Button=(Button)popup.findViewById(R.id.cancel_Msg_Button);
        decline_Msg_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int MsgID=((RefusedMesgAdapter) messages_listView.getAdapter()).getSelectedMsgId();
                String Msg=((RefusedMesgAdapter) messages_listView.getAdapter()).getSelectedMsgString();

                if(MsgID==0&&Msg.equals(""))
                {
                    showMessageInToast("Please select a message or write one");
                }
                else {
                    ShowProgressDialog();
                    ReservationController.getInstance(getmController()).cancelReservation(reservation.getBooking_id(),MsgID,Msg, new SuccessCallback<String>() {
                        @Override
                        public void OnSuccess(String result) {
                            HideProgressDialog();
                            events.remove(event);
                            addedNewEvent=true;
                            mWeekView.notifyDatasetChanged();
                            popup.hide();
                            ReservationDialog.hide();
                        }
                    });
                }
            }
        });
        ImageButton close_Button=(ImageButton)popup.findViewById(R.id.close_Button);
        close_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        popup.show();
    }


    int dur=0;
    Calendar starttime;
    Calendar ca;
    int startHour = 00;
    int startMinute = 00;

    public void addReservationDialog() {
        startHour = 10;
        startMinute = 00;
        dur=0;
        // custom dialog
        ca = Calendar.getInstance();
        ReservationDialog = new Dialog(this);
        ReservationDialog.setCanceledOnTouchOutside(false);
        ReservationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ReservationDialog.setContentView(R.layout.add_reservation_dialog);

        // set the custom dialog components - text, image and button
        final EditText playeName_dialogtextView = (EditText) ReservationDialog.findViewById(R.id.playeName_dialogtextView);
        final EditText playePhone_dialogtextView = (EditText) ReservationDialog.findViewById(R.id.playePhone_dialogtextView);
        final Spinner spinner_dialog = (Spinner) ReservationDialog.findViewById(R.id.spinner_dialog);
        ArrayAdapter<Field> adapter = new ArrayAdapter<Field>(this, R.layout.field_spinner_item, ((MyApplication) getApplication()).getMyFields());
        adapter.setDropDownViewResource(R.layout.field_spinner_dropdown_item);
        spinner_dialog.setAdapter(adapter);

        final Spinner game_type_spinner_dialog = (Spinner) ReservationDialog.findViewById(R.id.game_type_spinner_dialog);
        final ArrayAdapter<Game> game_adapter = new ArrayAdapter<Game>(this, R.layout.field_spinner_item);
        game_adapter.setDropDownViewResource(R.layout.field_spinner_dropdown_item);
        game_type_spinner_dialog.setAdapter(game_adapter);

        spinner_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Field field=((MyApplication) getApplication()).getMyFields().get(position);
                game_adapter.clear();
                game_adapter.addAll(field.getGames());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        final TextView date_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.date_dialogtextView);
        date_dialogtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.DatePickerDialog(MainActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        date_dialogtextView.setText(Year + "-" + (Month + 1) + "-" + Day);
                        ca.set(Calendar.YEAR, Year);
                        ca.set(Calendar.MONTH, Month);
                        ca.set(Calendar.DAY_OF_MONTH, Day);
                    }
                }, ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date_dialogtextView.setText(ca.get(Calendar.YEAR) + "-" + (ca.get(Calendar.MONTH) + 1) + "-" + ca.get(Calendar.DAY_OF_MONTH));

        final TextView total_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.total_dialogtextView);
        total_dialogtextView.setText("0 "+(new CurrentAndroidUser(getApplicationContext())).Get().getCurrency());

        final TextView start_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.start_dialogtextView);
        final TextView duration_textview_dialog = (TextView) ReservationDialog.findViewById(R.id.duration_textview_dialog);

        start_dialogtextView.setText("10:00 AM");
        starttime = Calendar.getInstance();
        starttime.set(Calendar.HOUR_OF_DAY, 10);
        starttime.set(Calendar.MINUTE, 0);

        start_dialogtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startHour = selectedHour;
                        String am_pm = "";
                        starttime = Calendar.getInstance();
                        starttime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        starttime.set(Calendar.MINUTE, selectedMinute);
                        if (starttime.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (starttime.get(Calendar.AM_PM) == Calendar.PM) {
                            am_pm = "PM";
                        }
                        String strHrsToShow = (starttime.get(Calendar.HOUR) == 0) ? "12" : starttime.get(Calendar.HOUR) + "";
                        start_dialogtextView.setText(strHrsToShow + ":" + (starttime.get(Calendar.MINUTE) == 0 ? "00" : starttime.get(Calendar.MINUTE)) + " " + am_pm);
                    }
                }, starttime.get(Calendar.HOUR_OF_DAY), starttime.get(Calendar.MINUTE), false);//true for 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        duration_textview_dialog.setText("0 mins");
        ((RelativeLayout)duration_textview_dialog.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog time_dialog=new Dialog(MainActivity.this);
                time_dialog.setCanceledOnTouchOutside(false);
                time_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                time_dialog.setContentView(R.layout.select_time_dialog);

                final NumberPicker hour_numberPicker=(NumberPicker)time_dialog.findViewById(R.id.hour_numberPicker);
                hour_numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                hour_numberPicker.setMinValue(0);
                hour_numberPicker.setMaxValue(23);
                hour_numberPicker.setValue(dur/60);

                final NumberPicker minute_numberPicker=(NumberPicker)time_dialog.findViewById(R.id.minute_numberPicker);
                minute_numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                Game SelectedGame=((MyApplication) getApplication()).getMyFields().get(spinner_dialog.getSelectedItemPosition()).getGames().get(game_type_spinner_dialog.getSelectedItemPosition());
                String[] minuteValues = new String[60/SelectedGame.getIncreament_factor()];
                for (int i = 0; i < minuteValues.length; i++) {
                    String number = Integer.toString(i*SelectedGame.getIncreament_factor());
                    minuteValues[i] = number.length() < 2 ? "0" + number : number;
                }
                minute_numberPicker.setMinValue(0);
                minute_numberPicker.setMaxValue(minuteValues.length-1);
                minute_numberPicker.setDisplayedValues(minuteValues);
                minute_numberPicker.setValue(dur%60);
                time_dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time_dialog.dismiss();
                    }
                });
                time_dialog.findViewById(R.id.set_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int m=Integer.valueOf(minute_numberPicker.getDisplayedValues()[minute_numberPicker.getValue()]);
                        dur=hour_numberPicker.getValue()*60+m;
                        duration_textview_dialog.setText(String.valueOf(dur)+" mins");
                        Field selectedField = ((MyApplication) getApplication()).getMyFields().get(spinner_dialog.getSelectedItemPosition());
                        int total_m=m==0?0:selectedField.getHour_rate()/(60/m);
                        total_dialogtextView.setText(String.valueOf(selectedField.getHour_rate() *hour_numberPicker.getValue()+total_m));
                        time_dialog.dismiss();
                    }
                });
                time_dialog.show();
            }
        });


        final TextView note_dialogtextView = (TextView) ReservationDialog.findViewById(R.id.note_dialogtextView);
        note_dialogtextView.setText("");
        note_dialogtextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        note_dialogtextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        final CheckBox sms_checkBox = (CheckBox) ReservationDialog.findViewById(R.id.sms_checkBox);

        Button cancel = (Button) ReservationDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationDialog.dismiss();
            }
        });
        Button create = (Button) ReservationDialog.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();
                if (playeName_dialogtextView.getText().toString().trim().equals("")) {
                    playeName_dialogtextView.setError("Player Name is required!");
                } else if (playePhone_dialogtextView.getText().toString().trim().equals("")) {
                    playePhone_dialogtextView.setError("Player Phone is required!");
                } else if (today.get(Calendar.DAY_OF_YEAR)>ca.get(Calendar.DAY_OF_YEAR)) {
                    showMessageInToast("the current reservation date is not valid!");
                }else {
                    Map<String, String> reservationDetaild = new HashMap<String, String>();
                    Field selectedField = ((MyApplication) getApplication()).getMyFields().get(spinner_dialog.getSelectedItemPosition());
                    reservationDetaild.put("field_id", String.valueOf(selectedField.getField_id()));
                    reservationDetaild.put("start", String.valueOf(starttime.get(Calendar.HOUR_OF_DAY)) + ":"+ (starttime.get(Calendar.MINUTE)/10==0 ? "0" +  starttime.get(Calendar.MINUTE) :  starttime.get(Calendar.MINUTE))+":00");
                    reservationDetaild.put("duration", String.valueOf(dur));
                    reservationDetaild.put("date", date_dialogtextView.getText().toString());
                    reservationDetaild.put("notes", note_dialogtextView.getText().toString());
                    reservationDetaild.put("player_name", playeName_dialogtextView.getText().toString());
                    reservationDetaild.put("player_phone", playePhone_dialogtextView.getText().toString());
                    reservationDetaild.put("sms_option", String.valueOf(sms_checkBox.isChecked()));
                    Game selectedGame=selectedField.getGames().get(game_type_spinner_dialog.getSelectedItemPosition());
                    reservationDetaild.put("game_type",String.valueOf(selectedGame.getGame_type_id()));
                    ShowProgressDialog();
                    ReservationController.getInstance(mController).creatReservation(reservationDetaild, new SuccessCallback<Reservation>() {
                        @Override
                        public void OnSuccess(final Reservation result) {
                            HideProgressDialog();
                            addEvent(result);
                            ReservationDialog.dismiss();
                            reservations.add(result);

                        }
                    });
                }
            }
        });

        ReservationDialog.show();
    }

    boolean addedNewEvent = false;

    private void addEvent(Reservation reservation) {
        addedNewEvent = true;
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, reservation.getStart().getTime().getHours());
        startTime.set(Calendar.MINUTE, reservation.getStart().getTime().getMinutes());
        startTime.set(Calendar.DAY_OF_MONTH, reservation.getDate().get(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.MONTH, reservation.getDate().get(Calendar.MONTH));
        startTime.set(Calendar.YEAR, reservation.getDate().get(Calendar.YEAR));
        Calendar endTime = (Calendar) startTime.clone();
        int h=reservation.getDuration()/60;
        endTime.add(Calendar.HOUR_OF_DAY,h);
        int m=reservation.getDuration()%60;
        endTime.add(Calendar.MINUTE, m);
        WeekViewEvent event = new WeekViewEvent(1, reservation.getPlayer_Name() /*+ "\n" + reservation.getStart_string() + "\n" + reservation.getEnd_string()*/, startTime, endTime);
        int pos=getEventPos(reservation);
        if (pos%6 == 0)
            event.setColor(Color.parseColor(FIRST_FIELD_COLOR));
        if (pos%6 == 1)
            event.setColor(Color.parseColor(SEC_FIELD_COLOR));
        if (pos%6 == 2)
            event.setColor(Color.parseColor(THR_FIELD_COLOR));
        if (pos%6 == 3)
            event.setColor(Color.parseColor(FORTH_FIELD_COLOR));
        if (pos%6 == 4)
            event.setColor(Color.parseColor(FIFTH_FIELD_COLOR));
        if (pos%6 == 5)
            event.setColor(Color.parseColor(STH_FIELD_COLOR));
        event.setId(reservation.getBooking_id());
        event.setPos(pos);
        event.setGameID(reservation.getGame_type_id());
        events.add(event);
        mWeekView.setEventTextSize(20);
        mWeekView.notifyDatasetChanged();


    }

    private int getEventPos(Reservation reservation){
        List<Field>fields=((MyApplication) getApplication()).getMyFields();
        for(int i=0;i<fields.size();i++){
            if(fields.get(i).getField_id()==reservation.getField_id()){
                return  i;
            }
        }
        return -1;
    }


    private int getReservationPos(Reservation reservation){
        List<Reservation>reservations=reservationAdapter.getReservations();
        for(int i=0;i<reservations.size();i++){
            if(reservations.get(i).getBooking_id()==reservation.getBooking_id()){
                return  i;
            }
        }
        return -1;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MY_FIELDS:
                if(data.getExtras().getBoolean("fieldsChanged")) {
                    Intent intent = new Intent();
                    intent.setClass(this, this.getClass());
                    startActivity(intent);
                    finish();
                }
                break;

        }

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
            if(((MyApplication)getApplication()).getFieldByID(reservation.getField_id()).isAutoConfirm()){
                addEvent(reservation);
                reservations.add(reservation);
            }
            else{
                reservations_textView.setText("");
                reservationAdapter.getReservations().add(reservation);
                reservationAdapter.notifyDataSetChanged();
            }

        }
    }

    ArrayList<Bitmap> calenderBitmap;

    public void saveCalendar(View view){
        calenderBitmap=new ArrayList<>();
       /* View navHeaderView= navigationView.getHeaderView(0);
        ViewToBitmap(navHeaderView.findViewById(R.id.nav_comp_imageView),0);*/
        if(companyBitmap !=null){
            calenderBitmap.add(companyBitmap);
        }
        ViewToBitmap(Fields_linearLayout,0);
        mWeekView.setHourHeight(79);
        mWeekView.goToHour(0);
        ViewToBitmap(mWeekView,1);

        mWeekView.goToHour(8*1);
        ViewToBitmap(mWeekView,2);

        mWeekView.goToHour(8*2); //24H wooooooooow
        ViewToBitmap(mWeekView,3);
        try {
            AndroidBmpUtil.save(combineImageIntoOne(calenderBitmap),"/mnt/sdcard/calendar.bmp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap ViewToBitmap(View view,int index){
        Bitmap cs;
        if(index==0)
        {
            cs= Bitmap.createBitmap(mWeekView.getWidth(), view.getHeight()*2,Bitmap.Config.ARGB_8888);
        }else{
            cs= Bitmap.createBitmap(mWeekView.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        }

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Canvas canvas = new Canvas(cs);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        canvas.save();
        view.setDrawingCacheEnabled(false);

        int [] allpixels = new int [cs.getHeight()*cs.getWidth()];
        cs.getPixels(allpixels, 0, cs.getWidth(), 0, 0, cs.getWidth(), cs.getHeight());
        if(index>1)
        {
            cs = Bitmap.createBitmap(cs, 0, (cs.getHeight() /18), cs.getWidth(), cs.getHeight()-(cs.getHeight() /18));

        }
        calenderBitmap.add(cs);

        return cs;
    }

    private Bitmap combineImageIntoOne(ArrayList<Bitmap> bitmap) {
        int w = 0, h = 0;
        for (int i = 0; i < bitmap.size(); i++) {
            if (i < bitmap.size() - 1) {
                w = bitmap.get(i).getWidth() > bitmap.get(i + 1).getWidth() ? bitmap.get(i).getWidth() : bitmap.get(i + 1).getWidth();
            }
            h += bitmap.get(i).getHeight();
        }

        Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);
        canvas.drawColor(Color.WHITE);
        int top = 0;
        for (int i = 0; i < bitmap.size(); i++) {
            top = (i == 0 ? 0 : top+bitmap.get(i-1).getHeight());
            canvas.drawBitmap(bitmap.get(i), 0f, top, null);
        }
        return temp;
    }
}
