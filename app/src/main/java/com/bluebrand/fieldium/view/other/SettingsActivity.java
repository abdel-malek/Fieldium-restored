package com.bluebrand.fieldium.view.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.view.MasterActivity;

import java.util.Locale;

public class SettingsActivity extends MasterActivity {
    TextView languageBtn, countryBtn, currentLang_textView, currentCountry_textView;
    View lang_view, country_view;
    String localLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_setttings);
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.action_settings));
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void showData() {

    }

    @Override
    public void assignUIRefrences() {
        lang_view = (View) findViewById(R.id.lang_view);
        country_view = (View) findViewById(R.id.country_view);
        currentCountry_textView = (TextView) findViewById(R.id.currentcountry_textView);
        currentLang_textView = (TextView) findViewById(R.id.currentLang_textView);
        localLang = Locale.getDefault().getLanguage();
        if (localLang.equals("ar")) {
            currentCountry_textView.setText(UserUtils.getInstance(getmContext()).getCountry().getAr_name());
            currentLang_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.settings_triangle_r, 0, 0, 0);
            currentCountry_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.settings_triangle_r, 0, 0, 0);
            currentLang_textView.setText(getResources().getString(R.string.arabic));
        } else {
            currentCountry_textView.setText(UserUtils.getInstance(getmContext()).getCountry().getEn_name());
            currentLang_textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.settings_triang_l, 0);
            currentCountry_textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.settings_triang_l, 0);
            currentLang_textView.setText(getResources().getString(R.string.english));
        }
    }

    @Override
    public void assignActions() {
        lang_view.setOnClickListener(this);
        country_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lang_view:
                Intent intent = new Intent(SettingsActivity.this, LanguagesActivity.class);
                startActivity(intent);
                break;
            case R.id.country_view:
                Intent intent1 = new Intent(SettingsActivity.this, CountriesActivity.class);
                startActivityForResult(intent1, 49);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 49 && resultCode == RESULT_OK) {
            if (localLang.equals("ar"))
                currentCountry_textView.setText(UserUtils.getInstance(getmContext()).getCountry().getAr_name());
            else
                currentCountry_textView.setText(UserUtils.getInstance(getmContext()).getCountry().getEn_name());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
