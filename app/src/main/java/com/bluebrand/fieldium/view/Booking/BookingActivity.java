package com.bluebrand.fieldium.view.Booking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldium.core.controller.FieldsController;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.fieldium.core.model.BookingStatus;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.adapter.BookingVouchersAdapter;
import com.bluebrand.fieldium.view.other.CustomSpinner;
import com.bluebrand.fieldium.view.other.DatePickerFragment;
import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Booking;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.MasterFormActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends MasterFormActivity implements DatePickerFragment.OnDialogClosed {
    TextView textView_date, textView_startTime, textView_duration, textView_total, textView_game, companyAddress,
            companyName, fieldName, costPerHour, voucher_panel_title, voucherValue, subTotal_textView, discount_textView;
    EditText editText_note;
    ImageView companyLogo;
    Booking booking;
    FieldiumApplication fieldiumApplication;
    SharedPreferences sharedPreferences;
    String token;
    String sentToken;
    AlertDialog alertDialog;
    CustomSpinner vouchersSpinner;
    ArrayList<Voucher> vouchers;
    Voucher selectVoucher, typeVoucherCode;
    Dialog myDialog;
    boolean isVoucherChecked = false;
    boolean isVoucherApplied = false;
    String voucher_code = "";
    Voucher voucher = new Voucher();
    String currency = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_booking);
        super.onCreate(savedInstanceState);
        fieldiumApplication = (FieldiumApplication) getApplication();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        currency = getCurrency();
        getData();
        setTitle(getResources().getString(R.string.booking));

    }

    @Override
    public void assignUIRefrences() {
        editText_note = (EditText) findViewById(R.id.booking_note);
        textView_date = (TextView) findViewById(R.id.booking_date);
        textView_duration = (TextView) findViewById(R.id.booking_duration);
        textView_startTime = (TextView) findViewById(R.id.booking_startTime);
        textView_total = (TextView) findViewById(R.id.booking_total);
        textView_game = (TextView) findViewById(R.id.booking_game);
        companyAddress = (TextView) findViewById(R.id.company_address);
        companyName = (TextView) findViewById(R.id.company_name);
        fieldName = (TextView) findViewById(R.id.field_name);
        costPerHour = (TextView) findViewById(R.id.cost_per_hour);
        companyLogo = (ImageView) findViewById(R.id.logo_image);
        mProgressView = findViewById(R.id.proccess_indicator);
        mFormView = findViewById(R.id.form_container);
//        vouchersSpinner = (CustomSpinner) findViewById(R.id.voucher_spinner);
        voucher_panel_title = (TextView) findViewById(R.id.voucher_panel_title);
        voucherValue = (TextView) findViewById(R.id.voucher_value);
        subTotal_textView = (TextView) findViewById(R.id.subtotal_value);
        discount_textView = (TextView) findViewById(R.id.discount_value);
    }

    @Override
    public void submitForm() {
        booking.setNote(editText_note.getText().toString());
        findViewById(R.id.button_submit).setEnabled(false);
        if (booking.getDate() != null && booking.getDuration() != null && booking.getStartTime() != null)
            showConfirmationDialog();
        else
            Toast.makeText(fieldiumApplication, getResources().getString(R.string.select_valid_time), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getData() {

//        field = (Field) getIntent().getExtras().get("field");
        booking = fieldiumApplication.getBooking();
//        textView_startTime.setText(booking.getStartTime());
//        textView_duration.setText(booking.getDuration() + " Hour");
//        booking.setField(field);
//        fieldiumApplication.setBooking(booking);
        showData();
    }

    @Override
    public void showData() {
        companyName.setText(booking.getField().getCompany().getName());
        fieldName.setText(booking.getField().getName());
        costPerHour.setText(booking.getField().getHourRate() + " " + currency);
        companyAddress.setText(booking.getField().getCompany().getAddress().getName());

        try {
            Picasso.with(getmContext()).load(booking.getField().getCompany().getLogoURL())
                    .error(R.drawable.default_image)
//                        .fitCenter()
                    .placeholder(R.drawable.default_image)
                    .into(companyLogo);
        } catch (Exception e) {

        }
        calculateTotal(null);
        textView_date.setText(booking.getDate());
        textView_startTime.setText(booking.getStartTime());
        int intDuration = Integer.parseInt(booking.getDuration());
        String hDuration = (intDuration / 60) < 10 ? "0" + intDuration / 60 : intDuration / 60 + "";
        String mDuration = (intDuration % 60) < 10 ? "0" + intDuration % 60 : intDuration % 60 + "";
        textView_duration.setText(hDuration + ":" + mDuration);
//        textView_duration.setText((intDuration/60)<10?"0"+intDuration/60:intDuration/60 + ":"+(intDuration%60<10?"0"+intDuration%60:intDuration%60));
        textView_total.setText(booking.getTotal() + " " + currency);
        textView_game.setText(booking.getGame().getName());

    }

    @Override
    public void assignActions() {
//        textView_duration.setOnClickListener(this);
//        textView_date.setOnClickListener(this);
//        textView_startTime.setOnClickListener(this);
        voucher_panel_title.setOnClickListener(this);
    /*    vouchersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((Voucher) vouchersSpinner.getSelectedItem()).equals(typeVoucherCode)) {
                    //open dialog
                    showVoucherDialog();
                } else if (!((Voucher) vouchersSpinner.getSelectedItem()).equals(selectVoucher)) {
                    booking.setVoucher(((Voucher) vouchersSpinner.getSelectedItem()));
                    findViewById(R.id.voucher_panel).setVisibility(View.GONE);
                    findViewById(R.id.voucher_value_panel).setVisibility(View.VISIBLE);
                    findViewById(R.id.subtotal_panel).setVisibility(View.VISIBLE);
                    findViewById(R.id.discount_value_panel).setVisibility(View.VISIBLE);
                    calculateTotal(((Voucher) vouchersSpinner.getSelectedItem()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        super.assignActions();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voucher_panel_title:
//                vouchersSpinner.setVisibility(View.VISIBLE);
                showProgress(true);
                BookingController.getInstance(getmController()).getMyVouchers(new SuccessCallback<List<Voucher>>() {
                    @Override
                    public void OnSuccess(List<Voucher> result) {
                        showProgress(false);
                        vouchers = new ArrayList<Voucher>();
                        vouchers.addAll(result);
                        viewMyVouchers();
                      /*  selectVoucher = new Voucher();
                        selectVoucher.setCode("Select voucher");
                        selectVoucher.setId(-1);
                        vouchers.add(0, selectVoucher);
                        typeVoucherCode = new Voucher();
                        typeVoucherCode.setCode("Type code");
                        typeVoucherCode.setId(-2);
                        vouchers.add(vouchers.size(), typeVoucherCode);
                        ArrayAdapter<Voucher> voucherArrayAdapter = new BookingVouchersAdapter(getmContext(), R.layout.spinner_item, vouchers);
                        vouchersSpinner.setAdapter(voucherArrayAdapter);*/
                    }
                });
                break;
       /*     case R.id.booking_date:
                showDatePickerDialog(*//*v,*//* getSupportFragmentManager());
                break;
            case R.id.booking_startTime:
                showTimingActivity();
                break;*/
        }
        super.onClick(v);
    }

  /*  public void showTimingActivity() {

//        if (booking.getAvailableTimes().size() == 0) {
//            findViewById(R.id.proccess_indicator).setVisibility(View.VISIBLE);
//            FieldsController.getInstance(getmController()).CheckAvailability(booking.getField().getId(), booking.getDate(), new SuccessCallback<List<AvailableTime>>() {
//                @Override
//                public void OnSuccess(final List<AvailableTime> result) {
//                    booking.setAvailableTimes(result);
//                    ((FieldiumApplication) getApplication()).setBooking(booking);
//                    findViewById(R.id.proccess_indicator).setVisibility(View.GONE);
//                    Intent intent = new Intent(BookingActivity.this, TimingActivity.class);
//                    startActivityForResult(intent, 42);
//                }
//            });
//
//        } else {
        Intent intent = new Intent(BookingActivity.this, TimingActivity.class);
        startActivityForResult(intent, 42);
//        }

    }*/

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 42 && resultCode == RESULT_OK) {
            booking = fieldiumApplication.getBooking();
            field = booking.getField();
//            textView_date.setText(booking.getDate());
            textView_startTime.setText(booking.getStartTime());
            textView_duration.setText(booking.getDuration() + "");
            calculateTotal();
            textView_total.setText(booking.getTotal() + " " + getString(R.string.currancy));
        }
    }*/

    public void calculateTotal(Voucher mVoucher) {
        booking = fieldiumApplication.getBooking();

//        if (booking.getDuration() != null && booking.getField() != null) {
        if (mVoucher == null) {
            BigDecimal m60 = new BigDecimal(60);
            BigDecimal mDur = new BigDecimal(booking.getDuration());
            BigDecimal divideRes/*=(booking.getField().getHourRate()).divide(m60,BigDecimal.ROUND_HALF_EVEN)*/;
            try {
                divideRes = (booking.getField().getHourRate()).divide(m60);
            } catch (ArithmeticException e) {
                Log.d("Error bigdecimal", e.toString());
                divideRes = new BigDecimal((booking.getField().getHourRate()).doubleValue() / m60.doubleValue());
            }
            BigDecimal multiplyRes = divideRes.multiply(mDur);
            booking.setTotal(multiplyRes.setScale(0, BigDecimal.ROUND_HALF_EVEN));
            fieldiumApplication.setBooking(booking);
//        }
//        booking=fieldiumApplication.getBooking();
        } else if (mVoucher.getType() == 2) {
            BigDecimal m60 = new BigDecimal(60);
            BigDecimal mDur = new BigDecimal(Integer.parseInt(booking.getDuration()) - mVoucher.getValue());
            BigDecimal discountValue = new BigDecimal(mVoucher.getValue());
            BigDecimal divideRes/*=(booking.getField().getHourRate()).divide(m60,BigDecimal.ROUND_HALF_EVEN)*/;
            try {
                divideRes = (booking.getField().getHourRate()).divide(m60);
            } catch (ArithmeticException e) {
                Log.d("Error bigdecimal", e.toString());
                divideRes = new BigDecimal((booking.getField().getHourRate()).doubleValue() / m60.doubleValue());
            }
            BigDecimal multiplyRes = divideRes.multiply(mDur);
            discountValue = divideRes.multiply(discountValue).setScale(0, BigDecimal.ROUND_UP);
            BigDecimal total = multiplyRes.setScale(0, BigDecimal.ROUND_HALF_EVEN);
            if (total.compareTo(BigDecimal.ZERO) < 0)
                booking.setTotal(new BigDecimal(0));
            else
                booking.setTotal(total);
            fieldiumApplication.setBooking(booking);

            String hDuration = (mVoucher.getValue() / 60) < 10 ? "0" + mVoucher.getValue() / 60 : mVoucher.getValue() / 60 + "";
            String mDuration = (mVoucher.getValue() % 60) < 10 ? "0" + mVoucher.getValue() % 60 : mVoucher.getValue() % 60 + "";
            voucherValue.setText(hDuration + ":" + mDuration + " " + getResources().getString(R.string.free));
            textView_total.setText(booking.getTotal() + " " + currency);
            subTotal_textView.setText((divideRes.multiply(new BigDecimal(booking.getDuration()))).
                    setScale(0, BigDecimal.ROUND_HALF_EVEN) + " " + currency);
            String lang = Locale.getDefault().getLanguage();
            if (lang.equals("ar"))
                discount_textView.setText(discountValue + "-"+" "+currency);
            else
                discount_textView.setText("-" + discountValue+" "+currency);
            discount_textView.setPaintFlags(discount_textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else if (mVoucher.getType() == 1) {
            BigDecimal m60 = new BigDecimal(60);
            BigDecimal mDur = new BigDecimal(booking.getDuration());
            BigDecimal divideRes/*=(booking.getField().getHourRate()).divide(m60,BigDecimal.ROUND_HALF_EVEN)*/;
            try {
                divideRes = (booking.getField().getHourRate()).divide(m60);
            } catch (ArithmeticException e) {
                Log.d("Error bigdecimal", e.toString());
                divideRes = new BigDecimal((booking.getField().getHourRate()).doubleValue() / m60.doubleValue());
            }
            BigDecimal multiplyRes = divideRes.multiply(mDur);
            BigDecimal subTotal = multiplyRes.setScale(0, BigDecimal.ROUND_HALF_EVEN);
            BigDecimal discountValue = subTotal.multiply(new BigDecimal(mVoucher.getValue())).divide(new BigDecimal(100));
            String lang = Locale.getDefault().getLanguage();
            if (lang.equals("ar"))
                discount_textView.setText(discountValue + "-"+ " " + currency);
            else
                discount_textView.setText("-" + discountValue+ " " + currency);
            discount_textView.setPaintFlags(discount_textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            BigDecimal total = subTotal.subtract(discountValue);
            if (total.compareTo(BigDecimal.ZERO) < 0)
                booking.setTotal(new BigDecimal(0));
            else
                booking.setTotal(total);
            fieldiumApplication.setBooking(booking);
            voucherValue.setText(mVoucher.getValue() + "%");
            textView_total.setText(booking.getTotal() + " " + currency);
            subTotal_textView.setText(subTotal + " " + currency);
        }
    }

    public void sendTokenToserver() {
        showProgress(true);
        PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                if (findViewById(R.id.button_submit) != null)
                    findViewById(R.id.button_submit).setEnabled(true);
                if (findViewById(R.id.register_button) != null)
                    findViewById(R.id.register_button).setEnabled(true);
                if (findViewById(R.id.skip_button) != null)
                    findViewById(R.id.skip_button).setEnabled(true);
                showProgress(false);
                if (errorCode == Code.AuthenticationError || Message.equals("Not authorized")) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    ComponentName cn = intent.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                    startActivity(mainIntent);
                    UserUtils.getInstance(getmContext()).SignOut();
                    UserUtils.getInstance(getmContext()).DeletePhone();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                    sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                    sharedPreferences.edit().putString("file_path", "").apply();

                } else if (findViewById(R.id.parent_panel) != null) {
                    if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError) {
                        showSnackBar(Html.fromHtml(Message).toString());
                        if (findViewById(R.id.number_buttons_layout) != null) {
                            findViewById(R.id.number_buttons_layout).setVisibility(View.GONE);
                        }
                    } else {
                        Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();

                    }
                } else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
            }
        })).refreshToken(token, new SuccessCallback<Player>() {
            @Override
            public void OnSuccess(Player result) {
                final Player player = UserUtils.getInstance(getApplicationContext()).Get();
                player.setToken(token);
                UserUtils.getInstance(getApplicationContext()).Save(player);
                sharedPreferences.edit().putString("user_token_sent_to_server", token).apply();

                BookingController.getInstance(getmController()).createBook(booking, new SuccessCallback<Booking>() {
                    @Override
                    public void OnSuccess(Booking result) {
//                        String bookingState=result.getState().toString();
                        if (result.getState() == BookingStatus.Approved)
                            Toast.makeText(BookingActivity.this, "Your booking has been approved", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(BookingActivity.this, "Your booking has been sent", Toast.LENGTH_LONG).show();
                        fieldiumApplication.setBooking(new Booking());
                        Intent intent = new Intent(BookingActivity.this, BookingListActivity.class);
                        intent.putExtra("redirectToHomeScreen", true);
                        startActivity(intent);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void OnDialogClosed(/*int resId,*/ int year, int month, int day) {
        if (year == 0 && month == 0 && day == 0) {
        } else {
            booking = fieldiumApplication.getBooking();
            final String date = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
            textView_date.setText(date);
           /* findViewById(R.id.proccess_indicator).setVisibility(View.VISIBLE);
            FieldsController.getInstance(getmController()).CheckAvailability(booking.getField().getId(), date, new SuccessCallback<List<AvailableTime>>() {
                @Override
                public void OnSuccess(final List<AvailableTime> result) {
                    booking.setAvailableTimes(result);

                    findViewById(R.id.proccess_indicator).setVisibility(View.GONE);

                    if (result.size() != 0) {
                        booking.setDate(date);
                        booking.setStartTime(result.get(0).getStart());
                        booking.setDuration("1");
                        textView_startTime.setText(booking.getStartTime());
                        textView_duration.setText(booking.getDuration() + " Hour");
                        fieldiumApplication.setBooking(booking);
                    } else {
                        booking.setStartTime(null);
                        booking.setDuration(null);
                        textView_startTime.setText("-");
                        textView_duration.setText("-");
                        Toast.makeText(fieldiumApplication, "Field isn't valid in selected day", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }

    public void showConfirmationDialog() {
        int intDuration = Integer.parseInt(booking.getDuration());
        String hDuration = (intDuration / 60) < 10 ? "0" + intDuration / 60 : intDuration / 60 + "";
        String mDuration = (intDuration % 60) < 10 ? "0" + intDuration % 60 : intDuration % 60 + "";

        alertDialog = new AlertDialog.Builder(getmContext())
                .setTitle(getResources().getString(R.string.confirmation))
                .setMessage(getResources().getString(R.string.you_about_to_reserve) + "\n" +
                        "\"" + booking.getField().getName() + "\""
                        + "\n" + getResources().getString(R.string.date) + ": " + booking.getDate() +
                        "\n" + getResources().getString(R.string.start) + ": " + booking.getStartTime() +
                        "\n" + getResources().getString(R.string.duration) + ": " + hDuration + ":" + mDuration +
                        "\n\n" + getResources().getString(R.string.total) + ": " + booking.getTotal() + " " + currency)

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        ////Booking process

                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        token = sharedPreferences.getString("token", "123");
                        sentToken = sharedPreferences.getString("user_token_sent_to_server", "");
                        if (token.equals("123")) {
                            if (!checkPlayServices()) {
                                Toast.makeText(BookingActivity.this, getResources().getString(R.string.update_google_play_services), Toast.LENGTH_LONG).show();
                                finish();
                                return;

                            } else {
                                showProgress(true);
                                BookingController.getInstance(getmController()).createBook(booking, new SuccessCallback<Booking>() {
                                    @Override
                                    public void OnSuccess(Booking result) {
                                        if (result.getState() == BookingStatus.Approved)
                                            Toast.makeText(BookingActivity.this, "Your booking has been approved", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(BookingActivity.this, "Your booking has been sent", Toast.LENGTH_SHORT).show();
                                        fieldiumApplication.setBooking(new Booking());
                                        Intent intent = new Intent(BookingActivity.this, BookingListActivity.class);
                                        intent.putExtra("show_no_token_problem", true);
                                        intent.putExtra("redirectToHomeScreen", true);
                                        startActivity(intent);
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                });
                            }
                        } else {
                            if (token.equals(sentToken)) {
                                showProgress(true);
                                BookingController.getInstance(getmController()).createBook(booking, new SuccessCallback<Booking>() {
                                    @Override
                                    public void OnSuccess(Booking result) {
                                        if (result.getState() == BookingStatus.Approved)
                                            Toast.makeText(BookingActivity.this, "Your booking has been approved", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(BookingActivity.this, "Your booking has been sent", Toast.LENGTH_SHORT).show();
                                        fieldiumApplication.setBooking(new Booking());
                                        Intent intent = new Intent(BookingActivity.this, BookingListActivity.class);
                                        intent.putExtra("redirectToHomeScreen", true);
                                        startActivity(intent);
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                });
                            } else {
                                sendTokenToserver();
                            }
                        }
                        ////

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        findViewById(R.id.button_submit).setEnabled(true);

                    }
                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        findViewById(R.id.button_submit).setEnabled(true);
                    }
                })
                .show();

        TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
        final int titleId = getResources().getIdentifier("alertTitle", "id", "android");
        TextView title = (TextView) alertDialog.findViewById(titleId);
        if (title != null) {
            title.setGravity(Gravity.CENTER);
        }
//        TextView titleView = (TextView)alertDialog.findViewById(android.R.id.title);
        messageView.setGravity(Gravity.CENTER);
//        title.setGravity(Gravity.CENTER);
        final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
//        positiveButtonLL.gravity = Gravity.CENTER;
        final Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negativeButtonLL = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();

        positiveButton.setTextColor(getResources().getColor(R.color.colorAccent));
        negativeButton.setTextColor(getResources().getColor(R.color.colorAccent));
        positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        negativeButton.setVisibility(View.GONE);
////        negativeButtonLL.width = ViewGroup.LayoutParams.WRAP_CONTENT;
////        positiveButtonLL.weight=1;
//        negativeButtonLL.weight=0.5f;
        positiveButton.setLayoutParams(positiveButtonLL);
//        negativeButton.setLayoutParams(negativeButtonLL);

    }

    public void showVoucherDialog() {
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.voucher_dialog);
        //set width match parent
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = myDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        // Focus and show keyboard
        myDialog.findViewById(R.id.textView_voucher_code).requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        myDialog.setCancelable(false);
        final Button checkVoucher = (Button) myDialog.findViewById(R.id.check_voucherButton);

        checkVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVoucherChecked) {
                    final EditText voucherCode = (EditText) myDialog.findViewById(R.id.textView_voucher_code);
                    final TextView voucherMessage = (TextView) myDialog.findViewById(R.id.voucher_message);
                    voucher_code = voucherCode.getText().toString();
                    ///
                    EditText focusView = null;
                    try {
                        voucherCode.setError(null);
                        // String voucher_code = voucherCode.getText().toString();
                        focusView = voucherCode;
                        isVoucherCodeValid(voucher_code);
                        ///
                        myDialog.findViewById(R.id.proccess_indicator).setVisibility(View.VISIBLE);
                        myDialog.findViewById(R.id.form_container).setVisibility(View.GONE);

                        BookingController.getInstance(getmController()).checkVoucher(voucher_code, booking.getField().getId(), booking.getDate(),
                                booking.getDuration(), booking.getStartTime(), booking.getGame().getId(), new SuccessCallback<Voucher>() {
                                    @Override
                                    public void OnSuccess(Voucher result) {
                                        voucher = result;
                                        voucherMessage.setVisibility(View.VISIBLE);
                                        myDialog.findViewById(R.id.proccess_indicator).setVisibility(View.GONE);
                                        myDialog.findViewById(R.id.form_container).setVisibility(View.VISIBLE);
                                        if (voucher.getValid() == 0/*getValue().compareTo(BigDecimal.ZERO) == 0*/) {
                                            voucherMessage.setText(voucher.getMessage());
                                            voucher.setCode("");
                                        } else {
                                            if (voucher.getType() == 1) {
//                                                voucher.setValue(voucher.getValue());
                                                voucherMessage.setText(voucher.getMessage() + " " + voucher.getValue() + "%");
                                            } else if (voucher.getType() == 2) {
                                                String hDuration = (voucher.getValue() / 60) < 10 ? "0" + voucher.getValue() / 60 : voucher.getValue() / 60 + "";
                                                String mDuration = (voucher.getValue() % 60) < 10 ? "0" + voucher.getValue() % 60 : voucher.getValue() % 60 + "";
                                                voucherMessage.setText(voucher.getMessage() + " " + hDuration + ":" + mDuration + "\n" + getResources().getString(R.string.free));
//                                                voucherMessage.setText(voucher.getMessage() + " " + voucher.getValue() + " " + getString(R.string.currancy));
                                            }
                                            voucherCode.setEnabled(false);
                                            checkVoucher.setText(getResources().getString(R.string.apply_voucher));
                                            isVoucherChecked = true;
                                        }
                                    }
                                });
                    } catch (InvalidInputException e) {
                        focusView.setError(e.getMessage());
                        focusView.requestFocus();
                    }

                } else if (isVoucherChecked) {
                    voucher.setCode(voucher_code);
                    booking.setVoucher(voucher);
                    findViewById(R.id.voucher_panel).setVisibility(View.GONE);
                    findViewById(R.id.voucher_value_panel).setVisibility(View.VISIBLE);
                    findViewById(R.id.subtotal_panel).setVisibility(View.VISIBLE);
                    findViewById(R.id.discount_value_panel).setVisibility(View.VISIBLE);
                    calculateTotal(voucher);
//                    findViewById(R.id.total_TableRow).setVisibility(View.VISIBLE);
                    myDialog.dismiss();
                    isVoucherChecked = false;
                    isVoucherApplied = true;
                }
            }
        });
        Button cancel = (Button) myDialog.findViewById(R.id.voucher_cancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voucher.setCode("");
                isVoucherChecked = false;
//                vouchersSpinner.setSelection(0);
                myDialog.dismiss();
            }
        });
        myDialog.show();
        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });
     /*   myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (!isVoucherChecked) {
                    vouchersSpinner.setSelection(0);
                }

            }
        });*/
    }

    private void isVoucherCodeValid(String vocher_code) throws InvalidInputException {
        //TODO: Replace this with your own logic
        if (TextUtils.isEmpty(vocher_code)) {
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
        }
    }

    @Override
    public void showSnackBar(String Message) {
        View view = findViewById(R.id.parent_panel);

        if (myDialog != null && myDialog.isShowing()) {
            view = myDialog.findViewById(R.id.parent_panel);
            mFormView = myDialog.findViewById(R.id.form_container);
            mProgressView = myDialog.findViewById(R.id.proccess_indicator);
        }
        Snackbar snackbar = Snackbar
                .make(view/*getWindow().getDecorView().getRootView()*/, Message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.color_primary))
                .setAction(getResources().getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (myDialog != null && myDialog.isShowing()) {
                            myDialog.findViewById(R.id.proccess_indicator).setVisibility(View.GONE);
                            myDialog.findViewById(R.id.form_container).setVisibility(View.VISIBLE);
                        } else
                            recreate();
                    }
                });

        snackbar.show();
    }

    public void viewMyVouchers() {
        if (vouchers.size() != 0) {
            ((LinearLayout) findViewById(R.id.my_vouchers_panel)).removeAllViews();
            for (int i = 0; i < vouchers.size(); i++) {
                TextView imageView = new TextView(this);
                TextView textView = new TextView(this);
//
                imageView.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
                layoutParams.gravity = Gravity.CENTER;
                imageView.setLayoutParams(layoutParams);
                imageView.setGravity(Gravity.CENTER);
                imageView.setBackground(getResources().getDrawable(R.drawable.circular_textview));
                if (vouchers.get(i).getType() == 2) {
                    String hDuration = (vouchers.get(i).getValue() / 60) < 10 ? "0" + vouchers.get(i).getValue() / 60 : vouchers.get(i).getValue() / 60 + "";
                    String mDuration = (vouchers.get(i).getValue() % 60) < 10 ? "0" + vouchers.get(i).getValue() % 60 : vouchers.get(i).getValue() % 60 + "";
                    imageView.setText(hDuration + ":" + mDuration + "\n" + getResources().getString(R.string.free));
                } else {
                    imageView.setText(vouchers.get(i).getValue() + "%");
                }
                textView.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams1);
                textView.setPadding(10, 10, 10, 10);
                textView.setGravity(Gravity.CENTER);
                textView.setText(daysLeft(vouchers.get(i).getExpiry_date()) + " " + getResources().getString(R.string.days_left)
                        + "\n" + vouchers.get(i).getCode());
                LinearLayout linearLayout = new LinearLayout(this);

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams2.setMargins(5, 5, 5, 5);
                linearLayout.setLayoutParams(layoutParams2);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
//            int t=linearLayout.getChildCount();
                linearLayout.addView(textView, 0);
                linearLayout.addView(imageView, 0);

                final int finalI = i;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        booking.setVoucher(vouchers.get(finalI));
                        findViewById(R.id.voucher_panel).setVisibility(View.GONE);
                        findViewById(R.id.voucher_value_panel).setVisibility(View.VISIBLE);
                        findViewById(R.id.subtotal_panel).setVisibility(View.VISIBLE);
                        findViewById(R.id.discount_value_panel).setVisibility(View.VISIBLE);
                        calculateTotal(vouchers.get(finalI));
                    }
                });

//            int t1=linearLayout.getChildCount();
                ((LinearLayout) findViewById(R.id.my_vouchers_panel)).addView(
                        linearLayout, i);
            }
            //type code view
            TextView imageView = new TextView(this);
            TextView textView = new TextView(this);
//
            imageView.setTextColor(getResources().getColor(R.color.color_primary));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
            layoutParams.gravity = Gravity.CENTER;
            imageView.setLayoutParams(layoutParams);
            imageView.setGravity(Gravity.CENTER);
            imageView.setBackground(getResources().getDrawable(R.drawable.circular_textview));
            imageView.setText("+");
            imageView.setTextSize(30);

            textView.setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams1);
            textView.setPadding(10, 10, 10, 10);
            textView.setGravity(Gravity.CENTER);
            textView.setText(getResources().getString(R.string.type_voucher_code));
            LinearLayout linearLayout = new LinearLayout(this);

            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(5, 5, 5, 5);
            linearLayout.setLayoutParams(layoutParams2);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
//            int t=linearLayout.getChildCount();
            linearLayout.addView(textView, 0);
            linearLayout.addView(imageView, 0);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showVoucherDialog();
                }
            });

            ((LinearLayout) findViewById(R.id.my_vouchers_panel)).addView(
                    linearLayout, vouchers.size());
        } else {
            showVoucherDialog();
        }

    }

}
