package com.bluebrand.fieldiumadmin.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.AppInfo;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;

/**
 * Created by r.desouki on 1/19/2017.
 */
public class AboutActivity extends MasterActivity {
    AppInfo appInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_info);
        super.onCreate(savedInstanceState);
    }

    @Override
    void getData() {
        appInfo=new CurrentAndroidUser(this).GetAppInfo();

    }

    @Override
    void showData() {
        if(appInfo.getEmail().equals("")){
            ((TextView)findViewById(R.id.info_email)).setVisibility(View.GONE);
        }else{
            ((TextView)findViewById(R.id.info_email)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.info_email)).setText("Email: "+appInfo.getEmail());
        }
        if(appInfo.getPhone().equals("")){
            ((TextView)findViewById(R.id.info_phone)).setVisibility(View.GONE);
        }else{
            ((TextView)findViewById(R.id.info_phone)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.info_phone)).setText("Phone: "+appInfo.getPhone());
        }
        if(appInfo.getMobile().equals("")){
            ((TextView)findViewById(R.id.info_mobile)).setVisibility(View.GONE);
        }else{
            ((TextView)findViewById(R.id.info_mobile)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.info_mobile)).setText("Mobile: "+appInfo.getMobile());
        }

    }

    @Override
    void assignUIReferences() {

    }

    @Override
    void assignActions() {

    }

    @Override
    public void onClick(View view) {

    }
}
