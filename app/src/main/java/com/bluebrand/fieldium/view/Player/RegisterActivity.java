package com.bluebrand.fieldium.view.Player;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldium.FieldiumApplication;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.Booking.HomeActivity;
import com.bluebrand.fieldium.view.InvalidInputException;
import com.bluebrand.fieldium.view.MasterFormActivity;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.view.adapter.AreaAdapter;
import com.bluebrand.fieldium.view.adapter.CountriesAdapter;
import com.bluebrand.network.Code;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.SuccessCallback;

import java.util.List;


/**
 * Created by player on 12/18/16.
 */
public class RegisterActivity
        extends MasterFormActivity {

    String phoneNumber, name;
    Spinner countriesSpinner;
    ProgressBar progressBar_country;
    ImageView refresh_countries;
    Country country;
    // UI references.
    private EditText mPhoneNumberView, mNameView;
    Button mRegiterButton;
    boolean redirectToFieldDetailsActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        getData();
        checkPlayServices();
    }

    @Override
    public void getData() {
        if (getIntent().getExtras() != null) {
            redirectToFieldDetailsActivity = getIntent().getExtras().getBoolean("fieldDetailsActivity", false);
        }
        refreshCountries();
    }

    @Override
    public void assignUIRefrences() {
        mPhoneNumberView = (EditText) findViewById(R.id.editText_phone_number);
        mFormView = findViewById(R.id.form_container);
        mProgressView = findViewById(R.id.proccess_indicator);
        mRegiterButton = (Button) findViewById(R.id.register_button);
        mNameView = (EditText) findViewById(R.id.editText_name);
        countriesSpinner = (Spinner) findViewById(R.id.spinner_country);
        progressBar_country = (ProgressBar) findViewById(R.id.progressLoading_country);
        refresh_countries = (ImageView) findViewById(R.id.refresh_countries_button);
    }

    @Override
    public void assignActions() {
        findViewById(R.id.skip_button).setOnClickListener(this);
        refresh_countries.setOnClickListener(this);
        countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country=(Country) countriesSpinner.getSelectedItem();
                ((TextView)findViewById(R.id.country_code)).setText(country.getDialingCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mRegiterButton.setOnClickListener(this);
    }

    @Override
    public void submitForm() {
        mPhoneNumberView.setError(null);
        phoneNumber = mPhoneNumberView.getText().toString();
        mNameView.setError(null);
        name = mNameView.getText().toString();
        EditText focusView = null;

        try {
            focusView = mNameView;
            isNameValid(name);
            focusView = mPhoneNumberView;
            isPhoneNumberValid(phoneNumber);
            if (country == null)
                throw new InvalidInputException(getResources().getString(R.string.please_select_country));
            showProgress(true);

            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final String token = sharedPreferences.getString("token", "123");
            final String sentToken = sharedPreferences.getString("user_token_sent_to_server", "");
            if (token.equals("123") && !checkPlayServices()) {
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.update_google_play_services), Toast.LENGTH_LONG).show();
                finish();
                return;
            } else {
                PlayerController.getInstance(getmController()).Register(phoneNumber, name, country.getId(), new SuccessCallback<Player>() {
                    @Override
                    public void OnSuccess(Player player) {
                        Intent intent = new Intent(RegisterActivity.this, VerficationActivity.class);
                        intent.putExtra("mobile", player.getPhone());
                        intent.putExtra("name", player.getName());
                        intent.putExtra("serverId", player.getServerId());
                        UserUtils.getInstance(getmContext()).SavePhone(phoneNumber);
                        UserUtils.getInstance(getmContext()).saveServerId(player.getServerId());
                        UserUtils.getInstance(getmContext()).saveCountry(country);
//                        intent.putExtra("from_context",activity);
//                        intent.putExtra("field_id",getIntent().getExtras().getInt("field_id"));
                        intent.putExtra("fieldDetailsActivity", redirectToFieldDetailsActivity);
                        startActivity(intent);
                        finish();
                    }
                });
            }


        } catch (InvalidInputException e) {
            if (e.getMessage().equals(getResources().getString(R.string.please_select_country))) {
                showMessageInToast(e.getMessage());
            } else {
                focusView.requestFocus();
                focusView.setError(e.getMessage());
            }
        }
    }


    private void isPhoneNumberValid(String phoneNumber) throws InvalidInputException {
        //TODO: Replace this with your own logic
        if (TextUtils.isEmpty(phoneNumber)) {
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
        } else if (!phoneNumber.matches("[0-9]+")) {
            throw new InvalidInputException(getResources().getString(R.string.invalid_phone));
        } /*else if (phoneNumber.charAt(0) != '5') {
            throw new InvalidInputException(getResources().getString(R.string.invalid_phone_start));
        }*/ else if (phoneNumber.length() != 9) {
            throw new InvalidInputException(getResources().getString(R.string.invalid_phone_length));
        }
    }

    private void isNameValid(String name) throws InvalidInputException {
        if (android.text.TextUtils.isEmpty(name))
            throw new InvalidInputException(getResources().getString(R.string.error_field_required));
    }


    void showNextView() {
        if (/*activity != null*/redirectToFieldDetailsActivity) {
            finish();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
     /*   Intent intent1 = new Intent(RegisterActivity.this, SpecialOffersActivity.class);
        intent1.putExtra("skip", true);
        startActivity(intent1);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                submitForm();
                break;

            case R.id.skip_button:
                showNextView();
                break;
            case R.id.refresh_countries_button:
                refresh_countries.setVisibility(View.GONE);
                progressBar_country.setVisibility(View.VISIBLE);
                refreshCountries();
                break;
        }
    }

    public void refreshCountries() {
        BookingController.getInstance(new Controller(getmContext(), new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                if (findViewById(R.id.button_submit) != null)
                    findViewById(R.id.button_submit).setEnabled(true);
                if (findViewById(R.id.register_button) != null)
                    findViewById(R.id.register_button).setEnabled(true);
                if (findViewById(R.id.skip_button) != null)
                    findViewById(R.id.skip_button).setEnabled(true);
                progressBar_country.setVisibility(View.GONE);
                refresh_countries.setVisibility(View.VISIBLE);
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
//                    sharedPreferences.edit().putString("unregistered_user_token_sent_to_server", "").apply();
                    sharedPreferences.edit().putString("user_token_sent_to_server", "").apply();
                    sharedPreferences.edit().putString("file_path", "").apply();
                }/* else if (findViewById(R.id.parent_panel) != null) {
                    if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError) {
                        showSnackBar(Html.fromHtml(Message).toString());
                        if (findViewById(R.id.number_buttons_layout) != null) {
                            findViewById(R.id.number_buttons_layout).setVisibility(View.GONE);
                        }
                    } else {
                        Snackbar.make(findViewById(R.id.parent_panel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();

                    }
                }*/ else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();

            }
        })).getCountries(new SuccessCallback<List<Country>>() {
            @Override
            public void OnSuccess(List<Country> countries) {
                progressBar_country.setVisibility(View.GONE);
                ArrayAdapter<Country> areasAdapter = new CountriesAdapter(getmContext(), R.layout.country_spinner_item, countries);
                countriesSpinner.setAdapter(areasAdapter);
                Country mCountry = UserUtils.getInstance(getmContext()).getCountry();
                if (mCountry != null)
                    for (int i = 0; i < countries.size(); i++) {
                        if (countries.get(i).equals(mCountry)) {
                            countriesSpinner.setSelection(i);
                            break;
                        }
                    }
                //change countries to Map and set selection here
            }
        });
    }
}

