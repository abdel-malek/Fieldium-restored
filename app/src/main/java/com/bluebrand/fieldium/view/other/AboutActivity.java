package com.bluebrand.fieldium.view.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.controller.AboutController;
import com.bluebrand.fieldium.core.model.AboutModel;
import com.bluebrand.fieldium.view.MasterActivity;
import com.bluebrand.network.SuccessCallback;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public class AboutActivity
        extends MasterActivity {
    TextView textViewInfo;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        super.onCreate(savedInstanceState);

//        progressBar.setVisibility(View.VISIBLE);
//        textViewInfo.setVisibility(View.GONE);
        setTitle(getTitle());
        getData();

    }

    @Override
    protected void getData() {
        AboutController.getInstance(getmController()).LoadAbout(new SuccessCallback<AboutModel>() {
            @Override
            public void OnSuccess(AboutModel about) {
                progressBar.setVisibility(View.GONE);
                textViewInfo.setVisibility(View.VISIBLE);
//                String info_email="E-mail: "+about.getEmail();
//                String info_phone="Phone: "+about.getPhone();
//                String info_mobile="Mobile: "+about.getMobile();
                textViewInfo.setText("E-mail: "+about.getEmail()+(!about.getPhone().equals("")?"\nPhone: "+about.getPhone():"")+(!about.getMobile().equals("")?"\n Mobile: "+about.getMobile():""));

            }
        });
    }

    @Override
    protected void showData() {

    }

    @Override
    public void assignUIRefrences() {
        textViewInfo = (TextView) findViewById(R.id.textView_info);
        progressBar = (ProgressBar) findViewById(R.id.proccess_indicator);

    }

    @Override
    protected void assignActions() {

    }

    @Override
    public void onClick(View v) {

    }
}
