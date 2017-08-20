package com.bluebrand.fieldium.view.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.BookingController;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.Booking.HomeActivity;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.fieldium.view.adapter.CompanyAdapter;
import com.bluebrand.fieldium.view.adapter.CountryAdapter;
import com.bluebrand.network.SuccessCallback;


import java.util.ArrayList;
import java.util.List;

public class CountriesActivity extends MasterActivity {

    ArrayList<Country> countries;
    ImageButton close_button;
    ListView countries_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_countries);
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setFinishOnTouchOutside(false);
        getData();
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
            countries_listView.setAdapter(new CountryAdapter(getmContext(), R.layout.country_item, countries));
            countries_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                  /*  showProgress(true);
                    final Player player = UserUtils.getInstance(getmContext()).Get();
                    player.setCountry(countries.get(i));
                    PlayerController.getInstance(getmController()).updateInfo(player, new SuccessCallback<Player>() {
                        @Override
                        public void OnSuccess(Player result) {
                            showProgress(false);
                            result.setServerId(player.getServerId());
                            result.setToken(player.getToken());
                            UserUtils.getInstance(getmContext()).Save(result);*/
                            UserUtils.getInstance(getmContext()).saveCountry(countries.get(i));
                            Intent intent = new Intent(getmContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
//                            setResult(RESULT_OK, intent);
                            finish();
                  /*      }
                    });*/
                }
            });
        }

        /*for (int i = 0; i < countries.size(); i++) {
            //create imageview here and setbg
            final Button countryButton = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 10, 10);
            countryButton.setLayoutParams(layoutParams);
            countryButton.setText(countries.get(i).getName());
            countryButton.setBackgroundResource(R.drawable.button_selector_countries);
            countryButton.setTextAppearance(getmContext(), R.style.AppTheme_TextAppearance_Medium_White);
            countryButton.setTextColor(getResources().getColor(R.color.dark_gray));

            ((LinearLayout) findViewById(R.id.countries_buttons)).addView(
                    countryButton, i);

            final int finalI = i;
            countryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update user country
                    if (UserUtils.getInstance(getmContext()).IsLogged()) {
                        showProgress(true);
                        Player player = UserUtils.getInstance(getmContext()).Get();
                        player.setCountry(countries.get(finalI));
                        PlayerController.getInstance(getmController()).updateInfo(player, new SuccessCallback<Player>() {
                            @Override
                            public void OnSuccess(Player result) {
                                showProgress(false);
                                UserUtils.getInstance(getmContext()).saveCountry(countries.get(finalI));
                                finish();
                            }
                        });
                    } else {
                        UserUtils.getInstance(getmContext()).saveCountry(countries.get(finalI));
                        finish();
                    }

                }
            });
        }*/
    }

    @Override
    public void assignUIRefrences() {
        close_button = (ImageButton) findViewById(R.id.close_button);
        mProgressView = (View) findViewById(R.id.proccess_indicator);
        mFormView = (View) findViewById(R.id.form_container);
        countries_listView = (ListView) findViewById(R.id.countries_listView);
    }

    @Override
    protected void assignActions() {
        close_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_button)
            finish();
    }

}

