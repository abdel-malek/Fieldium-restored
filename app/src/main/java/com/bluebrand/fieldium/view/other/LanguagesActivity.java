package com.bluebrand.fieldium.view.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.PlayerController;
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.core.model.Player;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.network.SuccessCallback;

import java.util.ArrayList;
import java.util.Locale;


public class LanguagesActivity extends MasterActivity {
    ImageButton close_button;
    TextView languages_title;
    RadioGroup languageRadioGroup;
    RadioButton arabicRadioButton;
    RadioButton englishRadioButton;
    String currentLang;
    boolean arabicChecked;
    boolean englishChecked;
    Locale locale;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        setContentView(R.layout.activity_languages);
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setFinishOnTouchOutside(false);
        showData();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void showData() {
        currentLang = Locale.getDefault().getLanguage();
        if (currentLang.equals("ar")) {
            arabicChecked = true;
            languageRadioGroup.check(R.id.arabic_setting_radioButton);
        } else {
            englishChecked = true;
            languageRadioGroup.check(R.id.english_setting_radioButton);
        }
        /*  for (int i = 0; i < countries.size(); i++) {
            //create imageview here and setbg
            Button language_button = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 10, 10);
            language_button.setLayoutParams(layoutParams);
            language_button.setText(countries.get(i).getName());
            language_button.setBackgroundResource(R.drawable.button_selector_languages);
            language_button.setTextColor(getResources().getColor(R.color.white_text));
            language_button.setTextAppearance(getmContext(), R.style.AppTheme_TextAppearance_Medium_White);
            ((LinearLayout) findViewById(R.id.language_buttons)).addView(
                    language_button, i);

            final int finalI = i;
            language_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countries.get(finalI).getRtl() == 1) {
                        SharedPreferences lang = getmContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
                        Locale locale = new Locale("ar");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        SharedPreferences.Editor editor = lang.edit();
                        editor.putInt("lang_id", countries.get(finalI).getId());
                        editor.putInt("rtl", countries.get(finalI).getRtl());
                        editor.commit();
                    } else {
                        SharedPreferences lang = getmContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
                        Locale locale = new Locale("en");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        SharedPreferences.Editor editor = lang.edit();
                        editor.putInt("lang_id", countries.get(finalI).getId());
                        editor.putInt("rtl", countries.get(finalI).getRtl());
                        editor.commit();
                    }
                    getAppLanguage(countries.get(finalI).getId());

                }
            });
        }*/
    }

    @Override
    public void assignUIRefrences() {
        close_button = (ImageButton) findViewById(R.id.close_button);
        mProgressView = (View) findViewById(R.id.proccess_indicator);
        mFormView = (View) findViewById(R.id.form_container);
        languages_title = (TextView) findViewById(R.id.languages_title);
        languageRadioGroup = (RadioGroup) findViewById(R.id.language_radioGroup);
        arabicRadioButton = (RadioButton) findViewById(R.id.arabic_setting_radioButton);
        englishRadioButton = (RadioButton) findViewById(R.id.english_setting_radioButton);
    }

    @Override
    protected void assignActions() {
        close_button.setOnClickListener(this);

        englishRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && !englishChecked && !currentLang.equals("en")) {
                    if (UserUtils.getInstance(getmContext()).IsLogged()) {
                        showProgress(true);
                        player = UserUtils.getInstance(getmContext()).Get();
                        player.setLanguage("en");
                        PlayerController.getInstance(getmController()).updateInfo(player, new SuccessCallback<Player>() {
                            @Override
                            public void OnSuccess(Player result) {
                                showProgress(false);
                                changeLanguage("en");
                                result.setServerId(player.getServerId());
                                result.setToken(player.getToken());
                                result.setLanguage("en");
                                UserUtils.getInstance(getmContext()).Save(player);
                            }
                        });
                    } else
                        changeLanguage("en");
                }
            }
        });
        arabicRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && !arabicChecked && !currentLang.equals("ar")) {
                    if (UserUtils.getInstance(getmContext()).IsLogged()) {
                        showProgress(true);
                        player = UserUtils.getInstance(getmContext()).Get();
                        player.setLanguage("ar");
                        PlayerController.getInstance(getmController()).updateInfo(player, new SuccessCallback<Player>() {
                            @Override
                            public void OnSuccess(Player result) {
                                showProgress(false);
                                changeLanguage("ar");
                                result.setServerId(player.getServerId());
                                result.setToken(player.getToken());
                                result.setLanguage("ar");
                                UserUtils.getInstance(getmContext()).Save(player);
                            }
                        });
                    } else changeLanguage("ar");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_button)
            finish();
    }

    public void changeLanguage(String language) {
//        if (!currentLang.equals(language)) {
        SharedPreferences lang = this.getSharedPreferences("lang", Context.MODE_PRIVATE);
        locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = lang.edit();
        editor.putString("value", language);
        editor.commit();

        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
//        }
    }

}
