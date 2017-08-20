package com.bluebrand.fieldium.view.Player;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static java.util.concurrent.TimeUnit.*;

import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Image;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.Booking.HomeActivity;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.MasterFormActivity;
import com.bluebrand.fieldium.R;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerficationActivity extends MasterFormActivity {
    /*private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;*/
    // UI references.
    private EditText mVerificationCodeView;
    TextView enterCode_textView, getNewCode_textView, skip_textView, textView_changeNumber;
    String verificationCode, phoneNumber, serverId;
    boolean redirectToFieldDetailsActivity = false;
//    Class activity;
    /*  Activity activity;
    Class<? extends Activity> ActivityToOpen;

    public void ActivityToOPen (final Class<? extends Activity> ActivityToOpen){
        this.ActivityToOpen =ActivityToOpen;
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setLanguage();
        setContentView(R.layout.activity_verfication);
        super.onCreate(savedInstanceState);
//        setTitle(getTitle());
        setTitleText(getTitle());
        getData();
        showData();
    }

    @Override
    public void assignUIRefrences() {
        mVerificationCodeView = (EditText) findViewById(R.id.editeText_verification_code);
        mFormView = findViewById(R.id.form_container);
        mProgressView = findViewById(R.id.proccess_indicator);
        enterCode_textView = (TextView) findViewById(R.id.enterCode_textView);
        textView_changeNumber = (TextView) findViewById(R.id.change_number);
        skip_textView = (TextView) findViewById(R.id.skip_button);
        getNewCode_textView = (TextView) findViewById(R.id.get_new_code);
        Long counterTime = getCounterTime();/*UserUtils.getInstance(getmContext()).GetCounterStartTimeForNewCode();*/
//        Long zero = MILLISECONDS.convert(0, SECONDS);
//        if (counterTime > zero) {
        getNewCode_textView.setClickable(false);
        getNewCode_textView.setEnabled(false);
        getNewCode_textView.setTextColor(getResources().getColor(R.color.bright_gray));
        new CountDownTimer(counterTime, 1000) {
            public void onTick(long millisUntilFinished) {
                getNewCode_textView.setText(/*"seconds remaining: " + millisUntilFinished / 1000*/
                        "" + String.format(Locale.ENGLISH, "%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
//                    UserUtils.getInstance(getmContext()).SetCounterStartTimeForNewCode(millisUntilFinished);
            }

            public void onFinish() {
                UserUtils.getInstance(getmContext()).DeleteCalendarCounter();
                getNewCode_textView.setText(getResources().getString(R.string.get_new_number));
                getNewCode_textView.setClickable(true);
                getNewCode_textView.setEnabled(true);
                getNewCode_textView.setTextColor(getResources().getColor(R.color.color_primary));
            }
        }.start();
//        }
    }

    @Override
    public void getData() {
        phoneNumber = getIntent().getExtras().getString("mobile");
        serverId = getIntent().getExtras().getString("serverId");
        redirectToFieldDetailsActivity = getIntent().getExtras().getBoolean("fieldDetailsActivity", false);

//        activity = (Class) getIntent().getExtras().get("from_context");

   /*     if (activity != null) {
            Intent intent = new Intent(VerficationActivity.this, activity);
            startActivity(intent);
        }
*/
    }

    @Override
    public void showData() {
        enterCode_textView.setText(getResources().getString(R.string.enter_code_sent_to_phone) + "\n" + phoneNumber);
    }

    @Override
    public void assignActions() {
        skip_textView.setOnClickListener(this);
        getNewCode_textView.setOnClickListener(this);
        textView_changeNumber.setOnClickListener(this);
        super.assignActions();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.skip_button) {
            if (/*activity != null*/redirectToFieldDetailsActivity) {
              /*  int fieldId = getIntent().getExtras().getInt("field_id");
                Intent intent = new Intent(VerficationActivity.this, activity);
                intent.putExtra("field_id", fieldId);
                startActivity(intent);*/
                finish();
            } else {
                Intent intent1 = new Intent(VerficationActivity.this, HomeActivity.class);
                startActivity(intent1);
            }
        } else if (v.getId() == R.id.get_new_code) {
//            UserUtils.getInstance(getmContext()).SetCounterStartTimeForNewCode(Calendar.getInstance());

            showProgress(true);
            PlayerController.getInstance(getmController()).requestActivationCode(phoneNumber, serverId, new SuccessCallback<Player>() {
                @Override
                public void OnSuccess(Player result) {
                    showProgress(false);
                    UserUtils.getInstance(getmContext()).SetCounterStartCalendarForNewCode(Calendar.getInstance());
                    getNewCode_textView.setClickable(false);
                    getNewCode_textView.setEnabled(false);
                    getNewCode_textView.setTextColor(getResources().getColor(R.color.bright_gray));
                    Long counterTime = getCounterTime();/*UserUtils.getInstance(getmContext()).GetCounterStartTimeForNewCode();*/
                    new CountDownTimer(counterTime, 1000) {
                        public void onTick(long millisUntilFinished) {
                            getNewCode_textView.setText(/*"seconds remaining: " + millisUntilFinished / 1000*/
                                    "" + String.format("%d min, %d sec",
                                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
//                            UserUtils.getInstance(getmContext()).SetCounterStartTimeForNewCode(millisUntilFinished);
                        }

                        public void onFinish() {
                            UserUtils.getInstance(getmContext()).DeleteCalendarCounter();
                            getNewCode_textView.setText(getResources().getString(R.string.get_new_number));
                            getNewCode_textView.setClickable(true);
                            getNewCode_textView.setEnabled(true);
                            getNewCode_textView.setTextColor(getResources().getColor(R.color.color_primary));
                        }
                    }.start();
                }
            });
        } else if (v.getId() == R.id.change_number) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            ComponentName cn = intent.getComponent();
            Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
            startActivity(mainIntent);
            UserUtils.getInstance(getmContext()).SignOut();
            UserUtils.getInstance(getmContext()).DeletePhone();
            UserUtils.getInstance(getmContext()).DeleteCalendarCounter();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
            sharedPreferences.edit().putString("file_path", "").apply();
        }
        super.onClick(v);
    }

    public Long getCounterTime() {
        Calendar calendar_current = Calendar.getInstance();
        Calendar calendar_past = UserUtils.getInstance(getmContext()).GetCounterStartCalendarForNewCode();
        Date test1 = calendar_past.getTime();
        Date test2 = calendar_current.getTime();
        long difference = test2.getTime() - test1.getTime();
//        if (difference<0) difference=-difference;
        long MAX_DURATION = MILLISECONDS.convert(15, MINUTES);
//        int Hours = (int) difference / (1000 * 60 * 60);
//        int Mins = (int) difference % (1000 * 60 * 60);
//        int days = (int) (difference / (1000 * 60 * 60 * 24));
//        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
//        int Mins = (int)difference/(1000*60);
//        if (Mins < 0)
//            Mins = -Mins;
        if (difference >= MAX_DURATION || difference <= 0)
            return Long.valueOf("0");
        else return MAX_DURATION - difference;

//        return UserUtils.getInstance(getmContext()).GetCounterStartTimeForNewCode();
    }

    @Override
    public void submitForm() {
        mVerificationCodeView.setError(null);

        // Store values at the time of the login attempt.
        verificationCode = mVerificationCodeView.getText().toString();

        View focusView = null;

        try {
            focusView = mVerificationCodeView;
            isCodeValid(verificationCode);
            showProgress(true);

            PlayerController.getInstance(getmController()).ActivateUser(phoneNumber, verificationCode, new SuccessCallback<Player>() {
                @Override
                public void OnSuccess(Player player) {
                    player.setPhone(phoneNumber);
                    player.setServerId(serverId);
                    player.setLanguage(Locale.getDefault().getLanguage().equals("ar") ? "ar" : "en");
                    UserUtils.getInstance(VerficationActivity.this).Save(player);
                    if (/*activity != null*/redirectToFieldDetailsActivity) {
//                        int fieldId = getIntent().getExtras().getInt("field_id");
//                        Intent intent = new Intent(VerficationActivity.this, activity);
//                        intent.putExtra("field_id", fieldId);
                        sendTokenToserver();
//                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(VerficationActivity.this, HomeActivity.class);
                        sendTokenToserver();
                        startActivity(intent);
                        finish();


                    }
                }
            });

        } catch (InvalidInputException e) {
            mVerificationCodeView.setError(e.getMessage());
            focusView.requestFocus();
        }
    }

    private void isCodeValid(String verificationCode) throws InvalidInputException {
        //TODO: Replace this with your own logic
        if (TextUtils.isEmpty(verificationCode)) {
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
        } else if (!verificationCode.matches("[0-9]+")) {
            throw new InvalidInputException(getResources().getString(R.string.invalid_code));
        } else if (verificationCode.length() != 6) {
            throw new InvalidInputException(getResources().getString(R.string.invalid_code));
        }
    }

    public void sendTokenToserver() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String token = sharedPreferences.getString("token", "123");
        final String sentToken = sharedPreferences.getString("user_token_sent_to_server", "");
        if (!token.equals("123") /*&& !sentToken.equals("")*/ && !token.equals(sentToken)) {
            PlayerController.getInstance(new Controller(getApplicationContext(), new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message) {
                    sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                }
            })).refreshToken(token, new SuccessCallback<Player>() {
                @Override
                public void OnSuccess(Player result) {
                    final Player player = UserUtils.getInstance(getApplicationContext()).Get();
                    player.setToken(token);
                    UserUtils.getInstance(getApplicationContext()).Save(player);
                    sharedPreferences.edit().putString("user_token_sent_to_server", token).apply();
                }
            });
        }
    }
}
