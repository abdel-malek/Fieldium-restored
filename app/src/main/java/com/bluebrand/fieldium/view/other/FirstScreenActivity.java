package com.bluebrand.fieldium.view.other;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.UserUtils;

import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.fieldium.view.Booking.HomeActivity;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.Player.RegisterActivity;
import com.bluebrand.fieldium.view.Player.VerficationActivity;
import com.bluebrand.fieldium.view.adapter.CountryAdapter;
import com.bluebrand.fieldium.view.adapter.CountryAdapterFirstScreen;
import com.bluebrand.network.SuccessCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user .
 */
public class FirstScreenActivity extends MasterActivity {
    ArrayList<Country> countries;
    SharedPreferences sharedPreferences;
    ListView countries_listView;
    CountryAdapterFirstScreen adapter;
    Country mCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_first_screen);
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean first = sharedPreferences.getBoolean("app_first_use", true);
        if (first) {
//            fieldiumApplication=(FieldiumApplication)getApplication();
            getData();
        } else ShowNextView();
    }

    @Override
    protected void getData() {
        showProgress(true);
        BookingController.getInstance(getmController()).getCountries(new SuccessCallback<List<Country>>() {
            @Override
            public void OnSuccess(List<Country> result) {
                showProgress(false);
                countries = new ArrayList<Country>();
                countries.addAll(result);
                showData();
            }
        });

    }

    @Override
    protected void showData() {
        if (countries.size() == 0) {
            findViewById(R.id.noResult).setVisibility(View.VISIBLE);
            countries_listView.setVisibility(View.GONE);
        } else {
            findViewById(R.id.noResult).setVisibility(View.GONE);
            countries_listView.setVisibility(View.VISIBLE);
            adapter = new CountryAdapterFirstScreen(getmContext(), R.layout.country_item_first_screen, countries);
            countries_listView.setAdapter(adapter);
            countries_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    adapter.setSelectedIndex(i);
                    findViewById(R.id.select_country_btn).setVisibility(View.VISIBLE);
                    mCountry = countries.get(i);
//                    ((TextView) view.findViewById(R.id.textView_countryName)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_black_24dp, 0, 0, 0);
                 /*   final SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("app_first_use", false);
                    editor.commit();
                    UserUtils.getInstance(getmContext()).saveCountry(countries.get(i));
                    ShowNextView();*/
                }
            });
        }
//        if (countries.size() == 0)
//            findViewById(R.id.noResult).setVisibility(View.VISIBLE);
    /*    for (int i = 0; i < countries.size(); i++) {
            //create imageview here and setbg
            Button country_button = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 10, 10);
            country_button.setLayoutParams(layoutParams);
            country_button.setText(countries.get(i).getName());
            country_button.setBackgroundResource(R.drawable.button_selector_countries);
            country_button.setTextColor(getResources().getColor(R.color.white_text));
            country_button.setTextAppearance(getmContext(), R.style.AppTheme_TextAppearance_Medium_White);
            ((LinearLayout) findViewById(R.id.countries_buttons)).addView(
                    country_button, i);

            final int finalI = i;
            country_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("app_first_use", false);
                    editor.commit();

                    UserUtils.getInstance(getmContext()).saveCountry(countries.get(finalI));
                    ShowNextView();
                }
            });
        }*/

    }

    @Override
    public void assignUIRefrences() {
        mFormView = findViewById(R.id.form_container);
        mProgressView = findViewById(R.id.proccess_indicator);
        countries_listView = (ListView) findViewById(R.id.countries_listView);
    }

    @Override
    protected void assignActions() {
        findViewById(R.id.select_country_btn).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.select_country_btn) {
            if (mCountry == null)
                showMessageInSnackbar(getResources().getString(R.string.please_select_country));
            else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("app_first_use", false);
                editor.commit();
                UserUtils.getInstance(getmContext()).saveCountry(mCountry);
                ShowNextView();
            }
        }

    }

    public void ShowNextView() {

        if (UserUtils.getInstance(this).IsLogged()) {

//            if (!UserUtils.getInstance(this).IsRegisterCompleted()) {
//                Intent intent = new Intent(SplashActivity.this, CompleteRegisterationActivity.class);
//                startActivity(intent);
//            } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
//        }
        else {
            if (UserUtils.getInstance(this).IsRegistered()) {
                String phone = UserUtils.getInstance(this).GetPhone();
                Intent intent = new Intent(FirstScreenActivity.this, VerficationActivity.class);
                intent.putExtra("mobile", phone);
                intent.putExtra("serverId", UserUtils.getInstance(this).getServerId());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
        }
        finish();
    }

  /*  public void getCountries(int lang_id) {
        showProgress(true);
        WakeelController.getInstance(getmController()).getAppValues(lang_id, new SuccessCallback<AppValuesModel>() {
            @Override
            public void OnSuccess(AppValuesModel result) {
                wakeelApplication.setAppValuesModel(result);
                ShowNextView();
            }
        });
    }*/
}
