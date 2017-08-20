package com.bluebrand.fieldium.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldium.view.Player.VerficationActivity;
import com.bluebrand.fieldium.view.other.DatePickerFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.other.TextUtils;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by malek on 5/15/16.
 */
public abstract class MasterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";


    private Context mContext;
    private Controller mController;
    int contentViewRes;
    protected View mProgressView;
    protected View mFormView;
    protected Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(contentViewRes);

        mContext = this;

        defineController();
        assignUIRefrences();
        assignActions();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Controller getmController() {
        return mController;
    }

    public void setmController(Controller mController) {
        this.mController = mController;
    }

    @Override
    public void setContentView(int viewRes) {
        this.contentViewRes = viewRes;
    }

    protected abstract void getData();

    protected abstract void showData();

    @Override
    public void setTitle(CharSequence title) {
        setTitleText(title);
        showBackButton();
    }

    public void setTitleText(CharSequence title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        if (mTitle != null) {
            mTitle.setTypeface(TextUtils.getFont(this));
            mTitle.setText(title);
        }
        setSupportActionBar(toolbar);
        super.setTitle("");
    }

    void showBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    public abstract void assignUIRefrences();

    protected abstract void assignActions();


    public void defineController() {
        mController = new Controller(this, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                if (findViewById(R.id.swipeRefreshLayout) != null)
                    ((SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout)).setRefreshing(false);
                if (findViewById(R.id.deactivate_account) != null) {
                    findViewById(R.id.deactivate_account).setVisibility(View.VISIBLE);
                    findViewById(R.id.deactivate_account).setEnabled(true);
                }
                if (findViewById(R.id.button_submit) != null)
                    findViewById(R.id.button_submit).setEnabled(true);
                if (mFormView != null && mProgressView != null)
                    showProgress(false);
                if (errorCode == Code.AuthenticationError || Message.equals("Not authorized")) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    ComponentName cn = intent.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                    startActivity(mainIntent);
                    UserUtils.getInstance(getmContext()).SignOut();
                    UserUtils.getInstance(getmContext()).DeletePhone();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                    sharedPreferences.edit().putString("file_path", "").apply();
                } else if (findViewById(R.id.parent_panel) != null) {
                    if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError)
                        showSnackBar(Html.fromHtml(Message).toString());
                    else if (Message.equals("Invalid date") && findViewById(R.id.availableTimes_listView) != null) {
                        findViewById(R.id.availableTimes_listView).setVisibility(View.GONE);
                        Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
                    } else
                        Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();

                } else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();

            }

        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void showSnackBar(String Message) {
        snackbar = Snackbar
                .make(findViewById(R.id.parent_panel)/*getWindow().getDecorView().getRootView()*/, Message, Snackbar.LENGTH_INDEFINITE)
//                .setActionTextColor(getResources().getColor(R.color.white_bg))
                .setActionTextColor(getResources().getColor(R.color.color_primary))
                .setAction(getResources().getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* Intent intent = new Intent(*//*context, context.getClass()*//*getIntent());
                        finish();
                        startActivity(intent);*/
                        recreate();

                    }
                });

        snackbar.show();
    }

    protected void showMessageInToast(String message) {
        Toast.makeText(getApplicationContext(), Html.fromHtml(message), Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInToast(int messageRes) {
        Toast.makeText(getApplicationContext(), messageRes, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInSnackbar(String message) {
        if (findViewById(R.id.parent_panel) != null)
            Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(message), Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInSnackbar(int messageRes) {
        if (findViewById(R.id.parent_panel) != null)
            Snackbar.make(findViewById(R.id.parent_panel), messageRes, Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), messageRes, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void showDatePickerDialog(/*View v,*/ FragmentManager supportFragmentManager) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle arg = new Bundle();
//        arg.putInt("viewId", v.getId());
        newFragment.setArguments(arg);
        newFragment.setCancelable(false);
        //Get yesterday's date

//        newFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.datepicker);
        newFragment.show(supportFragmentManager, "datePicker");
    }

    public void redirectToRegistrationView() {
        if (UserUtils.getInstance(this).IsRegistered()) {
            String phone = UserUtils.getInstance(this).GetPhone();
            Intent intent = new Intent(getmContext(), VerficationActivity.class);
            intent.putExtra("mobile", phone);
            intent.putExtra("serverId", UserUtils.getInstance(this).getServerId());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    public boolean checkSelectedTimeRange(String selectedTime, String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);

        try {
            Date mStart = dateFormat.parse(dateFormat.format(dateFormat1.parse(start)));
            /*Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);*/

            Date mEnd = dateFormat.parse(dateFormat.format(dateFormat1.parse(end)));
           /* Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);*/


            Date mSelectedTime = dateFormat.parse(dateFormat.format(dateFormat1.parse(selectedTime)));
       /*     Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(mSelectedTime);*/
//            if (calendar2.get(Calendar.MINUTE) == 59) {
//                calendar3.set(Calendar.MINUTE, calendar3.get(Calendar.MINUTE)-1);
//
////                time2 = dateFormat.parse(dateFormat.format(calendar2.getTime()));
////                calendar2.setTime(time2);
//            }
      /*      Date _start = calendar1.getTime();
            Date _end = calendar2.getTime();
            Date selected = calendar3.getTime();*/

            if ((mSelectedTime.after(mStart) || mSelectedTime.compareTo(mStart) == 0) &&
                    (mSelectedTime.before(mEnd) || /*checkCompare0(calendar3,calendar2)*/mSelectedTime.compareTo(mEnd) == 0)) {
                return true;
            } else
                return false;
        } catch (ParseException e) {
//            Toast.makeText(fieldiumApplication, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    protected void setLanguage() {
        SharedPreferences lang = this.getSharedPreferences("lang", Context.MODE_PRIVATE);
        Locale locale;
        String localLang = lang.getString("value", "");
        if (localLang.equals("en")) {
            locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);/*locale = locale;*/
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        } else if (localLang.equals("ar")) {
            locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);/*locale = locale;*/
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
    }

    public int daysLeft(String expiryDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date d = null;
        try {
            d = formatter.parse(expiryDate);//catch exception


            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(d);

            Calendar today = Calendar.getInstance();

            long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            return (int) days < 0 ? -1 * (int) days : (int) days;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getCurrency() {
        String lang = Locale.getDefault().getLanguage();
        String currency = "";
        if (lang.equals("ar"))
            currency = UserUtils.getInstance(getmContext()).getCountry().getArabicCurrency();
        else
            currency = UserUtils.getInstance(getmContext()).getCountry().getEnglishCurrency();
        return currency;
    }

}
